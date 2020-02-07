package com.android.foosh.mvvmpro1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;

    // empty Constructor
    public RecipeListViewModel() {

        mIsViewingRecipes =false;
        Log.d("LifeCycle:","RecipeListViewModel,Constructor");
        mRecipeRepository = RecipeRepository.getInstance();

    }


    public LiveData<List<Recipe>> getRecipes() {

        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query,int pageNumber){
        mIsViewingRecipes=true; // stop showing the Category
        mRecipeRepository.searchRecipesApi(query,pageNumber);
    }

    public boolean ismIsViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setmIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }
}
