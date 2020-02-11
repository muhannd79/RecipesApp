package com.android.foosh.mvvmpro1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.repositories.RecipeRepository;


public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mDidRetrieveRecipe;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetrieveRecipe = false;

    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimedOut(){
        return mRecipeRepository.isRecipeRequestTimedOut();
    }

    public void searchRecipeById(String recipeId){
        mRecipeId = recipeId;
       mRecipeRepository.searchRecipeById(recipeId);

    }

    public void setRetrievedRecipe(boolean retrievedRecipe){
        mDidRetrieveRecipe = retrievedRecipe;
    }

    public boolean didRetrieveRecipe(){
        return mDidRetrieveRecipe;
    }

    public String getRecipeId() {
        return mRecipeId;
    }
}
