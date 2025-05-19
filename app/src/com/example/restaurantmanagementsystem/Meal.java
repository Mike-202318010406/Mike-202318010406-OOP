/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.restaurantmanagementsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Meal class for managing meals composed of ingredients
 * name: the meals name
 * Ingredients: list of ingredients
 * @author ashongtical
 */
public class Meal implements Priceable {
    private String name;
    private List<Ingredient> ingredients;
    
    public Meal(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
    
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    
    @Override
    public double getPrice() {
        double total = 0;
        for (Ingredient ingredient : ingredients) {
            total += ingredient.getPrice();
        }
        return total;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Meal: %s (RMB%.2f)\n", name, getPrice()));
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append("  - ").append(ingredient).append("\n");
        }
        return sb.toString();
    }
}