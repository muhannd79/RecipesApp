package com.android.foosh.mvvmpro1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.foosh.mvvmpro1.R;
import com.android.foosh.mvvmpro1.models.Recipe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;


    public RecipeRecyclerAdapter( OnRecipeListener mOnRecipeListener) {

        this.mOnRecipeListener = mOnRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item,parent,false);

        return new RecipeViewHolder(view,mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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

    @Override
    public int getItemCount() {
        if(mRecipes !=null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        if(recipes !=null){
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffutilHelper(mRecipes,recipes));
            mRecipes = recipes;
            //notifyDataSetChanged();
            diffResult.dispatchUpdatesTo(this);
        }

    }
}
