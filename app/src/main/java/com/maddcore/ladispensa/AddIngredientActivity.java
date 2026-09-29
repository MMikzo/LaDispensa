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
    private EditText editQuantity;
    private EditText editUnit;

    private DatabaseHelper databaseHelper;

    private int ingredientId = -1;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editName = findViewById(R.id.editIngredientName);
        editCategory = findViewById(R.id.editIngredientCategory);
        editQuantity = findViewById(R.id.editIngredientQuantity);
        editUnit = findViewById(R.id.editIngredientUnit);

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

            double quantity =
                    getIntent().getDoubleExtra(
                            "ingredient_quantity", 1.0);

            String unit =
                    getIntent().getStringExtra(
                            "ingredient_unit");

            editName.setText(name);
            editCategory.setText(category);

            if (quantity == Math.floor(quantity)) {
                editQuantity.setText(
                        String.valueOf((int) quantity));
            } else {
                editQuantity.setText(
                        String.valueOf(quantity));
            }

            if (unit != null) {
                editUnit.setText(unit);
            }

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

        String quantityText =
                editQuantity.getText().toString().trim();

        String unit =
                editUnit.getText().toString().trim();

        // Name validation
        if (name.isEmpty()) {

            editName.setError(
                    "Enter an ingredient name");

            editName.requestFocus();
            return;
        }

        // Category validation
        if (category.isEmpty()) {

            editCategory.setError(
                    "Enter a category");

            editCategory.requestFocus();
            return;
        }

        // Quantity validation
        if (quantityText.isEmpty()) {

            editQuantity.setError(
                    "Enter a quantity");

            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            editQuantity.setError(
                    "Enter a valid quantity");

            editQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {

            editQuantity.setError(
                    "Quantity must be greater than 0");

            editQuantity.requestFocus();
            return;
        }

        // Unit validation
        if (unit.isEmpty()) {

            editUnit.setError(
                    "Enter a unit");

            editUnit.requestFocus();
            return;
        }

        if (editMode) {

            Ingredient ingredient =
                    new Ingredient(
                            ingredientId,
                            name,
                            category,
                            quantity,
                            unit
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
                    new Ingredient(
                            name,
                            category,
                            quantity,
                            unit
                    );

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