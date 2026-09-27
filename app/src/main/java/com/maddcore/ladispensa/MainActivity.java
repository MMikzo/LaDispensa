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

        pantryCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PantryActivity.class);
            startActivity(intent);
        });
    }
}