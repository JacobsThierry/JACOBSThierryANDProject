package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource;

import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.FoodApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String ApiKey = "ae9fd15ab2d34282ba771a0fae871c3b";

    public static String imageUrl = "https://spoonacular.com/recipeImages/";
    public static String baseURL = "https://api.spoonacular.com/";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static FoodApi foodApi = retrofit.create(FoodApi.class);

    public static FoodApi getFoodApi(){
        return foodApi;
    }

}
