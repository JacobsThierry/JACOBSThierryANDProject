package com.example.jacobsthierryandroidproject.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Pojo.myCallback;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.requests;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.responses.RecipeResponse;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RecipeModel extends ViewModel {

    private MutableLiveData<ArrayList<Recipe>> queryResult = new MutableLiveData<>();
    private MutableLiveData<Recipe> singleQueryResult = new MutableLiveData<>();

    public LiveData<ArrayList<Recipe>> getQueryResult(){
        return queryResult;
    }

    public LiveData<Recipe> getSingleQueryResult(){
        return singleQueryResult;
    }


    public void queryByName(String argument){
        requests.requestFood(argument, new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                if(result != null && result.getRecipes() != null)
                    queryResult.postValue(new ArrayList<Recipe>(result.getRecipes()));
            }
        });
    }

    public void queryRandom(){
        requests.requestRandom(new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                queryResult.postValue(new ArrayList<Recipe>(result.getRecipes()));
            }
        }, 30);
    }

    public void queryById(int argument){
        requests.getById(argument, new myCallback<Recipe>() {
            @Override
            public void callbackCall(Recipe result) {
                singleQueryResult.postValue(result);
            }
        });
    }





}
