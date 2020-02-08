package com.android.foosh.mvvmpro1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.repositories.RecipeRepository;


public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;


    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();

    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }


    public void searchRecipeById(String recipeId){
       mRecipeRepository.searchRecipeById(recipeId);

    }



}
