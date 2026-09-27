package com.maddcore.ladispensa;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerRecipes;
    private TextView textNoRecipes;
    private TextView textRecipeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        textNoRecipes = findViewById(R.id.textNoRecipes);
        textRecipeCount = findViewById(R.id.textRecipeCount);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMatchingRecipes();
    }

    private void loadMatchingRecipes() {

        List<Recipe> recipes =
                databaseHelper.getMatchingRecipes();

        RecipeAdapter adapter =
                new RecipeAdapter(recipes);

        recyclerRecipes.setAdapter(adapter);

        int count = recipes.size();

        if (count == 1) {
            textRecipeCount.setText("1 recipe");
        } else {
            textRecipeCount.setText(count + " recipes");
        }

        if (recipes.isEmpty()) {

            textNoRecipes.setVisibility(View.VISIBLE);
            recyclerRecipes.setVisibility(View.GONE);

        } else {

            textNoRecipes.setVisibility(View.GONE);
            recyclerRecipes.setVisibility(View.VISIBLE);
        }
    }
}