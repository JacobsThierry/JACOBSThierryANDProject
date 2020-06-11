package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource;

import android.util.Log;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Pojo.myCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.responses.RecipeResponse;

public abstract class requests {

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
}
