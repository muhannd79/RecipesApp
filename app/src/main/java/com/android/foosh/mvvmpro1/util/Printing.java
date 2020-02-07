package com.android.foosh.mvvmpro1.util;

import android.util.Log;

import com.android.foosh.mvvmpro1.models.Recipe;

import java.util.List;

public class Printing {

    public static void printRecipes(String tag, List<Recipe> recipes){

        for (Recipe recipe:recipes){
            Log.d(tag,"printRecipes:"+recipe.getRecipe_id());
        }
    }
}
