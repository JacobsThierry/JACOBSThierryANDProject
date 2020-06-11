package com.example.jacobsthierryandroidproject.Repository.RemoteDataSource;

import android.util.Log;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Pojo.myCallback;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.responses.RecipeResponse;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class requestsTest extends TestCase {

    @Test
    public void testRequestFood() {
        requests.requestFood("burger", new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                Log.d("food", result.toString());
                assertTrue(result != null);
            }
        });
    }

    @Test
    public void testRequestRandom(){
        requests.requestRandom(new myCallback<RecipeResponse>() {
            @Override
            public void callbackCall(RecipeResponse result) {
                Log.d("food", result.toString());
                assertTrue(result != null);
            }
        }, 5);
    }

    @Test
    public void testGetById(){
        requests.getById(1, new myCallback<Recipe>() {
            @Override
            public void callbackCall(Recipe result) {
                assertTrue(result != null);
            }
        });
    }

}