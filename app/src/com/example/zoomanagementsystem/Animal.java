/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.zoomanagementsystem;

/**
 * Animal Class - Represents animals in the zoo
 * @author ashongtical
 */
public class Animal {
    private String name;
    private String species;
    private int age;

    public Animal(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public String getName() {
        return name;
    }
     public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public void display() {
        
        System.out.println("Name: " + name + ", Species: " + species + ", Age: " + age);
    }
    
}
