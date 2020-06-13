package com.example.jacobsthierryandroidproject.View.fragment.recipePage;

import android.app.Activity;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Comment> list = new ArrayList<>();
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


                int SECOND_ACT = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SECOND_ACT){
            Log.d("food requestCode", Integer.toString(requestCode));

            if(resultCode == Activity.RESULT_OK){

                String result = data.getStringExtra("result");
                Log.d("food result", result);
                model.addComment(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), result, recipe.getId());
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        final Fragment act = this;
        FloatingActionButton fab = getView().findViewById(R.id.btn_add_comment);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act.getContext(), addComment.class);
                startActivityForResult(i, SECOND_ACT);

            }
        });



        if(model == null) model = new CommentsModel(getActivity().getApplication(), recipe);
        recyclerView = getView().findViewById(R.id.rv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        if(list == null) list=new ArrayList<>();

        adapter = new CommentsAdapter(new ArrayList<Comment>(list));
        recyclerView.setAdapter(adapter);

        if(recipe != null){
            model.getComments(recipe).observe(this, new Observer<ArrayList<Comment>>() {
                @Override
                public void onChanged(ArrayList<Comment> comments) {
                    if(comments != null){
                    adapter.setComments(comments);
                    adapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

}
