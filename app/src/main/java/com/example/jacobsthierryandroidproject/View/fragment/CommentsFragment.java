package com.example.jacobsthierryandroidproject.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jacobsthierryandroidproject.Model.CommentsModel;
import com.example.jacobsthierryandroidproject.Pojo.Comment;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.example.jacobsthierryandroidproject.View.adapter.CommentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Comment> list;
    CommentsAdapter adapter;
    CommentsModel model;
    Recipe recipe;
    private static String KEYRECIPE = "Lorem";

    private static String KEY = "azjioeazjpoiazp";


    public static CommentsFragment newInstance(Recipe recipe){
        CommentsFragment frag = new CommentsFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(KEYRECIPE, recipe);
        frag.setArguments(arg);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arg = getArguments();
        if(arg != null){
            if(arg.getSerializable(KEYRECIPE) != null){
                this.recipe = (Recipe) arg.getSerializable(KEYRECIPE);
            }
        }
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list != null){
            outState.putSerializable(KEY, new ArrayList<Comment>(list));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            if(savedInstanceState.getSerializable(KEY) != null){
                Log.d("aa", "aa");
                list = (List<Comment>) savedInstanceState.getSerializable("list") ;}}}


    @Override
    public void onStart() {
        super.onStart();
        if(model == null) model = new CommentsModel(getActivity().getApplication());
        recyclerView = getView().findViewById(R.id.rv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        if(list == null) list=new ArrayList<>();

        adapter = new CommentsAdapter();
        recyclerView.setAdapter(adapter);

        if(recipe != null){
            model.getComments(recipe).observe(this, new Observer<ArrayList<Comment>>() {
                @Override
                public void onChanged(ArrayList<Comment> comments) {
                    adapter.setComments(comments);
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }
}
