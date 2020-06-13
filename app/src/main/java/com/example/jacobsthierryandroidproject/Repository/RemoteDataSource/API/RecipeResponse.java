package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {
    @SerializedName(value="recipes", alternate={"results"})
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }


    @Override
    public String toString() {
        return "FoodResponseExtended{" +
                "recipes=" + recipes +
                '}';
    }
}
