/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bankingtasklistgui;

/**
 *
 * @author ashongtical
 */
public class BankAccount {
    private double balance;
    private int depositsCount;
    private int withdrawalsCount;
    private double annualInterestRate;
    private double monthlyServiceCharges;
    private boolean isActive;
    
    
    //initialise by setting values of balance, annual Interest Rate, deposit count, monthlyservice charges, withdrawal count
    public BankAccount(double balance, double annualInterestRate) {
        this.balance = balance;
        this.depositsCount = 0;
        this.withdrawalsCount = 0;
        this.annualInterestRate = annualInterestRate;
        this.monthlyServiceCharges = 0;
        this.isActive = balance >= 25.00;
    }

    // The Deposit method to put 
    // check if amount to be deposited is 0 or less otherwise add amount to balance
    public void deposit(double amount) {
       if (amount > 0) {
            balance += amount;
            depositsCount++;
            if (!isActive && balance >= 25.00) {
                isActive = true;
            }
        }
    }

    // Withdraw method to take money from the bank
    // check if balance is less than RMB 25 and deny withdrawal
    // 
    public void withdraw(double amount) {
        if (isActive && balance >= amount) {
            balance -= amount;
            withdrawalsCount++;
            if (balance < 25.00) {
                isActive = false;
            }
        }
    }

    // Calculate monthly interest
    public void calcInterest() {
        double monthlyInterestRate = annualInterestRate / 12;
        double interest = balance * monthlyInterestRate;
        balance += interest;
    }

    // Monthly processing
    public void monthlyProcess() {
        balance -= monthlyServiceCharges;
        calcInterest();
        depositsCount = 0;
        withdrawalsCount = 0;
        monthlyServiceCharges = 0;
        System.out.println("Monthly processing completed.");
    }

    // Display account details
    public void displayAccountDetails() {
        System.out.println("Balance: RMB " + balance);
        System.out.println("Deposits this month: " + depositsCount);
        System.out.println("Withdrawals this month: " + withdrawalsCount);
        System.out.println("Account Status: " + (isActive ? "Active" : "Inactive"));
    }
     public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }
}
