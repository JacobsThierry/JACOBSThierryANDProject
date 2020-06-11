package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource;

import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.FoodApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String ApiKey = "a8fc84c7be27483e81172e8e6a41691c";

    /*
    spare keys :
    d0a8a221299846a892c75d423bf4bc00
    ae9fd15ab2d34282ba771a0fae871c3b
    a8fc84c7be27483e81172e8e6a41691c

    I know this is a bad idea to put api keys there, but those are free keys that I have to change every so often because I can only do 50 requests with a key
     */

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
