package com.maddcore.ladispensa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (NavigationHelper.handleNavigation(this, item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}