package com.example.jacobsthierryandroidproject.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jacobsthierryandroidproject.Model.RecipeModel;
import com.example.jacobsthierryandroidproject.View.adapter.FoodAdapter;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.example.jacobsthierryandroidproject.View.adapter.OnListItemClickListener;
import com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodFragmentStateAdapter;
import com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodItemFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnListItemClickListener{

    RecyclerView recyclerView;
    List<Recipe> list;
    FoodAdapter adapter;

    RecipeModel viewModel ;
    ProgressBar progressBar;

    private static String KEY = "ABC123ABC456";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list != null){
        outState.putSerializable(KEY, new ArrayList<Recipe>(list));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
        if(savedInstanceState.getSerializable(KEY) != null){
            Log.d("aa", "aa");
        list = (List<Recipe>) savedInstanceState.getSerializable("list") ;}}}



    @Override
    public void onStart() {
        super.onStart();
        if(viewModel == null) viewModel = new RecipeModel(getActivity().getApplication());
        recyclerView = getView().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        progressBar = getView().findViewById(R.id.home_progress_bar);
        final OnListItemClickListener lis = this;
        if(list == null) list=new ArrayList<>();
        adapter = new FoodAdapter(list, lis);
        recyclerView.setAdapter(adapter);

        viewModel.getQueryResult().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if(recipes == null) list = new ArrayList<>();
                else list = recipes;
                Log.d("onChanged", recipes.toString());
                adapter.setFood(new ArrayList<Recipe>(list));
                adapter.notifyDataSetChanged();;

            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visible = aBoolean?View.VISIBLE:View.INVISIBLE;
                progressBar.setVisibility(visible);
            }
        });

        if(list.size() < 1) viewModel.queryRandom();



    }

    @Override
    public void onClick(Recipe position) {
        Log.d("food home", position.toString());
        /*foodItemFragment foodItemFragment = com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodItemFragment.newInstance(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodItemFragment, "foodFragment").addToBackStack(null).commit();*/
        foodFragmentStateAdapter az = foodFragmentStateAdapter.getInstance(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, az, "foodFragment").addToBackStack(null).commit();
    }
}
