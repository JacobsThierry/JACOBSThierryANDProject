package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API;


import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("recipes/search")
    Call<RecipeResponse> searchFood(@Query("query") String query, @Query("apiKey") String apiKey);

    @GET("recipes/random")
    Call<RecipeResponse> popularFood(@Query("apiKey") String apiKey, @Query("number") int number);

    @GET("recipes/{id}/information")
    Call<Recipe> getById(@Path("id") int id, @Query("apiKey") String apiKey);

}
