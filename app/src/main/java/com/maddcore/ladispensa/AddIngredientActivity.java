package com.maddcore.ladispensa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editCategory;

    private DatabaseHelper databaseHelper;

    private int ingredientId = -1;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editName = findViewById(R.id.editIngredientName);
        editCategory = findViewById(R.id.editIngredientCategory);

        Button saveButton =
                findViewById(R.id.buttonSaveIngredient);

        TextView title =
                findViewById(R.id.textAddIngredientTitle);

        databaseHelper = new DatabaseHelper(this);

        // Check whether an existing ingredient is being edited.
        if (getIntent().hasExtra("ingredient_id")) {

            editMode = true;

            ingredientId =
                    getIntent().getIntExtra(
                            "ingredient_id", -1);

            String name =
                    getIntent().getStringExtra(
                            "ingredient_name");

            String category =
                    getIntent().getStringExtra(
                            "ingredient_category");

            editName.setText(name);
            editCategory.setText(category);

            title.setText("MODIFICA INGREDIENTE");
            saveButton.setText("SAVE CHANGES");
        }

        saveButton.setOnClickListener(v ->
                saveIngredient());
    }

    private void saveIngredient() {

        String name =
                editName.getText().toString().trim();

        String category =
                editCategory.getText().toString().trim();

        // Validation
        if (name.isEmpty()) {

            editName.setError(
                    "Enter an ingredient name");

            editName.requestFocus();
            return;
        }

        if (category.isEmpty()) {

            editCategory.setError(
                    "Enter a category");

            editCategory.requestFocus();
            return;
        }

        if (editMode) {

            Ingredient ingredient =
                    new Ingredient(
                            ingredientId,
                            name,
                            category
                    );

            int result =
                    databaseHelper.updateIngredient(
                            ingredient);

            if (result > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Ingredient could not be updated",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            Ingredient ingredient =
                    new Ingredient(name, category);

            long result =
                    databaseHelper.addIngredient(
                            ingredient);

            if (result != -1) {

                Toast.makeText(
                        this,
                        name + " added to your pantry",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Ingredient could not be saved",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}