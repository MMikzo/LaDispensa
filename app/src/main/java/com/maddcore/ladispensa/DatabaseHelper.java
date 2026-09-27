package com.maddcore.ladispensa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ladispensa.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_CATEGORY + " TEXT NOT NULL)";

        db.execSQL(createIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }

    // Add a new ingredient to the pantry.
    public long addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_CATEGORY, ingredient.getCategory());

        return db.insert(TABLE_INGREDIENTS, null, values);
    }

    // Return every stored ingredient.
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

                ingredients.add(new Ingredient(id, name, category));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return ingredients;
    }

    // Update an ingredient using its database ID.
    public int updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_CATEGORY, ingredient.getCategory());

        return db.update(
                TABLE_INGREDIENTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())}
        );
    }

    // Delete an ingredient using its database ID.
    public int deleteIngredient(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_INGREDIENTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }
}