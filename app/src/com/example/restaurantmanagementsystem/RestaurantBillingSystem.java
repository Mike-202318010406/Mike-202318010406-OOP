/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.restaurantmanagementsystem;

import java.util.ArrayList;

/**
 * Abstract class that provides structure 
 * for the billing system
 * @author ashongtical
 */
public abstract class RestaurantBillingSystem {
    
    // Constructor to initialize the menu
    public ArrayList<Meal> menu;

    public RestaurantBillingSystem() {
        this.menu = new ArrayList<>();
    }
   
    // Add a meal to the menu
    // meal: The meal to add
    public void addMeal(Meal meal) {
        menu.add(meal);
        System.out.println("Meal '" + meal.getName() + "' added to menu.");
    }
    
    // Remove a meal from the menu by name
    // mealName The name of the meal to remove
    // return true if meal was removed, false otherwise
    public boolean removeMeal(String mealName) {
        Meal meal = findMealByName(mealName);
        if (meal != null) {
            menu.remove(meal);
            System.out.println("Meal '" + mealName + "' removed from menu.");
            return true;
        }
        System.out.println("Meal '" + mealName + "' not found in menu.");
        return false;
    }
    
    // Display all meals on the menu
    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("Menu is currently empty.");
            return;
        }

        System.out.println("\n===== RESTAURANT MENU =====");
        for (Meal meal : menu) {
            System.out.println(meal);
        }
        System.out.println("==========================");
    }

    // Find a meal on the menu by name
    // mealName: The name of the meal to find
    // The meal if found, null otherwise
    public Meal findMealByName(String mealName) {
        for (Meal meal : menu) {
            if (meal.getName().equalsIgnoreCase(mealName)) {
                return meal;
            }
        }
        return null;
    }
    
    // Abstract method for adding a meal to an order
    // mealName The name of the meal to add to the order
    // return boolean indicating success or failure
    public abstract boolean addMealToOrder(String mealName);
    
    // Abstract method for calculating the bill
    // return The total bill amount
    public abstract double calculateBill();
}