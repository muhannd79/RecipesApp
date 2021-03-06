package com.android.foosh.mvvmpro1.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.foosh.mvvmpro1.AppExecutors;
import com.android.foosh.mvvmpro1.models.Recipe;
import com.android.foosh.mvvmpro1.requests.responses.RecipeSearchResponse;
import com.android.foosh.mvvmpro1.util.Constans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.android.foosh.mvvmpro1.util.Constans.NETWORK_TIMEOUT;

public class RecipeApiClient {

    private static final String TAG = "Muhannd";
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;

    public static RecipeApiClient getInstance() {
        Log.d("LifeCycle:","RecipeApiClient,getInstance");
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        Log.d("LifeCycle:","RecipeApiClient,Construcotre");
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {

        return mRecipes;
    }

    // The Goal of the Video 17 is to Call searchRecipesApi Method
    public void searchRecipesApi(String query,int pageNumber) {
        //this will  be set the timeout for the request

        if(mRetrieveRecipesRunnable !=null){
            // to create a New mRetrieveRecipesRunnable object completely
            mRetrieveRecipesRunnable =null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query,pageNumber);

        final Future handler = AppExecutors.get().networkIO().submit(mRetrieveRecipesRunnable);

        //How to implement the timeout

        AppExecutors.get().networkIO().schedule(new Runnable() {
            @Override
            //this is going to run after 3 second
            public void run() {

                //this is going to stop the request - let the user know is timed out
                handler.cancel(true);
            }
        },  NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

}


    // This Runnable that make the Search Query
    private class RetrieveRecipesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        //we will use this flag to cancel  the request Manually if the times is out for the Runnable
        private boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //This line of code will run in the Background Thread
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200 && response.body() != null) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1) {
                        // set these recipes to the live data
                        // we use post because we are working in backgroundThread
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.d(TAG, "run:" + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "run:" + e.toString());
                mRecipes.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {

            return ServiceGenerator.getRecipeApi().searchRecipe(Constans.API_KEY, query, String.valueOf(pageNumber));
        }

        private void CancelRequest() {
            Log.d(TAG, "Cancel the Request");
            cancelRequest = true;
        }
    }
}
