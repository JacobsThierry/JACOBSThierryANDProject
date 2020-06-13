package com.example.jacobsthierryandroidproject.View.fragment;

import android.os.Bundle;
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
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.example.jacobsthierryandroidproject.View.adapter.FoodAdapter;
import com.example.jacobsthierryandroidproject.View.adapter.OnListItemClickListener;
import com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodItemFragment;

import java.util.ArrayList;
import java.util.List;

public class favoriteFragment extends Fragment implements OnListItemClickListener{


    RecipeModel viewModel;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Recipe> list;
    FoodAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(viewModel == null) viewModel = new RecipeModel(getActivity().getApplication());
        recyclerView = getView().findViewById(R.id.favoriteRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        progressBar = getView().findViewById(R.id.favorite_progressbar);
        final OnListItemClickListener lis = this;
        if(list == null) list=new ArrayList<>();

        viewModel.getFavorite().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                list = recipes;
                adapter.setFood(new ArrayList<Recipe>(list));
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visible = aBoolean?View.VISIBLE:View.INVISIBLE;

                progressBar.setVisibility(visible);
            }
        });

        adapter = new FoodAdapter(list, lis);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClick(Recipe food) {
        foodItemFragment foodItemFragment = com.example.jacobsthierryandroidproject.View.fragment.recipePage.foodItemFragment.newInstance(food);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodItemFragment, "foodFragment").addToBackStack(null).commit();
    }
}
