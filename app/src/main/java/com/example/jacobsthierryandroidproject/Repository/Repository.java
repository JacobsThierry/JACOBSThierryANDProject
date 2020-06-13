package com.example.jacobsthierryandroidproject.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Pojo.myCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.DAO.RecipeDao;
import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.Database.RecipeDatabase;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API.FoodApi;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API.ServiceGenerator;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API.RecipeResponse;

import java.util.List;

public class Repository {

    private RecipeDao recipeDao;
    private static Repository instance;
    private LiveData<List<Recipe>> favourites;
    private LiveData<Boolean> isInFavoriteData;

    public static synchronized Repository getInstance(Application application){
        if(instance == null) instance = new Repository(application);
        return instance;
    }


    private Repository(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDao = database.getRecipeDao();
        favourites = recipeDao.getAllFavourites();
    }

    public static void requestRandom(final myCallback<RecipeResponse> callback, int number){
        FoodApi foodApi = ServiceGenerator.getFoodApi();
        Call<RecipeResponse> call = foodApi.popularFood(ServiceGenerator.ApiKey, number);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.code() == 200){
                    callback.callbackCall(response.body());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.d("food resp", t.toString());

            }
        });

    }

    public static void requestFood(String query, final myCallback<RecipeResponse> callback){
        FoodApi foodApi = ServiceGenerator.getFoodApi();
        Call<RecipeResponse> call = foodApi.searchFood(query, ServiceGenerator.ApiKey);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d("food resp", Integer.toString(response.code()));
               // Log.d("food resp", response.body().toString());
                if(response.code() == 200){
                    Log.d("food rest", response.body().toString());
                    callback.callbackCall(response.body());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.d("food resp", t.toString());
            }
        });
    }

    public static void getById(int id, final myCallback<Recipe> callback){
        FoodApi foodApi = ServiceGenerator.getFoodApi();
        Call<Recipe> call = foodApi.getById(id, ServiceGenerator.ApiKey);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Log.d("food resp", Integer.toString(response.code()));
                //Log.d("food resp", response.body().toString());
                if(response.code() == 200){
                    callback.callbackCall(response.body());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("food resp", t.toString());
            }
        });

    }


    public LiveData<List<Recipe>> getFavourites(){

        favourites = recipeDao.getAllFavourites();
        return favourites;

    }

    public void addRecipeToFavourites(Recipe recipe){

        new InsertRecipeAsync(recipeDao).execute(recipe);
    }



    public void removeRecipeFromFavourites(Recipe recipe){
        new RemoveRecipeAsync(recipeDao).execute(recipe);
    }

    public void changeFavorite(final Recipe recipe){
        Log.d("food ", "Change favorite 1");
        new ChangeFavoriteAsync(recipeDao).execute(recipe);


    }


        public void isInFavorite(Recipe recipe){
        Log.d("food ", "is in favorite");
            isInFavoriteData = recipeDao.isRecipeInFavorite(recipe.getId());
        }

        public LiveData<Boolean> getIsInFavoriteData(){
        return isInFavoriteData;
        }




    private static class InsertRecipeAsync extends AsyncTask<Recipe, Void, Void> {

        private RecipeDao recipeDao;

        private InsertRecipeAsync(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            LiveData<List<Recipe>> ld = recipeDao.getRecipeById(recipes[0].getId());
            recipeDao.insert(recipes[0]);//duplicate key is handled in DAO
            return null;


        }
    }

    private static class ChangeFavoriteAsync extends AsyncTask<Recipe, Void, Void>{

        private RecipeDao recipeDao;

        private ChangeFavoriteAsync(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            Boolean boo = recipeDao.isRecipeInFavoriteSynch(recipes[0].getId());
            if(boo){
                recipeDao.delete(recipes[0]);
            }else{
                recipeDao.insert(recipes[0]);
            }
            return null;
        }
    }

    private static class RemoveRecipeAsync extends AsyncTask<Recipe, Void, Void>{

        private RecipeDao recipeDao;

        private RemoveRecipeAsync(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            LiveData<List<Recipe>> ld = recipeDao.getRecipeById(recipes[0].getId());

            recipeDao.delete(recipes[0]);
            return null;
        }
    }
}
