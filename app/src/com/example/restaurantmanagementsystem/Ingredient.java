/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.restaurantmanagementsystem;
import java.util.*;
/**
 * Ingredient class for managing individual ingredients
 * name: The name of the ingredient
 * price: The price of the ingredient
 * @author ashongtical
 */
public class Ingredient implements Priceable {
    private String name;
    private double price;
    
    public Ingredient(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return String.format("%-20s RMB%.2f", name, price);
    }
}