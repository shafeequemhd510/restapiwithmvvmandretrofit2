package com.example.restapiwithmvvmandretrofit2.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restapiwithmvvmandretrofit2.models.Recipe;


@Database(entities = {Recipe.class}, version = 1)
public abstract class AppData extends RoomDatabase {
    public abstract RecipeDao recipeDao();

}

