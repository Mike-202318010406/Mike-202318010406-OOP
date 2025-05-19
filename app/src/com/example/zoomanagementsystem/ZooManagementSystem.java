/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.zoomanagementsystem;

/**
 *
 * @author ashongtical
 */

//not lectured on access specifiers and modifiers yet

import java.util.Scanner;

// Main Class - Program starts execution here

public class ZooManagementSystem {
    public static void main(String[] args) {
        // Create scanner for user input
        Scanner input = new Scanner(System.in);
        
        // Create two zoo instances as required
        Zoo southernZone = new Zoo("Southern-Zone Zoo");
        Zoo northernZone = new Zoo("Northern-Zone Zoo");
        
        // Add 5 animals to Southern-Zone Zoo
        southernZone.addAnimal(new Animal("Simba", "African Lion", 6));
        southernZone.addAnimal(new Animal("Dumbo", "African Elephant", 12));
        southernZone.addAnimal(new Animal("Luna", "Gray Wolf", 4));
        southernZone.addAnimal(new Animal("Poe", "Raven", 3));
        southernZone.addAnimal(new Animal("Benny", "Grizzly Bear", 8));
        
        // Add 5 animals to Northern-Zone Zoo
        northernZone.addAnimal(new Animal("Arctic", "Polar Bear", 7));
        northernZone.addAnimal(new Animal("Blizzard", "Snow Leopard", 5));
        northernZone.addAnimal(new Animal("Frost", "Arctic Fox", 3));
        northernZone.addAnimal(new Animal("Penguin", "Emperor Penguin", 4));
        northernZone.addAnimal(new Animal("Aurora", "Caribou", 6));
        
        // Display all animals in both zoos
        southernZone.displayAnimals();
        northernZone.displayAnimals();
        
        // Interactive menu for zoo management
        boolean running = true;
        while (running) {
            // Display menu options
            System.out.println("\n===== ZOO MANAGEMENT SYSTEM =====");
            System.out.println("1. Display all animals in Southern-Zone Zoo");
            System.out.println("2. Display all animals in Northern-Zone Zoo");
            System.out.println("3. Move an animal between zoos");
            System.out.println("4. Add a new animal to a zoo");
            System.out.println("5. Remove an animal from a zoo");
            System.out.println("6. Find an animal");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            // Get user choice
            int choice = input.nextInt();
            
            
            // Process user choice - instead of helper methods, functionality is directly in the switch
            switch (choice) {
                case 1:
                    southernZone.displayAnimals();
                    break;
                    
                case 2:
                    northernZone.displayAnimals();
                    break;
                    
                case 3:
                    // Move animal between zoos - inline implementation
                    System.out.println("\n----- MOVE ANIMAL BETWEEN ZOOS -----");
                    System.out.println("1. Move from Southern-Zone to Northern-Zone");
                    System.out.println("2. Move from Northern-Zone to Southern-Zone");
                    System.out.print("Enter your choice: ");
                    
                    // Determine source and destination zoos based on user choice
                    int moveChoice = input.nextInt();
                    input.nextLine(); // Clear buffer
                    
                    Zoo from = (moveChoice == 1) ? southernZone : northernZone;
                    Zoo to = (moveChoice == 1) ? northernZone : southernZone;
                    
                    // Show available animals in source zoo
                    System.out.println("Available animals in " + from.getName() + ":");
                    from.displayAnimals();
                    
                    // Check if source zoo has animals to move
                    if (from.getCounter() == 0) {
                        System.out.println("No animals available to move.");
                        break;
                    }
                    
                    // Get animal name to move
                    System.out.print("Enter the name of the animal to move: ");
                    String animalName = input.nextLine();
                    
                    // Check if animal exists in source zoo
                    int index = from.findAnimal(animalName);
                    if (index != -1) {
                        // Setup logistics for the move
                        System.out.println("\nSetting up logistics for the move...");
                        
                        // Create vehicle item
                        Item vehicle = new Item("Transport Truck", "VEH_001");
                        System.out.print("Enter vehicle cost: RMB");
                        vehicle.setPrice(input.nextDouble());
                        
                        
                        // Create fuel item
                        Item fuel = new Item("Diesel Fuel", "FUEL_001");
                        System.out.print("Enter fuel cost: RMB");
                        fuel.setPrice(input.nextDouble());
                        
                        
                        // Setup caretakers
                        int numCaretakers;
                        // Ensure number is between 1-3
                        do {
                            System.out.print("Enter the number of caretakers (between 1 and 3): ");
                            numCaretakers = input.nextInt();
             

                            if (numCaretakers < 1 || numCaretakers > 3) {
                                System.out.println("Invalid input! Please enter a number between 1 and 3.");
                            }
                        } while (numCaretakers < 1 || numCaretakers > 3);

                        System.out.println("Number of caretakers set to: " + numCaretakers);
                        
                        // Get caretaker names
                        String[] caretakers = new String[numCaretakers];
                        for (int i = 0; i < numCaretakers; i++) {
                            System.out.print("Enter name of caretaker " + (i+1) + ": ");
                            //separate caretaker names with comma
                            caretakers[i] = input.nextLine();
                        }
                        
                        // Create logistics object with all components
                        // vehicle and fuel are item types
                        Logistics logistics = new Logistics(vehicle, fuel, caretakers);
                        
                        // Move the animal from source to destination zoo
                        from.moveAnimal(animalName, to, logistics);
                        
                        // Display updated zoo information
                        from.displayAnimals();
                        to.displayAnimals();
                    } else {
                        System.out.println("--- Animal '" + animalName + "' not found in " + from.getName());
                    }
                    break;
                    
                case 4:
                    // Add new animal - inline implementation
                    System.out.println("\n----- ADD NEW ANIMAL -----");
                    System.out.println("1. Add to Southern-Zone Zoo");
                    System.out.println("2. Add to Northern-Zone Zoo");
                    System.out.print("Enter your choice: ");
                    
                    // Determine which zoo to add to
                    int addChoice = input.nextInt();
                    
                    
                    Zoo selectedZooForAdd = (addChoice == 1) ? southernZone : northernZone;
                    
                    // Get animal details from user
                    System.out.print("Enter animal name: ");
                    String name = input.next();
                    
                    System.out.print("Enter animal species: ");
                    String species = input.next();
                    
                    System.out.print("Enter animal age: ");
                    int age = input.nextInt();
                    
                    
                    // Create and add the new animal
                    Animal newAnimal = new Animal(name, species, age);
                    selectedZooForAdd.addAnimal(newAnimal);
                    
                    // Display updated zoo
                    selectedZooForAdd.displayAnimals();
                    break;
                    
                case 5:
                    // Remove animal - inline implementation
                    System.out.println("\n----- REMOVE ANIMAL -----");
                    System.out.println("1. Remove from Southern-Zone Zoo");
                    System.out.println("2. Remove from Northern-Zone Zoo");
                    System.out.print("Enter your choice: ");
                    
                    // Determine which zoo to remove from
                    int removeChoice = input.nextInt();
                    input.nextLine(); // Clear buffer
                    
                    Zoo selectedZooForRemove = (removeChoice == 1) ? southernZone : northernZone;
                    
                    // Show available animals
                    selectedZooForRemove.displayAnimals();
                    
                    // Check if zoo has animals to remove
                    if (selectedZooForRemove.getCounter() == 0) {
                        System.out.println("No animals to remove.");
                        break;
                    }
                    
                    // Get animal name to remove
                    System.out.print("Enter the name of the animal to remove: ");
                    String animalToRemove = input.nextLine();
                    
                    // Remove the animal and display updated zoo
                    selectedZooForRemove.deleteAnimal(animalToRemove);
                    selectedZooForRemove.displayAnimals();
                    break;
                    
                case 6:
                    // Find animal - inline implementation
                    System.out.println("\n----- FIND ANIMAL -----");
                    System.out.print("Enter the name of the animal to find: ");
                    String animalToFind = input.next();
                    
                    
                    // Search for animal in both zoos
                    int indexSouthern = southernZone.findAnimal(animalToFind);
                    int indexNorthern = northernZone.findAnimal(animalToFind);
                    
                    // Display results based on where (if) animal was found
                    if (indexSouthern != -1) {
                        System.out.println("Animal found in Southern-Zone Zoo:");
                        southernZone.getAnimal(indexSouthern).display();
                    } else if (indexNorthern != -1) {
                        System.out.println("Animal found in Northern-Zone Zoo:");
                        northernZone.getAnimal(indexNorthern).display();
                    } else {
                        System.out.println("Animal '" + animalToFind + "' not found in either zoo.");
                    }
                    break;
                    
                case 0:
                    running = false;
                    System.out.println("-----*****Exiting Zoo Management System. Goodbye!******-----");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}