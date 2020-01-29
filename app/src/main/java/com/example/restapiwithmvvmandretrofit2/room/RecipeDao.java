package com.example.restapiwithmvvmandretrofit2.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restapiwithmvvmandretrofit2.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("select * from Recipe")
    List<Recipe> getAllRecipe();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Recipe> recipes);


/*
   @Query("select * from Recipe where status==0")
    List<LeadC> getAllList();

 @Update
    void update(LeadC leadC);

    @Delete
    void delete(LeadC leadC);

    @Query("delete from LeadC")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM LeadC WHERE status = 0")
    LiveData<Integer> getCount();

    @Query("SELECT COUNT(*) FROM LeadC WHERE status = 0")
    Integer getCounts();*/

}
