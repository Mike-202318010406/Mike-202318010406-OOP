/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.restaurantmanagementsystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Restaurant billing implementation that handles 
 * customer orders and bill calculation
 * @author ashongtical
 */
public class RestaurantBilling extends RestaurantBillingSystem {
    private List<Meal> order;
    private Map<String, Integer> mealQuantities;
    
    public RestaurantBilling() {
        super();
        this.order = new ArrayList<>();
        this.mealQuantities = new HashMap<>();
    }
    
    @Override
    public boolean addMealToOrder(String mealName) {
        Meal meal = findMealByName(mealName);
        if (meal != null) {
            order.add(meal);
            mealQuantities.put(mealName, mealQuantities.getOrDefault(mealName, 0) + 1);
            System.out.println("Added '" + mealName + "' to order.");
            return true;
        }
        System.out.println("Meal '" + mealName + "' not found in menu.");
        return false;
    }
    
    @Override
    public double calculateBill() {
        double total = 0;
        for (Meal meal : order) {
            total += meal.getPrice();
        }
        return total;
    }
    
    public void clearOrder() {
        order.clear();
        mealQuantities.clear();
        System.out.println("Order cleared.");
    }
    
    public void displayOrder() {
        if (order.isEmpty()) {
            System.out.println("No items in current order.");
            return;
        }
        
        System.out.println("\n===== CURRENT ORDER =====");
        for (Map.Entry<String, Integer> entry : mealQuantities.entrySet()) {
            Meal meal = findMealByName(entry.getKey());
            System.out.printf("%-20s %d x RMB%.2f = RMB%.2f\n", 
                meal.getName(), 
                entry.getValue(), 
                meal.getPrice(), 
                meal.getPrice() * entry.getValue());
        }
        System.out.println("--------------------------");
        System.out.printf("TOTAL: RMB%.2f\n", calculateBill());
        System.out.println("========================");
    }
    
    public String generateBill(String customerName) {
        StringBuilder bill = new StringBuilder();
        bill.append("\n===== RESTAURANT BILL =====\n");
        bill.append("Customer: ").append(customerName).append("\n\n");
        bill.append("Order Details:\n");
        
        for (Map.Entry<String, Integer> entry : mealQuantities.entrySet()) {
            Meal meal = findMealByName(entry.getKey());
            bill.append(String.format("%-20s %d x RMB%.2f = RMB%.2f\n", 
                meal.getName(), 
                entry.getValue(), 
                meal.getPrice(), 
                meal.getPrice() * entry.getValue()));
        }
        
        bill.append("--------------------------\n");
        bill.append(String.format("TOTAL: RMB%.2f\n", calculateBill()));
        bill.append("==========================\n");
        bill.append("Thank you for dining with us!");
        
        return bill.toString();
    }
    
    // 添加公共访问方法，供GUI使用
    public List<Meal> getOrder() {
        return order;
    }
    
    public Map<String, Integer> getMealQuantities() {
        return mealQuantities;
    }
}