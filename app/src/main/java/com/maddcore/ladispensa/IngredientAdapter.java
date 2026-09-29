package com.maddcore.ladispensa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredients;
    private final Context context;
    private final Runnable refreshPantry;

    public IngredientAdapter(Context context,
                             List<Ingredient> ingredients,
                             Runnable refreshPantry) {

        this.context = context;
        this.ingredients = ingredients;
        this.refreshPantry = refreshPantry;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());

        String quantityText;

        if (ingredient.getQuantity()
                == Math.floor(ingredient.getQuantity())) {

            quantityText =
                    String.valueOf((int) ingredient.getQuantity());

        } else {

            quantityText =
                    String.valueOf(ingredient.getQuantity());
        }

        holder.category.setText(
                ingredient.getCategory()
                        + "  •  "
                        + quantityText
                        + " "
                        + ingredient.getUnit()
        );

        // EDIT
        holder.edit.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    AddIngredientActivity.class
            );

            intent.putExtra(
                    "ingredient_id",
                    ingredient.getId()
            );

            intent.putExtra(
                    "ingredient_name",
                    ingredient.getName()
            );

            intent.putExtra(
                    "ingredient_category",
                    ingredient.getCategory()
            );

            intent.putExtra(
                    "ingredient_quantity",
                    ingredient.getQuantity()
            );

            intent.putExtra(
                    "ingredient_unit",
                    ingredient.getUnit()
            );

            context.startActivity(intent);
        });

        // DELETE
        holder.delete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Remove ingredient?")
                    .setMessage(
                            "Remove "
                                    + ingredient.getName()
                                    + " from your pantry?"
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                DatabaseHelper databaseHelper =
                                        new DatabaseHelper(context);

                                int result =
                                        databaseHelper.deleteIngredient(
                                                ingredient.getId()
                                        );

                                if (result > 0) {
                                    refreshPantry.run();
                                }
                            })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView name;
        TextView category;
        TextView edit;
        TextView delete;

        public IngredientViewHolder(
                @NonNull View itemView) {

            super(itemView);

            name = itemView.findViewById(
                    R.id.textIngredientName
            );

            category = itemView.findViewById(
                    R.id.textIngredientCategory
            );

            edit = itemView.findViewById(
                    R.id.buttonEditIngredient
            );

            delete = itemView.findViewById(
                    R.id.buttonDeleteIngredient
            );
        }
    }
}