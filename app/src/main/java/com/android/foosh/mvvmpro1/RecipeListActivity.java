package com.android.foosh.mvvmpro1;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.foosh.mvvmpro1.adapters.OnRecipeListener;
import com.android.foosh.mvvmpro1.adapters.RecipeRecyclerAdapter;
import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.requests.RecipeApi;
import com.android.foosh.mvvmpro1.requests.ServiceGenerator;
import com.android.foosh.mvvmpro1.requests.responses.RecipeSearchResponse;
import com.android.foosh.mvvmpro1.util.Constans;
import com.android.foosh.mvvmpro1.util.Printing;
import com.android.foosh.mvvmpro1.util.VerticalSpacingItemDecorator;
import com.android.foosh.mvvmpro1.viewmodels.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter mAdapter;

    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView =findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);
        Log.d("LifeCycle:", "onCreate");
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();

        if(!mRecipeListViewModel.ismIsViewingRecipes()){
            //display search category
            displaySearchCategories();
        }

        // assosiate
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        VerticalSpacingItemDecorator decorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(decorator);

    }
    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d(TAG, "simsim");
                if (recipes != null) {
                    if(mRecipeListViewModel.ismIsViewingRecipes()){

                        //that mean the query complete so we need to put false;
                        mRecipeListViewModel.setmIsPerformingQuery(false);

                        // Printing.printRecipes(TAG, recipes);
                        // Log.d(TAG, "if,");
                        mAdapter.setRecipes(recipes);
                    }

                }

            }
        });
    }

    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG,"displayLoading,called From Activites..!");
                //(1)
                mAdapter.displayLoading();
                searchRecipesApi(query, 1);
                mSearchView.clearFocus(); // so when we click on back button it will stop the query (because the first click on back it close the focus)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchRecipesApi(String query, int pageNumber) {
        mRecipeListViewModel.searchRecipesApi(query, pageNumber);
    }


    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mRecipeListViewModel.searchRecipesApi(category, 1);
        mSearchView.clearFocus();
    }

    private void displaySearchCategories(){
        mRecipeListViewModel.setmIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if(mRecipeListViewModel.onBackPressed()){
            // if true close the App (That mean we are viewing the Categories)
            super.onBackPressed();
        } else{
            displaySearchCategories();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_categories){
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    private void testRetrofitrequest() {
//
//        final RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
//
//        Call<RecipeSearchResponse> recipeListActivityCall = recipeApi.
//                searchRecipe(Constans.API_KEY, "chicken breast", "1");
//
//        recipeListActivityCall.enqueue(new Callback<RecipeSearchResponse>() {
//            @Override
//            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
//
//                Log.d(TAG, "onResponse: server response:" + response.toString());
//
//                if (response.code() == 200 && response.body() != null) {
//
//                    Log.d(TAG, "onResponse:" + response.body().toString());
//                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
//                    for (Recipe recipe : recipes) {
//                        Log.d(TAG, "For Loop:" + recipe.getTitle());
//                    }
//
//                } else {
//                    try {
//                        Log.d(TAG, "Response:" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
//
//            }
//        });
//
//
//    }
}
