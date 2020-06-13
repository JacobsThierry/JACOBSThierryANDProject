package com.example.jacobsthierryandroidproject.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jacobsthierryandroidproject.Pojo.Comment;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Repository.Firebase.firebase;

import java.util.ArrayList;

public class CommentsModel extends AndroidViewModel {


    LiveData<Comment> recipesLivedata = new MutableLiveData<>();
    firebase fb = new firebase();

    public CommentsModel(Application application){
        super(application);
    }

    public LiveData<ArrayList<Comment>> getComments(Recipe recipe){
        return getComments(recipe.getId());
    }

    public LiveData<ArrayList<Comment>> getComments(int id){
        fb.getComments(id);
        return fb.getLiveData();
    }

    public void addComment(Comment com){
        fb.addComment(com);
    }

    public void addComment(String name, String content, int recipeid){
        addComment(new Comment(name, content, recipeid));
    }



}
