package com.maddcore.ladispensa;

import java.util.List;

public class Recipe {

    private final int id;
    private final String name;
    private final String italianName;

    // Simple ingredient names used by the pantry matching system.
    private final List<String> matchingIngredients;

    // Full ingredient list shown on the recipe detail screen.
    private final String displayIngredients;

    private final String instructions;

    public Recipe(int id,
                  String name,
                  String italianName,
                  List<String> matchingIngredients,
                  String displayIngredients,
                  String instructions) {

        this.id = id;
        this.name = name;
        this.italianName = italianName;
        this.matchingIngredients = matchingIngredients;
        this.displayIngredients = displayIngredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItalianName() {
        return italianName;
    }

    public List<String> getMatchingIngredients() {
        return matchingIngredients;
    }

    public String getDisplayIngredients() {
        return displayIngredients;
    }

    public String getInstructions() {
        return instructions;
    }
}