package com.maddcore.ladispensa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        Button addIngredientButton = findViewById(R.id.buttonAddIngredient);

        addIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(PantryActivity.this, AddIngredientActivity.class);
            startActivity(intent);
        });
    }
}