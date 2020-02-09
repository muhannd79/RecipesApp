package com.android.foosh.mvvmpro1.adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.android.foosh.mvvmpro1.models.Recipe;

import java.util.List;

class DiffutilHelper extends DiffUtil.Callback {

    List<Recipe> oldRecipes;
    List<Recipe> newRecipes;

    public DiffutilHelper(List<Recipe> oldRecipes, List<Recipe> newRecipes) {

        this.oldRecipes = oldRecipes;
        this.newRecipes =newRecipes;

    }

    @Override
    public int getOldListSize() {
        if(oldRecipes ==null){
            return 0;
        } else {
            return oldRecipes.size();
        }
    }

    @Override
    public int getNewListSize() {

        if(newRecipes ==null){
            return 0;
        } else {
            return newRecipes.size();
        }

    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipes.get(newItemPosition) == newRecipes.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return oldRecipes.get(newItemPosition) == newRecipes.get(newItemPosition);
    }
}


