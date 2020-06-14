package com.example.jacobsthierryandroidproject.Repository.LocalDataSource;

import androidx.room.TypeConverter;

import com.example.jacobsthierryandroidproject.Pojo.Ingredient;
import com.example.jacobsthierryandroidproject.Pojo.Instructions.instruction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromInstructionList(List<instruction> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<instruction>>() {
        }.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public List<instruction> toInstructionList(String instructionString) {
        if (instructionString == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<instruction>>() {
        }.getType();
        List<instruction> list = gson.fromJson(instructionString, type);
        return list;
    }

    @TypeConverter
    public String fromIngredientList(List<Ingredient> list) {
        if (list == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public List<Ingredient> toingredientList(String ingredientString) {
        if (ingredientString == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        List<Ingredient> list = gson.fromJson(ingredientString, type);
        return list;
    }

}
