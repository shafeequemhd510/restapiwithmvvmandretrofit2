package com.example.restapiwithmvvmandretrofit2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restapiwithmvvmandretrofit2.Repositories.RecipeRepository;
import com.example.restapiwithmvvmandretrofit2.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    public RecipeListViewModel() {

        mRecipeRepository=RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }
    public void searchRecipesApi(String query , int pageNumber) {
        mRecipeRepository.searchRecipesApi(query, pageNumber);
    }

    }
