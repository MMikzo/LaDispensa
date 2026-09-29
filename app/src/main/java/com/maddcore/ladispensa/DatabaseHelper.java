package com.maddcore.ladispensa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ladispensa.db";
    private static final int DATABASE_VERSION = 3;

    // Pantry table
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";

    // Recipe table
    private static final String TABLE_RECIPES = "recipes";
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_ITALIAN_NAME = "italian_name";
    private static final String RECIPE_MATCHING = "matching_ingredients";
    private static final String RECIPE_DISPLAY = "display_ingredients";
    private static final String RECIPE_INSTRUCTIONS = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createIngredientTable(db);
        createRecipeTable(db);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Version 2 introduced recipes.
        if (oldVersion < 2) {
            createRecipeTable(db);
            seedRecipes(db);
        }

        // Version 3 adds quantity and unit to pantry ingredients.
        // Existing ingredients are preserved.
        if (oldVersion < 3) {

            db.execSQL(
                    "ALTER TABLE " + TABLE_INGREDIENTS +
                            " ADD COLUMN " + COLUMN_QUANTITY +
                            " REAL NOT NULL DEFAULT 1"
            );

            db.execSQL(
                    "ALTER TABLE " + TABLE_INGREDIENTS +
                            " ADD COLUMN " + COLUMN_UNIT +
                            " TEXT NOT NULL DEFAULT 'item'"
            );
        }
    }

    private void createIngredientTable(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_CATEGORY + " TEXT NOT NULL, " +
                        COLUMN_QUANTITY + " REAL NOT NULL DEFAULT 1, " +
                        COLUMN_UNIT + " TEXT NOT NULL DEFAULT 'item')";

        db.execSQL(sql);
    }

    private void createRecipeTable(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                        RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME + " TEXT NOT NULL, " +
                        RECIPE_ITALIAN_NAME + " TEXT NOT NULL, " +
                        RECIPE_MATCHING + " TEXT NOT NULL, " +
                        RECIPE_DISPLAY + " TEXT NOT NULL, " +
                        RECIPE_INSTRUCTIONS + " TEXT NOT NULL)";

        db.execSQL(sql);
    }

    // ---------------- PANTRY CRUD ----------------

    public long addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_CATEGORY, ingredient.getCategory());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        return db.insert(TABLE_INGREDIENTS, null, values);
    }

    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COLUMN_ID));

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_NAME));

                String category = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_UNIT));

                ingredients.add(
                        new Ingredient(id, name, category, quantity, unit)
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        return ingredients;
    }

    public int updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_CATEGORY, ingredient.getCategory());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        return db.update(
                TABLE_INGREDIENTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{
                        String.valueOf(ingredient.getId())
                }
        );
    }

    public int deleteIngredient(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_INGREDIENTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // ---------------- RECIPE DATA ----------------

    private void addRecipe(SQLiteDatabase db,
                           String name,
                           String italianName,
                           String matchingIngredients,
                           String displayIngredients,
                           String instructions) {

        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, name);
        values.put(RECIPE_ITALIAN_NAME, italianName);
        values.put(RECIPE_MATCHING, matchingIngredients);
        values.put(RECIPE_DISPLAY, displayIngredients);
        values.put(RECIPE_INSTRUCTIONS, instructions);

        db.insert(TABLE_RECIPES, null, values);
    }

    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(
                db,
                "Garlic and Oil Spaghetti",
                "Spaghetti Aglio e Olio",
                "Spaghetti|Olive Oil|Garlic|Red Pepper Flakes|Parsley|Parmesan",
                "1 lb dry spaghetti\n" +
                        "4 tbsp extra-virgin olive oil\n" +
                        "6 cloves garlic, thinly sliced\n" +
                        "1/4 tsp red pepper flakes\n" +
                        "1/4 cup fresh parsley\n" +
                        "Freshly grated Parmesan",
                "Cook the spaghetti until al dente. " +
                        "Gently fry the sliced garlic in olive oil until fragrant. " +
                        "Add the red pepper flakes, then toss through the drained pasta. " +
                        "Finish with parsley and freshly grated Parmesan."
        );

        addRecipe(
                db,
                "Margherita Pizza",
                "Pizza Margherita",
                "Buffalo Mozzarella|Tomatoes|Basil|Olive Oil|Bread Flour|Salt|Fresh Yeast",
                "2 pieces buffalo mozzarella\n" +
                        "2 cups peeled tomatoes\n" +
                        "Fresh basil\n" +
                        "Extra-virgin olive oil\n" +
                        "4 cups bread flour\n" +
                        "2 tsp salt\n" +
                        "Fresh yeast",
                "Prepare a soft yeast dough and allow it to rise. " +
                        "Shape the dough, spread with crushed tomatoes and add mozzarella. " +
                        "Bake at high heat until the crust is golden, then finish with basil and olive oil."
        );

        addRecipe(
                db,
                "Roast Chicken, Potatoes and Mustard Sauce",
                "Pollo Arrosto con Patate",
                "Chicken|Potatoes|Butter|Shallot|Thyme|Rosemary|Mustard|Marjoram|Breadcrumbs|Grated Cheese|Red Wine|Olive Oil|Salt",
                "1 whole chicken\n" +
                        "18 oz potatoes\n" +
                        "1/2 cup butter\n" +
                        "1 shallot\n" +
                        "Thyme and rosemary\n" +
                        "Mustard\n" +
                        "Marjoram\n" +
                        "Breadcrumbs\n" +
                        "Grated cheese\n" +
                        "Red wine\n" +
                        "Extra-virgin olive oil\n" +
                        "Salt",
                "Season the chicken with herbs, olive oil and salt. " +
                        "Roast with the potatoes until golden and cooked through. " +
                        "Soften the shallot, add red wine and mustard, and reduce into a rich sauce. " +
                        "Serve the chicken and potatoes with the mustard sauce."
        );

        addRecipe(
                db,
                "Nonna Rosita's Little Pizzas",
                "Pizzette della Nonna Rosita",
                "Tomato Sauce|Buffalo Mozzarella|Bread Rolls|Rosemary|Olive Oil|Pepper|Salt",
                "1 lb 8 oz ready-made tomato sauce\n" +
                        "11 oz buffalo mozzarella\n" +
                        "20 small bread rolls\n" +
                        "4 sprigs rosemary\n" +
                        "Extra-virgin olive oil\n" +
                        "Pepper\n" +
                        "Salt",
                "Slice the bread rolls and arrange them on a baking tray. " +
                        "Spoon over tomato sauce and top with mozzarella. " +
                        "Season with rosemary, pepper and a little salt. " +
                        "Drizzle with olive oil and bake until crisp and bubbling."
        );

        addRecipe(
                db,
                "Pumpkin and Almond Strudel",
                "Strudel di Zucca e Mandorle",
                "Pumpkin|Almonds|Sugar|Phyllo Pastry|Butter|Lemon",
                "Pumpkin, cleaned and chopped\n" +
                        "Almonds with their skins\n" +
                        "Sugar, plus extra for finishing\n" +
                        "4 sheets phyllo pastry\n" +
                        "Butter\n" +
                        "1 lemon",
                "Cook the pumpkin until tender and lightly mash it. " +
                        "Combine with chopped almonds, sugar and lemon zest. " +
                        "Layer the phyllo with melted butter, add the filling and roll carefully. " +
                        "Bake until crisp and golden."
        );

        addRecipe(
                db,
                "Cheese and Pepper Pasta",
                "Cacio e Pepe",
                "Spaghetti|Pecorino Romano|Black Pepper|Salt",
                "Spaghetti\n" +
                        "Pecorino Romano\n" +
                        "Freshly ground black pepper\n" +
                        "Salt",
                "Cook the spaghetti until al dente and reserve some pasta water. " +
                        "Toast the black pepper briefly. " +
                        "Combine Pecorino with warm pasta water to make a smooth sauce, " +
                        "then toss with the spaghetti and pepper."
        );

        addRecipe(
                db,
                "Pasta with Tomato and Basil",
                "Pasta al Pomodoro",
                "Pasta|Tomatoes|Garlic|Olive Oil|Basil|Salt",
                "Pasta\n" +
                        "Ripe tomatoes\n" +
                        "Garlic\n" +
                        "Extra-virgin olive oil\n" +
                        "Fresh basil\n" +
                        "Salt",
                "Cook the pasta until al dente. " +
                        "Gently cook garlic in olive oil, add tomatoes and simmer into a simple sauce. " +
                        "Toss with the pasta and finish with fresh basil."
        );

        addRecipe(
                db,
                "Tomato Bruschetta",
                "Bruschetta al Pomodoro",
                "Bread|Tomatoes|Garlic|Basil|Olive Oil|Salt",
                "Rustic bread\n" +
                        "Fresh tomatoes\n" +
                        "Garlic\n" +
                        "Fresh basil\n" +
                        "Extra-virgin olive oil\n" +
                        "Salt",
                "Toast thick slices of bread until crisp. " +
                        "Rub lightly with garlic. " +
                        "Mix chopped tomatoes with basil, olive oil and salt, then spoon over the warm bread."
        );

        addRecipe(
                db,
                "Caprese Salad",
                "Insalata Caprese",
                "Tomatoes|Mozzarella|Basil|Olive Oil|Salt",
                "Ripe tomatoes\n" +
                        "Fresh mozzarella\n" +
                        "Fresh basil leaves\n" +
                        "Extra-virgin olive oil\n" +
                        "Salt",
                "Slice the tomatoes and mozzarella. " +
                        "Arrange them alternately with fresh basil leaves. " +
                        "Season lightly with salt and finish with extra-virgin olive oil."
        );

        addRecipe(
                db,
                "Spicy Tomato Penne",
                "Penne all'Arrabbiata",
                "Penne|Tomatoes|Garlic|Olive Oil|Red Pepper Flakes|Parsley|Salt",
                "Penne pasta\n" +
                        "Tomatoes\n" +
                        "Garlic\n" +
                        "Extra-virgin olive oil\n" +
                        "Red pepper flakes\n" +
                        "Parsley\n" +
                        "Salt",
                "Cook the penne until al dente. " +
                        "Fry garlic and red pepper flakes gently in olive oil. " +
                        "Add tomatoes and simmer, then toss with the pasta and finish with parsley."
        );

        addRecipe(
                db,
                "Lemon Spaghetti",
                "Spaghetti al Limone",
                "Spaghetti|Lemon|Parmesan|Butter|Black Pepper|Salt",
                "Spaghetti\n" +
                        "Fresh lemon\n" +
                        "Parmesan\n" +
                        "Butter\n" +
                        "Black pepper\n" +
                        "Salt",
                "Cook the spaghetti and reserve some pasta water. " +
                        "Melt butter and combine with lemon zest and juice. " +
                        "Toss through the pasta with Parmesan and enough pasta water to make a glossy sauce."
        );

        addRecipe(
                db,
                "Pea Pasta",
                "Pasta e Piselli",
                "Pasta|Peas|Onion|Olive Oil|Parmesan|Salt|Black Pepper",
                "Small pasta\n" +
                        "Peas\n" +
                        "Onion\n" +
                        "Extra-virgin olive oil\n" +
                        "Parmesan\n" +
                        "Salt\n" +
                        "Black pepper",
                "Soften the onion in olive oil, then add the peas. " +
                        "Cook the pasta and combine it with the peas and a little pasta water. " +
                        "Finish with Parmesan and black pepper."
        );

        addRecipe(
                db,
                "Herb Frittata",
                "Frittata alle Erbe",
                "Eggs|Parsley|Basil|Parmesan|Olive Oil|Salt|Black Pepper",
                "Eggs\n" +
                        "Fresh parsley\n" +
                        "Fresh basil\n" +
                        "Parmesan\n" +
                        "Olive oil\n" +
                        "Salt\n" +
                        "Black pepper",
                "Beat the eggs with Parmesan, chopped herbs, salt and pepper. " +
                        "Heat olive oil in a pan and add the mixture. " +
                        "Cook gently until almost set, then finish the top until golden."
        );

        addRecipe(
                db,
                "Mushroom Risotto",
                "Risotto ai Funghi",
                "Arborio Rice|Mushrooms|Onion|Butter|Parmesan|Vegetable Stock|Olive Oil|Salt",
                "Arborio rice\n" +
                        "Fresh mushrooms\n" +
                        "Onion\n" +
                        "Butter\n" +
                        "Parmesan\n" +
                        "Vegetable stock\n" +
                        "Extra-virgin olive oil\n" +
                        "Salt",
                "Cook the mushrooms until browned and set aside. " +
                        "Soften the onion, add the rice and toast briefly. " +
                        "Gradually add warm stock while stirring until the rice is creamy. " +
                        "Fold in the mushrooms, butter and Parmesan."
        );

        addRecipe(
                db,
                "Tuscan Bread Salad",
                "Panzanella",
                "Bread|Tomatoes|Cucumber|Red Onion|Basil|Olive Oil|Vinegar|Salt",
                "Day-old bread\n" +
                        "Ripe tomatoes\n" +
                        "Cucumber\n" +
                        "Red onion\n" +
                        "Fresh basil\n" +
                        "Extra-virgin olive oil\n" +
                        "Vinegar\n" +
                        "Salt",
                "Tear the bread into pieces and lightly soften it. " +
                        "Combine with chopped tomatoes, cucumber, red onion and basil. " +
                        "Dress with olive oil, vinegar and salt, then allow the flavours to mingle before serving."
        );
    }

    // ---------------- RECIPE READING ----------------

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_ITALIAN_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(RECIPE_ID));

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_NAME));

                String italianName = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_ITALIAN_NAME));

                String matching = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_MATCHING));

                String display = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_DISPLAY));

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_INSTRUCTIONS));

                List<String> matchingList =
                        Arrays.asList(matching.split("\\|"));

                recipes.add(
                        new Recipe(
                                id,
                                name,
                                italianName,
                                matchingList,
                                display,
                                instructions
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        return recipes;
    }

    // A recipe qualifies only when every required ingredient is present.
    public List<Recipe> getMatchingRecipes() {

        List<Ingredient> pantry = getAllIngredients();
        List<Recipe> recipes = getAllRecipes();
        List<Recipe> matches = new ArrayList<>();

        Set<String> pantryNames = new HashSet<>();

        for (Ingredient ingredient : pantry) {
            pantryNames.add(
                    normalise(ingredient.getName())
            );
        }

        for (Recipe recipe : recipes) {

            boolean completeMatch = true;

            for (String required :
                    recipe.getMatchingIngredients()) {

                if (!pantryNames.contains(
                        normalise(required))) {

                    completeMatch = false;
                    break;
                }
            }

            if (completeMatch) {
                matches.add(recipe);
            }
        }

        return matches;
    }

    private String normalise(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
