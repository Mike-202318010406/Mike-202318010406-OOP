/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.zoomanagementsystem;

/**
 * Logistics Class - Manages transportation details for animal movements
 * @author ashongtical
 */
public class Logistics {
    private Item vehicle;
    private Item fuel;
    private String[] caretakers;
    private double totalPrice;
     private String name;

    public Logistics(Item vehicle, Item fuel, String[] caretakers) {
        this.vehicle = vehicle;
        this.fuel = fuel;
        this.caretakers = caretakers;
        this.totalPrice = vehicle.getPrice() + fuel.getPrice();
       
        
       
    }

    public void display() {
        
        System.out.println("-----Logistics Details-----");
        System.out.println("Vehicle Information:");
        vehicle.display();
        System.out.println("Fuel Information:");
        fuel.display();
        System.out.print("Caretakers: ");
        
        for (int i = 0; i < caretakers.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(caretakers[i]);
        }
        System.out.println();
        System.out.println("Total Logistics Cost:$"+totalPrice);
        System.out.println("----------------------------------");
        System.out.println("        ");
        
        System.out.println("**********************************");
    }
    
}
