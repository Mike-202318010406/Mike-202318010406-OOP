/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.bankingtasklistgui;

/**
 *
 * @author ashongtical
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Bankingtasklistgui {
        // Main components
    private JFrame frame;
    private BankingTaskManager taskManager;

    // Account Creation Panel Components
    private JTextField accountNumberField;
    private JTextField initialBalanceField;
    private JTextField interestRateField;

    // Transaction Panel Components
    private JTextField transactionAccountField;
    private JTextField transactionAmountField;
    private JComboBox<String> transactionTypeCombo;

    // Task List Components
    private DefaultListModel<String> highPriorityModel;
    private DefaultListModel<String> lowPriorityModel;
    private JList<String> highPriorityList;
    private JList<String> lowPriorityList;
    private JTextField taskInputField;

    // Formatting
    private DecimalFormat currencyFormat;

    public Bankingtasklistgui() {
        // Initialize task manager
        taskManager = new BankingTaskManager();
        
        // Initialize currency formatter
        currencyFormat = new DecimalFormat("RMB #,##0.00");

        // Create and setup the main frame
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame("Banking Task Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Create main panels
        JPanel accountCreationPanel = createAccountCreationPanel();
        JPanel transactionPanel = createTransactionPanel();
        JPanel taskManagementPanel = createTaskManagementPanel();

        // Add panels to frame
        frame.add(accountCreationPanel, BorderLayout.NORTH);
        frame.add(transactionPanel, BorderLayout.CENTER);
        frame.add(taskManagementPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createAccountCreationPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Create Bank Account"));

        // Account Number
        panel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField(10);
        panel.add(accountNumberField);

        // Initial Balance
        panel.add(new JLabel("Initial Balance:"));
        initialBalanceField = new JTextField(10);
        panel.add(initialBalanceField);

        // Interest Rate
        panel.add(new JLabel("Annual Interest Rate (%):"));
        interestRateField = new JTextField(5);
        panel.add(interestRateField);

        // Create Account Button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> createAccount());
        panel.add(createAccountButton);

        return panel;
    }

    private void createAccount() {
        try {
            String accountNumber = accountNumberField.getText().trim();
            double initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
            double interestRate = Double.parseDouble(interestRateField.getText().trim()) / 100;

            // Create account
            taskManager.createAccount(accountNumber, initialBalance, interestRate);

            // Show success message
            JOptionPane.showMessageDialog(frame, 
                "Account Created:\n" +
                "Number: " + accountNumber + "\n" +
                "Initial Balance: " + currencyFormat.format(initialBalance) + "\n" +
                "Interest Rate: " + (interestRate * 100) + "%",
                "Account Creation Successful", 
                JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields
            accountNumberField.setText("");
            initialBalanceField.setText("");
            interestRateField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, 
                "Please enter valid numbers for balance and interest rate.",
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error creating account: " + ex.getMessage(),
                "Account Creation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Banking Transactions"));

        // Account Number
        panel.add(new JLabel("Account Number:"));
        transactionAccountField = new JTextField(10);
        panel.add(transactionAccountField);

        // Transaction Amount
        panel.add(new JLabel("Amount:"));
        transactionAmountField = new JTextField(10);
        panel.add(transactionAmountField);

        // Transaction Type
        String[] transactionTypes = {"Deposit", "Withdraw"};
        transactionTypeCombo = new JComboBox<>(transactionTypes);
        panel.add(transactionTypeCombo);

        // Transaction Button
        JButton transactionButton = new JButton("Process Transaction");
        transactionButton.addActionListener(e -> processTransaction());
        panel.add(transactionButton);

        return panel;
    }

    private void processTransaction() {
        try {
            String accountNumber = transactionAccountField.getText().trim();
            double amount = Double.parseDouble(transactionAmountField.getText().trim());
            String transactionType = (String) transactionTypeCombo.getSelectedItem();

            // Perform transaction
            if ("Deposit".equals(transactionType)) {
                taskManager.deposit(accountNumber, amount);
            } else {
                taskManager.withdraw(accountNumber, amount);
            }

            // Show success message
            JOptionPane.showMessageDialog(frame, 
                transactionType + " of " + currencyFormat.format(amount) + 
                " to Account " + accountNumber + " successful!",
                "Transaction Completed", 
                JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields
            transactionAccountField.setText("");
            transactionAmountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, 
                "Please enter a valid transaction amount.",
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Transaction failed: " + ex.getMessage(),
                "Transaction Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createTaskManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Task Management"));

        // Task Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        taskInputField = new JTextField(20);
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> addTask());
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskInputField);
        inputPanel.add(addTaskButton);

        // Task Lists
        highPriorityModel = new DefaultListModel<>();
        lowPriorityModel = new DefaultListModel<>();
        highPriorityList = new JList<>(highPriorityModel);
        lowPriorityList = new JList<>(lowPriorityModel);

        JPanel listPanel = new JPanel(new GridLayout(1, 2));
        listPanel.add(new JScrollPane(highPriorityList));
        listPanel.add(new JScrollPane(lowPriorityList));

        // Buttons for task management
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton removeTaskButton = new JButton("Remove Task");
        JButton changeTaskPriorityButton = new JButton("Change Priority");
        removeTaskButton.addActionListener(e -> removeTask());
        changeTaskPriorityButton.addActionListener(e -> changePriority());
        buttonPanel.add(removeTaskButton);
        buttonPanel.add(changeTaskPriorityButton);

        // Assemble task management panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addTask() {
        String task = taskInputField.getText().trim();
        if (!task.isEmpty()) {
            taskManager.addTask(task);
            updateTaskLists();
            taskInputField.setText("");
        }
    }

    private void removeTask() {
    int[] highSelectedIndices = highPriorityList.getSelectedIndices();
    // low
    int[] lowSelectedIndices = lowPriorityList.getSelectedIndices();

    // high
    for (int i = highSelectedIndices.length - 1; i >= 0; i--) {
        int index = highSelectedIndices[i];
        taskManager.removeTask("high", index);
    }

    //low
    for (int i = lowSelectedIndices.length - 1; i >= 0; i--) {
        int index = lowSelectedIndices[i];
        taskManager.removeTask("low", index);
    }

    // change
    updateTaskLists();
    }

    private void changePriority() {
        if (!highPriorityList.isSelectionEmpty()) {
            int index = highPriorityList.getSelectedIndex();
            taskManager.changePriority("high", index);
        } else if (!lowPriorityList.isSelectionEmpty()) {
            int index = lowPriorityList.getSelectedIndex();
            taskManager.changePriority("low", index);
        }
        updateTaskLists();
    }

    private void updateTaskLists() {
        highPriorityModel.clear();
        lowPriorityModel.clear();
        
        for (String task : taskManager.getHighPriorityTasks()) {
            highPriorityModel.addElement(task);
        }
        
        for (String task : taskManager.getLowPriorityTasks()) {
            lowPriorityModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        // Ensure GUI is created on Event
        SwingUtilities.invokeLater(() -> new Bankingtasklistgui());
    }
}
