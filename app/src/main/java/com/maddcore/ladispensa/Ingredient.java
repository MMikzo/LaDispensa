package com.maddcore.ladispensa;

public class Ingredient {

    private int id;
    private String name;
    private String category;
    private double quantity;
    private String unit;

    public Ingredient(int id, String name, String category,
                      double quantity, String unit) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name, String category,
                      double quantity, String unit) {

        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}