package com.android.foosh.mvvmpro1.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.foosh.mvvmpro1.R;
import com.android.foosh.mvvmpro1.models.Recipe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecipeRecyclerAdapter";
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;


    public RecipeRecyclerAdapter( OnRecipeListener mOnRecipeListener) {

        this.mOnRecipeListener = mOnRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {

            case RECIPE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }

            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        Log.d("View Pos","itemViewType="+itemViewType);
        if(itemViewType == RECIPE_TYPE) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(position).getImage_url())
                    .into(((RecipeViewHolder)holder).image);

            ((RecipeViewHolder)holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder)holder).publisher.setText(mRecipes.get(position).getPublisher());
            ((RecipeViewHolder)holder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(position).getSocial_rank())));
        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.d("View Pos","getItemViewType(position)="+position);
        if(mRecipes.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }
        else{
            return RECIPE_TYPE;
        }

    }

    //(2)
    public void displayLoading(){
        Log.d(TAG,"displayLoading:Called...!");
        if(!isLoading()){
            Log.d(TAG,"displayLoading:Called,Enter (isLoading),");
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            // add he one obj to the mRecipes List
            mRecipes = loadingList;
            Log.d(TAG,"notifyDataSetChanged,caled---------------");
            notifyDataSetChanged();
        }
    }

    //(3)
    private boolean isLoading(){
        Log.d(TAG,"isLoading:Called...!");
        boolean empty = mRecipes == null;
        Log.d(TAG,"(mRecipes == null)== "+empty);
        if(mRecipes != null){

            Log.d(TAG,"The size of mRecipes:"+mRecipes.size());
            Log.d("Adapter-inside:","The size of mRecipes:"+mRecipes);
            // for the second search the MRecipe will not be empty so we need to check if not have the title Loading
            // we need to retrun false
            if(mRecipes.size() > 0){
                int i=0;
                for(Recipe res:mRecipes){

                    Log.d("Titles=","The ("+i+") of mRecipes:"+res.getTitle());
                    i++;
                }
//                if(mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")){
//                    Log.d(TAG,"The (Title) of mRecipes:"+mRecipes.get(mRecipes.size() - 1).getTitle());
//                    return true;
//                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(mRecipes !=null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        Log.d(TAG,"setRecipes,called="+mRecipes.size());
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
