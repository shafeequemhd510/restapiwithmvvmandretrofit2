package com.example.restapiwithmvvmandretrofit2;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import android.app.SearchManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.example.restapiwithmvvmandretrofit2.adapters.OnRecipeListener;
import com.example.restapiwithmvvmandretrofit2.adapters.RecipeRecyclerAdapter;
import com.example.restapiwithmvvmandretrofit2.models.Recipe;
import com.example.restapiwithmvvmandretrofit2.room.AppData;
import com.example.restapiwithmvvmandretrofit2.viewmodel.RecipeListViewModel;

import java.util.Iterator;
import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private Button btnGetRecipeFromDb;
    private RecipeRecyclerAdapter mAdapter;
    private SearchView mSearchView;
    private AppData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        db = Room.databaseBuilder(getApplicationContext(),
                AppData.class, "db_crm")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        mRecyclerView = findViewById(R.id.rv_recipe_list);
        btnGetRecipeFromDb = findViewById(R.id.btn_get_from_db);
        btnGetRecipeFromDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<Recipe> recipeList=db.recipeDao().getAllRecipe();
                Toast.makeText(RecipeListActivity.this, recipeList.size()+" items in db", Toast.LENGTH_SHORT).show();
                for (Iterator<Recipe> iterator = recipeList.iterator(); iterator.hasNext(); ) {
                    Recipe recipe = iterator.next();
                    Log.d(TAG, "onClick: "+recipe.toString());
                }
            }
        });
//        mSearchView = findViewById(R.id.search_view);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        testRetrofitRequest();

        subscribeObservers();

        initRecyclerView();

        initSearchView();

    }

    private void initSearchView(){
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mRecipeListViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

  /*  private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mRecipeListViewModel.searchRecipesApi(s, 1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }*/

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) {
                    mAdapter.setRecipes(recipes);
                    db.recipeDao().insert(recipes);
                }
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void testRetrofitRequest() {
        mRecipeListViewModel.searchRecipesApi("chicken", 1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}

      /*  RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeSearchResponse>recipeSearchResponseCall =recipeApi.searchRecipe(Constants.API_KEY,
                "ital","1");

        recipeSearchResponseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {

                Toast.makeText(RecipeListActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: Server Response"+ response.toString());
                if (response.code()==200){
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    List<Recipe> recipeList= new ArrayList<>(response.body().getRecipes());
                    for (Recipe recipe:recipeList){
                        Log.d(TAG, "onResponse: "+recipe.getTitle());
                    }}
                    else
                    {
                        try {
                            Log.d(TAG, "onResponse: "+response.errorBody().string());
                        } catch (IOException e) {
                                    e.printStackTrace();
                        }
                    }
                }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

                Toast.makeText(RecipeListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });*/
      /*  RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeResponse>recipeResponseCall = recipeApi.getRecipe(Constants.API_KEY,
                "b269c4");

        recipeResponseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {

                Toast.makeText(RecipeListActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: Server Response"+ response.toString());
                if (response.code()==200){
                    Log.d(TAG, "onResponse: "+response.body().toString());
                   Recipe recipe= response.body().getRecipe();

                        Log.d(TAG, "onResponse:RETRIEVED RECIPE :"+recipe.toString());

                    }
                    else
                    {
                        try {
                            Log.d(TAG, "onResponse: "+response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

                Toast.makeText(RecipeListActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
*/


