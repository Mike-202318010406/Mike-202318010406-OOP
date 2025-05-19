/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bankingtasklistgui;
import java.util.*;
/**
 *
 * @author ashongtical
 */
//use HashMap for bank_accounts
//use TaskManager to manage tasks

//import packages

public class BankingTaskManager {
    private HashMap<String, BankAccount> accounts;
    private TaskManager taskManager;

    public BankingTaskManager() {
        accounts = new HashMap<>();
        taskManager = new TaskManager();
    // use HashMap for new accounts, task manager should TaskManager type
    

    //create new HashMap object for accounts and new object for Task Manager
        
    }

    // Create (add) a new bank account 
    public void createAccount(String accountNumber, double balance, double annualInterestRate) {
        BankAccount account = new BankAccount(balance, annualInterestRate);
        accounts.put(accountNumber, account);
    }

    // Deposit money
    // check if account has an account number, if yes get and add balance to the account number
    public void deposit(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            account.deposit(amount);
            taskManager.addTask("Deposit RMB " + amount + " to Account " + accountNumber);
        }
    }

    // Withdraw money
    // check if account has an account number, if yes get and subtract the amount from the balance of the account number
    public void withdraw(String accountNumber, double amount) {
         if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            if (account.isActive()) {
                account.withdraw(amount);
                taskManager.addTask("Withdraw RMB " + amount + " from Account " + accountNumber);
            }
        }
    }

    // View account details
    // check if account has an account number, if yes get and print the details
    public void viewAccountDetails(String accountNumber) {
          if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            account.displayAccountDetails();
        }
    }
  public void monthlyProcess(String accountNumber) {
      if (accounts.containsKey(accountNumber)) {
         BankAccount account = accounts.get(accountNumber);
          account.monthlyProcess();
      }
  }

    // Manage tasks
    public void viewTasks() {
        taskManager.displayTasks();
    }
    
    // New method to add a task
    public void addTask(String task) {
        taskManager.addTask(task);
    }

    // New method to remove a task
    public void removeTask(String priority, int index) {
        taskManager.removeTask(priority, index);
    }

    // New method to change task priority
    public void changePriority(String priority, int index) {
        taskManager.changePriority(priority, index);
    }

    // New method to promote a task
    public void promoteTask(int index) {
        taskManager.promoteTask(index);
    }

    // New method to get high-priority tasks
    public ArrayList<String> getHighPriorityTasks() {
        return taskManager.getHighPriorityTasks();
    }

    // New method to get low-priority tasks
    public ArrayList<String> getLowPriorityTasks() {
        return taskManager.getLowPriorityTasks();
    }
    
    
}
