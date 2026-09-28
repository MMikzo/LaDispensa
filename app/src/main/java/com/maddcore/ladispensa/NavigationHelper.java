package com.maddcore.ladispensa;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class NavigationHelper {

    public static boolean handleNavigation(Activity activity, MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openActivity(activity, MainActivity.class);
            return true;
        }

        if (id == R.id.nav_pantry) {
            openActivity(activity, PantryActivity.class);
            return true;
        }

        if (id == R.id.nav_recipes) {
            openActivity(activity, RecipesActivity.class);
            return true;
        }

        if (id == R.id.nav_settings) {
            openActivity(activity, SettingsActivity.class);
            return true;
        }

        return false;
    }

    private static void openActivity(
            Activity currentActivity,
            Class<?> destination) {

        if (currentActivity.getClass().equals(destination)) {
            return;
        }

        Intent intent = new Intent(currentActivity, destination);
        currentActivity.startActivity(intent);
    }
}