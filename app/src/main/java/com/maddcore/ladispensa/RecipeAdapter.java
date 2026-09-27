package com.maddcore.ladispensa;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        holder.italianName.setText(
                recipe.getItalianName().toUpperCase()
        );

        holder.englishName.setText(
                recipe.getName()
        );

        int ingredientCount =
                recipe.getMatchingIngredients().size();

        holder.ingredientCount.setText(
                ingredientCount + " ingredients"
        );

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra(
                    "italian_name",
                    recipe.getItalianName()
            );

            intent.putExtra(
                    "english_name",
                    recipe.getName()
            );

            intent.putExtra(
                    "ingredients",
                    recipe.getDisplayIngredients()
            );

            intent.putExtra(
                    "instructions",
                    recipe.getInstructions()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView italianName;
        TextView englishName;
        TextView ingredientCount;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            italianName = itemView.findViewById(
                    R.id.textRecipeItalianName
            );

            englishName = itemView.findViewById(
                    R.id.textRecipeEnglishName
            );

            ingredientCount = itemView.findViewById(
                    R.id.textRecipeIngredientCount
            );
        }
    }
}