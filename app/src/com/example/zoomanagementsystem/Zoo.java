/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.zoomanagementsystem;

/**
 * Zoo Class - Manages a collection of animals
 * @author ashongtical
 */
public class Zoo {
    private String name;
    private Animal[] animals;
    private int counter;
    private int capacity = 10;
 
    
    

    public Zoo(String name) {
        this.name = name;
        
        this.animals = new Animal[10];
        this.counter = 0;
      
    }
  

    public String getName() {
        return name;
    }
    

    public int getCounter() {
        return counter;
    }

    public void addAnimal(Animal animal) {
        if (counter < 10) {
            animals[counter++] = animal;
           
        }
       
    }
   

    public void deleteAnimal(String animalName) {
        for (int i = 0; i < counter; i++) {
            if (animals[i].getName().equals(animalName)) {
                for (int j = i; j < counter - 1; j++) {
                    animals[j] = animals[j + 1];
                }
                counter--;
                break;
                
            }
            
        }
    }

    public int findAnimal(String animalName) {
        for (int i = 0; i < counter; i++) {
            if (animals[i].getName().equals(animalName)) {
                return i;
            }
            
        }
        return -1;
    }

    public Animal getAnimal(int index) {
        return animals[index];
    }

    public void moveAnimal(String animalName, Zoo to, Logistics logistics) {
        int index = findAnimal(animalName);
        if (index != -1) {
            Animal animal = animals[index];
            to.addAnimal(animal);
            deleteAnimal(animalName);
             System.out.println("********* ANIMAL TRANSFER INVOICE *********");
             System.out.println("From: Southern-Zone Zoo");
             System.out.println("To: Northern-Zone Zoo");
             System.out.println("Animal Details:");
            String species = animal.getSpecies(); // getSpecies
            int age = animal.getAge(); // get the age
            System.out.println("Animal Name: " + animalName + ", Species: " + species + ", Age: " + age);
            
             
   
            logistics.display();
             //System.out.println("**********************************");
        }
             System.out.println("Animal '" + animalName + "' added to " + name + ".");
             System.out.println("Animal '" + animalName + "' removed from " + name + ".");
             System.out.println("Animal '" + animalName + "' successfully moved from " + name + " to " +to.getName() + " .");
             System.out.println("**********************************");

    }
    
//dispaly
    public void displayAnimals() {
       
        System.out.println("--- Animals in " + name + " (" +counter + "/" + capacity + ") ---");
        
        for (int i = 0; i < counter; i++) {
            
            animals[i].display();
        }
        
        System.out.println("----------------------------------");
    }

    public String getCapacity() {
        return null;
    }

}
