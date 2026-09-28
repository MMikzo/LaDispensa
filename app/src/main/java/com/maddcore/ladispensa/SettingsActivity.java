package com.maddcore.ladispensa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "LaDispensaPreferences";

    private Switch switchVegetarian;
    private RadioGroup radioCookingLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up navigation toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        switchVegetarian = findViewById(R.id.switchVegetarian);
        radioCookingLevel = findViewById(R.id.radioCookingLevel);
        Button saveButton = findViewById(R.id.buttonSaveSettings);

        loadPreferences();

        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void loadPreferences() {

        SharedPreferences preferences =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean vegetarian =
                preferences.getBoolean("vegetarian", false);

        String cookingLevel =
                preferences.getString("cooking_level", "Beginner");

        switchVegetarian.setChecked(vegetarian);

        if ("Intermediate".equals(cookingLevel)) {

            radioCookingLevel.check(R.id.radioIntermediate);

        } else if ("Confident".equals(cookingLevel)) {

            radioCookingLevel.check(R.id.radioConfident);

        } else {

            radioCookingLevel.check(R.id.radioBeginner);
        }
    }

    private void savePreferences() {

        boolean vegetarian =
                switchVegetarian.isChecked();

        String cookingLevel = "Beginner";

        int selectedId =
                radioCookingLevel.getCheckedRadioButtonId();

        if (selectedId != -1) {

            RadioButton selectedButton =
                    findViewById(selectedId);

            cookingLevel =
                    selectedButton.getText().toString();
        }

        SharedPreferences preferences =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putBoolean(
                "vegetarian",
                vegetarian
        );

        editor.putString(
                "cooking_level",
                cookingLevel
        );

        editor.apply();

        Toast.makeText(
                this,
                "Preferences saved",
                Toast.LENGTH_SHORT
        ).show();
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