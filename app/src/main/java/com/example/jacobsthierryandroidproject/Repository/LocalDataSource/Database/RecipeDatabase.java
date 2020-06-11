package com.example.jacobsthierryandroidproject.Repository.LocalDataSource.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.DAO.RecipeDao;
import com.example.jacobsthierryandroidproject.Repository.LocalDataSource.DataConverter;

@Database(entities = {Recipe.class}, version = 3)
@TypeConverters({DataConverter.class})
public abstract class RecipeDatabase extends RoomDatabase {
    private static RecipeDatabase instance;
    public abstract RecipeDao getRecipeDao();

    public static synchronized  RecipeDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration().build();
        }
        return  instance;
    }

}
