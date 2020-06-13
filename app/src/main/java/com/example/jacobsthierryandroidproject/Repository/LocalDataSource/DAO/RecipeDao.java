package com.example.jacobsthierryandroidproject.Repository.LocalDataSource.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipes_favourites")
    LiveData<List<Recipe>> getAllFavourites();

    @Query("SELECT * FROM recipes_favourites")
    List<Recipe> getAllFavouritesSync();

    @Query("SELECT * FROM recipes_favourites where id = :id")
    LiveData<List<Recipe>> getRecipeById(int id);

    @Query("SELECT COUNT(*) > 0 from recipes_favourites where id = :id")
    LiveData<Boolean> isRecipeInFavorite(int id);

    @Query("SELECT COUNT(*) > 0 from recipes_favourites where id = :id")
    Boolean isRecipeInFavoriteSynch(int id);
}
