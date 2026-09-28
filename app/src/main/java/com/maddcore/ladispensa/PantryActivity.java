package com.maddcore.ladispensa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerPantry;
    private TextView textEmptyPantry;
    private TextView textItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        // Set up the main navigation toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        textEmptyPantry = findViewById(R.id.textEmptyPantry);
        textItemCount = findViewById(R.id.textItemCount);

        Button addIngredientButton =
                findViewById(R.id.buttonAddIngredient);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this));

        addIngredientButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void loadPantry() {

        List<Ingredient> ingredients =
                databaseHelper.getAllIngredients();

        IngredientAdapter adapter =
                new IngredientAdapter(
                        this,
                        ingredients,
                        this::loadPantry
                );

        recyclerPantry.setAdapter(adapter);

        int count = ingredients.size();

        if (count == 1) {
            textItemCount.setText("1 item");
        } else {
            textItemCount.setText(count + " items");
        }

        if (ingredients.isEmpty()) {

            textEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            textEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
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