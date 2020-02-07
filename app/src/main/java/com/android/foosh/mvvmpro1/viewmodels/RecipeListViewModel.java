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

    // empty Constructor
    public RecipeListViewModel() {
        Log.d("LifeCycle:","RecipeListViewModel,Constructor");
        mRecipeRepository = RecipeRepository.getInstance();

    }


    public LiveData<List<Recipe>> getRecipes() {

        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query,int pageNumber){
        mRecipeRepository.searchRecipesApi(query,pageNumber);
    }
}
