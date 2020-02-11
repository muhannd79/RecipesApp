package com.android.foosh.mvvmpro1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.viewmodels.RecipeViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RecipeActivity extends BaseActivity {

    private static final String TAG = "RecipeActivity";
    // UI components
    private AppCompatImageView mRecipeImage;
    private TextView mRecipeTitle, mRecipeRank;
    private LinearLayout mRecipeIngredientsContainer;
    private ScrollView mScrollView;

    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipeRank = findViewById(R.id.recipe_social_score);
        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container);
        mScrollView = findViewById(R.id.parent);
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        getIncomingIntent();
        subscribeObservers();
        showProgressBar(true);

    }



    private void getIncomingIntent() {

        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            mRecipeViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {

                    if(recipe.getRecipe_id().equals(mRecipeViewModel.getRecipeId())){
                        // to check we retrieve new Recipe
                        // Because The Activity recreated and the Old recipe from the previous Activity still be in the ViewModel

                        setRecipeProperties(recipe);
                        mRecipeViewModel.setRetrievedRecipe(true);
                    }

//                    Log.d(TAG,"onChanged: ---------------------------------");
//                    Log.d(TAG,"onChanged:,"+recipe.getTitle());
//                    int i =1;
//                    for(String reg:recipe.getIngredients()){
//
//                        Log.d(TAG,"("+i+")-"+reg);
//                                i++;
//                    }
                }
            }
        });

        mRecipeViewModel.isRecipeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean && !mRecipeViewModel.didRetrieveRecipe()){
                    Log.d(TAG, "onChanged: timed out..");

                    displayErrorScreen("Error retrieving data. Check network connection.");
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage){
        mRecipeTitle.setText("Error retrieveing recipe...");
        mRecipeRank.setText("");
        TextView textView = new TextView(this);
        if(!errorMessage.equals("")){
            textView.setText(errorMessage);
        }
        else{
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        mRecipeIngredientsContainer.addView(textView);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(mRecipeImage);

        showParent();
        showProgressBar(false);
    }

    private void setRecipeProperties(Recipe recipe) {
        if (recipe != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImage_url())
                    .into(mRecipeImage);

            mRecipeTitle.setText(recipe.getTitle());
            mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

            // remove all the previous item
            mRecipeIngredientsContainer.removeAllViews();

            for (String ingredient : recipe.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                mRecipeIngredientsContainer.addView(textView);
            }
        }

        showParent();
        showProgressBar(false);

    }

    private void showParent() {
        mScrollView.setVisibility(View.VISIBLE);
    }
}
