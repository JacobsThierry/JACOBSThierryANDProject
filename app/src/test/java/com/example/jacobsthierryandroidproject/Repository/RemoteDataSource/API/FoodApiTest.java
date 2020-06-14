package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FoodApiTest {

    FoodApi api;

    @Before
    public void mokeUpWeb() {
        api = ServiceGenerator.getFoodApi();
    }

    @Test
    public void testQuerry() {
        Response<RecipeResponse> r = null;
        try {
            r = api.searchFood("burger", ServiceGenerator.ApiKey).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(r);
    }

    @Test
    public void testRandom() {
        Response<RecipeResponse> r;
        try {
            r = api.popularFood(ServiceGenerator.ApiKey, 5).execute();
        } catch (IOException e) {
            r = null;
        }

        if (r == null) {
            assertTrue(false);
        } else {
            assertTrue(r.body().getRecipes().size() == 5);
        }

    }

    @Test
    public void testGetById() {
        Response<Recipe> r;
        try {
            r = api.getById(1, ServiceGenerator.ApiKey).execute();
        } catch (IOException e) {
            r = null;
            e.printStackTrace();
        }
        assertNotNull(r);

    }


}