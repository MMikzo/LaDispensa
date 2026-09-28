package com.maddcore.ladispensa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View pantryCard = findViewById(R.id.cardPantry);
        View recipesCard = findViewById(R.id.cardRecipes);
        View settingsCard = findViewById(R.id.cardSettings);

        pantryCard.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    PantryActivity.class
            );

            startActivity(intent);
        });

        recipesCard.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RecipesActivity.class
            );

            startActivity(intent);
        });

        settingsCard.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });
    }
}