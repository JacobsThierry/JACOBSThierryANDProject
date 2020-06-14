package com.example.jacobsthierryandroidproject.Model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Pojo.myCallback;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API.RecipeResponse;
import com.example.jacobsthierryandroidproject.Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Recipe>> queryResult = new MutableLiveData<>();
    private MutableLiveData<Recipe> singleQueryResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private LiveData<Boolean> isInFavorite = new MutableLiveData<>(false);
    private Repository repository;

    public RecipeModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<ArrayList<Recipe>> getQueryResult() {
        return queryResult;
    }

    public LiveData<Recipe> getSingleQueryResult() {
        return singleQueryResult;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public void queryByName(String argument) {
        isLoading.setValue(true);
        Repository.requestFood(argument, new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                isLoading.setValue(false);
                if (result != null && result.getRecipes() != null)
                    queryResult.postValue(new ArrayList<Recipe>(result.getRecipes()));
            }
        });
    }

    public void queryRandom() {
        isLoading.setValue(true);
        Repository.requestRandom(new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                isLoading.setValue(false);
                queryResult.postValue(new ArrayList<Recipe>(result.getRecipes()));
            }
        }, 30);
    }

    public void queryById(int argument) {
        isLoading.setValue(true);
        Repository.getById(argument, new myCallback<Recipe>() {
            @Override
            public void callbackCall(Recipe result) {
                isLoading.setValue(false);
                singleQueryResult.postValue(result);
            }
        });
    }

    public LiveData<List<Recipe>> getFavorite() {
        return repository.getFavourites();
    }

    public void addToFavorite(Recipe recipe) {
        repository.addRecipeToFavourites(recipe);
    }


    public void isInFavorite(Recipe recipe) {
        repository.isInFavorite(recipe);
        isInFavorite = repository.getIsInFavoriteData();
    }

    public LiveData<Boolean> getIsInFavorite() {
        return isInFavorite;

    }

    public void changeFavorite(Recipe recipe) {
        Log.d("Food ", "recipe changeFavorite");
        repository.changeFavorite(recipe);
    }

}
