package com.example.jacobsthierryandroidproject.Repository.LocalDataSource;

import androidx.room.Database;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.DAO.RecipeDao;
import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.Database.RecipeDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataConverterTest {

    RecipeDatabase recipeDatabase;
    RecipeDao recipeDao;
    @Before
    public void createDb(){

        recipeDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), RecipeDatabase.class).build();
        recipeDao = recipeDatabase.getRecipeDao();
    }

    @After
    public void closeDb() throws IOException {
        recipeDatabase.close();
    }

    @Test
    public void testAddRecipe(){
        Recipe recipe = new Recipe(1,"az","az0, 1", 1, null,
                null, "az", "az", 5, 1, "az");
        recipeDao.insert(recipe);
        assertTrue(recipeDao.getAllFavouritesSync().size() == 1);

    }

    

}