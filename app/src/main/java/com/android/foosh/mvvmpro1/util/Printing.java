package com.android.foosh.mvvmpro1.util;

import android.util.Log;

import com.android.foosh.mvvmpro1.models.Recipe;

import java.util.List;

public class Printing {

    public static void printRecipes(String tag, List<Recipe> recipes){

        int i=0;
        Log.d(tag, "The i ="+i);
        for (Recipe recipe:recipes){
         //   if(recipe.getTitle().contains("LOADING...")) {
                Log.d(tag, "printRecipes("+i+"):" + recipe.getTitle());
                i++;
        //    }
        }
    }
}
