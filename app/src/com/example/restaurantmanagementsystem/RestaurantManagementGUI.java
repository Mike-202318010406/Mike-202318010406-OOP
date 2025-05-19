package com.example.restaurantmanagementsystem;

import com.example.restaurantmanagementsystem.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class RestaurantManagementGUI extends JFrame {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final RestaurantManagementSystem system;
    private final RestaurantBilling billing;
    
    // Table models
    private DefaultTableModel ingredientModel;
    private DefaultTableModel mealModel;
    private DefaultTableModel menuModel;
    private DefaultTableModel orderModel;

    public RestaurantManagementGUI() {
        system = new RestaurantManagementSystem();
        billing = system.getBilling();
        
        setTitle("Restaurant Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize table models
        initTableModels();
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Ingredients", createIngredientPanel());
        tabbedPane.addTab("Meals", createMealPanel());
        tabbedPane.addTab("Menu", createMenuPanel());
        tabbedPane.addTab("Orders", createOrderPanel());

        add(tabbedPane);
    }
    
    private void initTableModels() {
        ingredientModel = new DefaultTableModel(new Object[]{"Name", "Price"}, 0);
        mealModel = new DefaultTableModel(new Object[]{"Name", "Price", "Ingredients"}, 0);
        menuModel = new DefaultTableModel(new Object[]{"Name", "Price", "Ingredients"}, 0);
        orderModel = new DefaultTableModel(new Object[]{"Name", "Quantity", "Subtotal"}, 0);
    }

    // ===================== Ingredients Management Panel =====================
    private JPanel createIngredientPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(ingredientModel);
        updateIngredientTable();

        JButton addBtn = new JButton("Add Ingredient");
        JButton removeBtn = new JButton("Remove Ingredient");
        
        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter ingredient name:");
            if (name == null || name.isEmpty()) return;
            
            try {
                double price = Double.parseDouble(
                    JOptionPane.showInputDialog(this, "Enter price:")
                );
                if (price < 0) throw new NumberFormatException();
                
                system.getIngredientInventory().put(name, new Ingredient(name, price));
                updateIngredientTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        removeBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) ingredientModel.getValueAt(selectedRow, 0);
                system.getIngredientInventory().remove(name);
                updateIngredientTable();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateIngredientTable() {
        ingredientModel.setRowCount(0);
        system.getIngredientInventory().forEach((name, ingredient) ->
            ingredientModel.addRow(new Object[]{name, df.format(ingredient.getPrice())}));
    }

    // ===================== Meals Management Panel =====================
    private JPanel createMealPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(mealModel);
        updateMealTable();

        JButton createBtn = new JButton("Create New Meal");
        JButton addToMenuBtn = new JButton("Add to Menu");
        
        createBtn.addActionListener(e -> {
            if (system.getIngredientInventory().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add ingredients first", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String name = JOptionPane.showInputDialog(this, "Enter meal name:");
            if (name == null || name.isEmpty()) return;
            
            Meal newMeal = new Meal(name);
            
            // Create dialog to select ingredients
            Object[] options = system.getIngredientInventory().keySet().toArray();
            JList<Object> list = new JList<>(options);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            int result = JOptionPane.showConfirmDialog(
                this, 
                new JScrollPane(list), 
                "Select Ingredients", 
                JOptionPane.OK_CANCEL_OPTION
            );
            
            if (result == JOptionPane.OK_OPTION) {
                for (int index : list.getSelectedIndices()) {
                    String ingredientName = (String) options[index];
                    newMeal.addIngredient(system.getIngredientInventory().get(ingredientName));
                }
                
                system.getMealsList().add(newMeal);
                updateMealTable();
            }
        });
        
        addToMenuBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Meal selectedMeal = system.getMealsList().get(selectedRow);
                billing.addMeal(selectedMeal);
                updateMenuTable();
                JOptionPane.showMessageDialog(this, "Meal added to menu", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createBtn);
        buttonPanel.add(addToMenuBtn);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateMealTable() {
        mealModel.setRowCount(0);
        system.getMealsList().forEach(meal -> {
            StringBuilder ingredients = new StringBuilder();
            meal.getIngredients().forEach(ingredient -> 
                ingredients.append(ingredient.getName()).append(", ")
            );
            if (ingredients.length() > 0) {
                ingredients.delete(ingredients.length() - 2, ingredients.length());
            }
            mealModel.addRow(new Object[]{meal.getName(), df.format(meal.getPrice()), ingredients});
        });
    }

    // ===================== Menu Management Panel =====================
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(menuModel);
        updateMenuTable();

        JButton removeBtn = new JButton("Remove from Menu");
        removeBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String mealName = (String) menuModel.getValueAt(selectedRow, 0);
                billing.removeMeal(mealName);
                updateMenuTable();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(removeBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void updateMenuTable() {
        menuModel.setRowCount(0);
        billing.menu.forEach(meal -> {
            StringBuilder ingredients = new StringBuilder();
            meal.getIngredients().forEach(ingredient -> 
                ingredients.append(ingredient.getName()).append(", ")
            );
            if (ingredients.length() > 0) {
                ingredients.delete(ingredients.length() - 2, ingredients.length());
            }
            menuModel.addRow(new Object[]{meal.getName(), df.format(meal.getPrice()), ingredients});
        });
    }

    // ===================== Order Management Panel =====================
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menu display area
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        
        JTable menuTable = new JTable(menuModel);
        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);

        // Order display area
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Current Order"));
        
        JTable orderTable = new JTable(orderModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);

        // Action buttons
        JButton addBtn = new JButton("Add to Order");
        JButton generateBtn = new JButton("Generate Bill");
        JButton clearBtn = new JButton("Clear Order");
        
        addBtn.addActionListener(e -> {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow >= 0) {
                String mealName = (String) menuModel.getValueAt(selectedRow, 0);
                billing.addMealToOrder(mealName);
                updateOrderTable();
            }
        });
        
        generateBtn.addActionListener(e -> {
            String customerName = JOptionPane.showInputDialog(this, "Enter customer name:");
            if (customerName != null && !customerName.isEmpty()) {
                String bill = billing.generateBill(customerName);
                JTextArea billArea = new JTextArea(bill);
                billArea.setEditable(false);
                JOptionPane.showMessageDialog(
                    this, 
                    new JScrollPane(billArea), 
                    "Order Bill", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                if (JOptionPane.showConfirmDialog(
                    this, 
                    "Clear order?", 
                    "Confirmation", 
                    JOptionPane.YES_NO_OPTION
                ) == JOptionPane.YES_OPTION) {
                    billing.clearOrder();
                    updateOrderTable();
                }
            }
        });
        
        clearBtn.addActionListener(e -> {
            billing.clearOrder();
            updateOrderTable();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(generateBtn);
        buttonPanel.add(clearBtn);

        // Combine panels
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(menuPanel);
        topPanel.add(orderPanel);

        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateOrderTable() {
        orderModel.setRowCount(0);
        
        billing.getMealQuantities().forEach((name, count) -> {
            Meal meal = billing.findMealByName(name);
            if (meal != null) {
                double total = meal.getPrice() * count;
                orderModel.addRow(new Object[]{name, count, df.format(total)});
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantManagementGUI().setVisible(true));
    }
}