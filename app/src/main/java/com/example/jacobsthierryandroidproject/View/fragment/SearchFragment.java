package com.example.jacobsthierryandroidproject.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jacobsthierryandroidproject.Model.RecipeModel;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.example.jacobsthierryandroidproject.View.adapter.FoodAdapter;
import com.example.jacobsthierryandroidproject.View.adapter.OnListItemClickListener;
import com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodFragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements OnListItemClickListener {

    RecyclerView recyclerView;
    List<Recipe> list;
    FoodAdapter adapter;
    SearchView searchView;
    String searchString;
    RecipeModel viewModel;
    ProgressBar progressBar;

    private static String QUERYKEY = "querry";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (list != null) {
            outState.putSerializable("list", new ArrayList<Recipe>(list));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable("list") != null) {
                Log.d("aa", "aa");
                list = (List<Recipe>) savedInstanceState.getSerializable("list");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("TAG", "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.search_menu, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && query.trim() != "") {
                    viewModel.queryByName(query);
                } else {
                    adapter.setFood(new ArrayList<Recipe>());
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        if (searchString != null && !searchString.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel == null) viewModel = new RecipeModel(getActivity().getApplication());
        recyclerView = getView().findViewById(R.id.search_rv);
        final OnListItemClickListener lis = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        if (list == null) list = new ArrayList<>();

        adapter = new FoodAdapter(list, lis);


        recyclerView.setAdapter(adapter);
        progressBar = getView().findViewById(R.id.search_progress_bar);

        viewModel.getQueryResult().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if (recipes == null) list = new ArrayList<>();
                else list = recipes;
                Log.d("onChanged", recipes.toString());
                adapter.setFood(new ArrayList<Recipe>(list));
                adapter.notifyDataSetChanged();


            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visible = aBoolean ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visible);
            }
        });


    }

    @Override
    public void onClick(Recipe food) {
/*
        foodItemFragment foodItemFragment = com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodItemFragment.newInstance(food);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodItemFragment, "foodFragment").addToBackStack(null).commit();
*/
        foodFragmentStateAdapter az = foodFragmentStateAdapter.getInstance(food);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, az, "foodFragment").addToBackStack(null).commit();

        viewModel.queryById(food.getId());

    }

}



