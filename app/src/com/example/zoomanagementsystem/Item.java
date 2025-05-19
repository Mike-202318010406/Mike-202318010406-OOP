/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.zoomanagementsystem;

/**
 * Item Class - Represents objects used in logistics operations
 * @author ashongtical
 */
public class Item {
    private String name;
    private String code;
    private double price;
    

    public Item(String name, String code) {
        this.name = name;
        this.code = code;
        
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println("Name: " + name +  ", Price:$ " + price + ", Code: " + code );
        
    }
}


