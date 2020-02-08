package com.android.foosh.mvvmpro1.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;

private RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance(){
        Log.d("LifeCycle:","RecipeRepository,getInstance");
        if(instance == null){
            instance = new RecipeRepository();
        }
        return  instance;
    }

    public RecipeRepository() {
        Log.d("LifeCycle:","RecipeRepository,Construcotre");
        mRecipeApiClient = RecipeApiClient.getInstance();

    }

   public   LiveData<List<Recipe>> getRecipes(){

        return  mRecipeApiClient.getRecipes();
    }
     public void searchRecipesApi(String query,int pageNumber){
        if(pageNumber==0){
            pageNumber=1;
        }
        mRecipeApiClient.searchRecipesApi(query,pageNumber);
     }

     public void cancelRequest(){
        mRecipeApiClient.cancelRequest();
     }
}
