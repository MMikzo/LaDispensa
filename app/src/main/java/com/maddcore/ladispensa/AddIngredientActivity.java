package com.maddcore.ladispensa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editCategory;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editName = findViewById(R.id.editIngredientName);
        editCategory = findViewById(R.id.editIngredientCategory);
        Button saveButton = findViewById(R.id.buttonSaveIngredient);

        databaseHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {

        String name = editName.getText().toString().trim();
        String category = editCategory.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Enter an ingredient name");
            editName.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            editCategory.setError("Enter a category");
            editCategory.requestFocus();
            return;
        }

        Ingredient ingredient = new Ingredient(name, category);
        long result = databaseHelper.addIngredient(ingredient);

        if (result != -1) {
            Toast.makeText(this,
                    name + " added to your pantry",
                    Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this,
                    "Ingredient could not be saved",
                    Toast.LENGTH_SHORT).show();
        }
    }
}