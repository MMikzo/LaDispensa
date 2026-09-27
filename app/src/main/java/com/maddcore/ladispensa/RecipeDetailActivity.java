package com.maddcore.ladispensa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView italianName =
                findViewById(R.id.textDetailItalianName);

        TextView englishName =
                findViewById(R.id.textDetailEnglishName);

        TextView ingredients =
                findViewById(R.id.textDetailIngredients);

        TextView instructions =
                findViewById(R.id.textDetailInstructions);

        String recipeItalianName =
                getIntent().getStringExtra("italian_name");

        String recipeEnglishName =
                getIntent().getStringExtra("english_name");

        String recipeIngredients =
                getIntent().getStringExtra("ingredients");

        String recipeInstructions =
                getIntent().getStringExtra("instructions");

        italianName.setText(recipeItalianName);
        englishName.setText(recipeEnglishName);
        ingredients.setText(recipeIngredients);
        instructions.setText(recipeInstructions);
    }
}