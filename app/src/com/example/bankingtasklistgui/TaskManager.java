/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bankingtasklistgui;
import java.util.*;
/**
 *  use ArrayList to set the priority
 * @author ashongtical
 */
// import necessary packages

public class TaskManager {
    //use ArrayList to manage the data
 private ArrayList<String> highPriority;
    private ArrayList<String> lowPriority;
    public TaskManager() {
       //create new ArrayList object for priorities
        highPriority = new ArrayList<>();
        lowPriority = new ArrayList<>();
    }

    // Add a task
    public void addTask(String task) {
        if (task.contains("*")) {
            highPriority.add(task);
        } else {
            lowPriority.add(task);
        }
    }

    // Remove a task
    public void removeTask(String priority, int index) {
        if ("high".equals(priority) && index < highPriority.size()) {
            highPriority.remove(index);
        } else if ("low".equals(priority) && index < lowPriority.size()) {
            lowPriority.remove(index);
        }
    }

    // Change task priority
    // allows moving tasks between high and low priority lists
    public void changePriority(String priority, int index) {
        if ("high".equals(priority) && index < highPriority.size()) {
            String task = highPriority.remove(index);
            lowPriority.add(task);
        } else if ("low".equals(priority) && index < lowPriority.size()) {
            String task = lowPriority.remove(index);
            highPriority.add(task);
        }
    }

    // Promote a high-priority task 
    //moves a high-priority task up in the list
    public void promoteTask(int index) {
         if (index > 0 && index < highPriority.size()) {
            String task = highPriority.remove(index);
            highPriority.add(index - 1, task);
        }
    }
    
//     list getters
    // Get high-priority tasks
    // return copies of the lists to avoid direct modification
    public ArrayList<String> getHighPriorityTasks() {
        return new ArrayList<>(highPriority);
    }

    // Get low-priority tasks
    // return copies of the lists to avoid direct modification
    public ArrayList<String> getLowPriorityTasks() {
        return new ArrayList<>(lowPriority);
    }

    // Display tasks
    public void displayTasks() {
        System.out.println("\nHigh Priority Tasks:");
        for (int i = 0; i < highPriority.size(); i++) {
            System.out.println(i + ". " + highPriority.get(i));
        }

        System.out.println("\nLow Priority Tasks:");
        for (int i = 0; i < lowPriority.size(); i++) {
            System.out.println(i + ". " + lowPriority.get(i));
        }
    }
}
