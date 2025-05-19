package com.example.zoomanagementsystem;

import javax.swing.*;
import java.awt.*;
import com.example.bouncebox.bouncebox.Rectangle; // 形状模块的 Rectangle
import com.example.bouncebox.bouncebox.BounceBox; // 形状模块的图形窗口
import java.awt.Color;
import com.example.bouncebox.bouncebox.Line;
import com.example.bouncebox.bounceboxframework.Shape;

public class ZooManagementGUI {
    private Zoo southernZone;
    private Zoo northernZone;
    private JFrame mainFrame;
    private JTextArea outputArea;
    private JPanel buttonPanel; // 成员变量声明

    public ZooManagementGUI() {
        // Initialize zoos with sample animals
        southernZone = new Zoo("Southern-Zone Zoo");
        northernZone = new Zoo("Northern-Zone Zoo");
        
        // Add sample animals
        initializeSampleAnimals();
        
        // Create and set up the main window
        mainFrame = new JFrame("Zoo Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        
        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // 创建按钮面板（成员变量初始化）
        buttonPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // 增加行数以容纳新按钮
        
        // 初始化所有按钮（包括新按钮）
        initializeButtons();
        
        // Add components to frame
        mainFrame.add(buttonPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Display welcome message
        outputArea.append("===== Welcome to Zoo Management System =====\n");
        outputArea.append("Please select an operation from the buttons above.\n");
    }
    
    private void initializeSampleAnimals() {
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
    }
    
    private void displayAnimals(Zoo zoo) {
        outputArea.append("\n--- Animals in " + zoo.getName() + " (" + zoo.getCounter() + "/" + zoo.getCapacity() + ") ---\n");
        for (int i = 0; i < zoo.getCounter(); i++) {
            Animal animal = zoo.getAnimal(i);
            outputArea.append("Name: " + animal.getName() + ", Species: " + animal.getSpecies() + ", Age: " + animal.getAge() + "\n");
        }
        outputArea.append("----------------------------------\n");
    }
    
    private void moveAnimalDialog() {
        // Create dialog for moving animals
        JDialog moveDialog = new JDialog(mainFrame, "Move Animal Between Zoos", true);
        moveDialog.setLayout(new BorderLayout());
        moveDialog.setSize(500, 400);
        
        JPanel movePanel = new JPanel(new GridLayout(0, 1));
        
        // Direction selection
        JLabel directionLabel = new JLabel("Select move direction:");
        String[] directions = {"Southern-Zone to Northern-Zone", "Northern-Zone to Southern-Zone"};
        JComboBox<String> directionCombo = new JComboBox<>(directions);
        
        // Animal selection
        JLabel animalLabel = new JLabel("Select animal to move:");
        Zoo from = directionCombo.getSelectedIndex() == 0 ? southernZone : northernZone;
        String[] animalNames = getAnimalNames(from);
        JComboBox<String> animalCombo = new JComboBox<>(animalNames);
        
        // Update animal list when direction changes
        directionCombo.addActionListener(e -> {
            Zoo from1Zoo = directionCombo.getSelectedIndex() == 0 ? southernZone : northernZone;
            animalCombo.setModel(new DefaultComboBoxModel<>(getAnimalNames(from1Zoo)));
        });
        
        // Logistics inputs
        JLabel vehicleLabel = new JLabel("Vehicle Cost:");
        JTextField vehicleCostField = new JTextField();
        
        JLabel fuelLabel = new JLabel("Fuel Cost:");
        JTextField fuelCostField = new JTextField();
        
        JLabel caretakersLabel = new JLabel("Number of Caretakers (1-3):");
        JSpinner caretakersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
        
        JButton moveButton = new JButton("Move Animal");
        moveButton.addActionListener(e -> {
            try {
                String animalName = (String) animalCombo.getSelectedItem();
                Item vehicle = new Item("Transport Truck", "VEH_001");
                vehicle.setPrice(Double.parseDouble(vehicleCostField.getText()));
                Item fuel = new Item("Diesel Fuel", "FUEL_001");
                fuel.setPrice(Double.parseDouble(fuelCostField.getText()));
                int numCaretakers = (int) caretakersSpinner.getValue();
                String[] caretakers = new String[numCaretakers];
                for (int i = 0; i < numCaretakers; i++) {
                    caretakers[i] = JOptionPane.showInputDialog(moveDialog, "Enter name of caretaker " + (i+1) + ":");
                }
                Logistics logistics = new Logistics(vehicle, fuel, caretakers);
                Zoo to = directionCombo.getSelectedIndex() == 0 ? northernZone : southernZone;
                from.moveAnimal(animalName, to, logistics);
                displayAnimals(from);
                displayAnimals(to);
                moveDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(moveDialog, "Please enter valid numbers for costs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add components to panel
        movePanel.add(directionLabel);
        movePanel.add(directionCombo);
        movePanel.add(animalLabel);
        movePanel.add(animalCombo);
        movePanel.add(vehicleLabel);
        movePanel.add(vehicleCostField);
        movePanel.add(fuelLabel);
        movePanel.add(fuelCostField);
        movePanel.add(caretakersLabel);
        movePanel.add(caretakersSpinner);
        movePanel.add(moveButton);
        
        moveDialog.add(movePanel, BorderLayout.CENTER);
        moveDialog.setVisible(true);
    }
    
    private String[] getAnimalNames(Zoo zoo) {
        String[] names = new String[zoo.getCounter()];
        for (int i = 0; i < zoo.getCounter(); i++) {
            names[i] = zoo.getAnimal(i).getName();
        }
        return names;
    }
    
    private void addAnimalDialog() {
        JDialog addDialog = new JDialog(mainFrame, "Add New Animal", true);
        addDialog.setLayout(new GridLayout(0, 2));
        addDialog.setSize(400, 300);
        
        // Zoo selection
        JLabel zooLabel = new JLabel("Select Zoo:");
        String[] zoos = {"Southern-Zone Zoo", "Northern-Zone Zoo"};
        JComboBox<String> zooCombo = new JComboBox<>(zoos);
        
        // Animal details
        JLabel nameLabel = new JLabel("Animal Name:");
        JTextField nameField = new JTextField();
        
        JLabel speciesLabel = new JLabel("Species:");
        JTextField speciesField = new JTextField();
        
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        
        JButton addButton = new JButton("Add Animal");
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String species = speciesField.getText();
                int age = Integer.parseInt(ageField.getText());
                Zoo selectedZoo = zooCombo.getSelectedIndex() == 0 ? southernZone : northernZone;
                selectedZoo.addAnimal(new Animal(name, species, age));
                displayAnimals(selectedZoo);
                addDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addDialog, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add components
        addDialog.add(zooLabel);
        addDialog.add(zooCombo);
        addDialog.add(nameLabel);
        addDialog.add(nameField);
        addDialog.add(speciesLabel);
        addDialog.add(speciesField);
        addDialog.add(ageLabel);
        addDialog.add(ageField);
        addDialog.add(new JLabel()); // spacer
        addDialog.add(addButton);
        
        addDialog.setVisible(true);
    }
    
    private void removeAnimalDialog() {
        JDialog removeDialog = new JDialog(mainFrame, "Remove Animal", true);
        removeDialog.setLayout(new BorderLayout());
        removeDialog.setSize(400, 300);
        
        JPanel removePanel = new JPanel(new GridLayout(0, 1));
        
        // Zoo selection
        JLabel zooLabel = new JLabel("Select Zoo:");
        String[] zoos = {"Southern-Zone Zoo", "Northern-Zone Zoo"};
        JComboBox<String> zooCombo = new JComboBox<>(zoos);
        
        // Animal selection
        JLabel animalLabel = new JLabel("Select Animal to Remove:");
        Zoo selectedZoo = zooCombo.getSelectedIndex() == 0 ? southernZone : northernZone;
        String[] animalNames = getAnimalNames(selectedZoo);
        JComboBox<String> animalCombo = new JComboBox<>(animalNames);
        
        // Update animal list when zoo changes
        zooCombo.addActionListener(e -> {
            Zoo selectedZoo1Zoo = zooCombo.getSelectedIndex() == 0 ? southernZone : northernZone;
            animalCombo.setModel(new DefaultComboBoxModel<>(getAnimalNames(selectedZoo1Zoo)));
        });
        
        JButton removeButton = new JButton("Remove Animal");
        removeButton.addActionListener(e -> {
            String animalName = (String) animalCombo.getSelectedItem();
            selectedZoo.deleteAnimal(animalName);
            displayAnimals(selectedZoo);
            removeDialog.dispose();
        });
        
        // Add components
        removePanel.add(zooLabel);
        removePanel.add(zooCombo);
        removePanel.add(animalLabel);
        removePanel.add(animalCombo);
        removePanel.add(removeButton);
        
        removeDialog.add(removePanel, BorderLayout.CENTER);
        removeDialog.setVisible(true);
    }
    
    private void findAnimalDialog() {
        String animalName = JOptionPane.showInputDialog(mainFrame, "Enter animal name to find:");
        if (animalName != null && !animalName.trim().isEmpty()) {
            int indexSouthern = southernZone.findAnimal(animalName);
            int indexNorthern = northernZone.findAnimal(animalName);
            
            if (indexSouthern != -1) {
                Animal animal = southernZone.getAnimal(indexSouthern);
                outputArea.append("\nAnimal found in Southern-Zone Zoo:\n");
                outputArea.append("Name: " + animal.getName() + ", Species: " + animal.getSpecies() + ", Age: " + animal.getAge() + "\n");
            } else if (indexNorthern != -1) {
                Animal animal = northernZone.getAnimal(indexNorthern);
                outputArea.append("\nAnimal found in Northern-Zone Zoo:\n");
                outputArea.append("Name: " + animal.getName() + ", Species: " + animal.getSpecies() + ", Age: " + animal.getAge() + "\n");
            } else {
                outputArea.append("\nAnimal '" + animalName + "' not found in either zoo.\n");
            }
        }
    }
    
    public void show() {
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ZooManagementGUI gui = new ZooManagementGUI();
            gui.show();
        });
    }
    
    // 新增：绘制动物数量柱状图
    private void drawAnimalCountChart() {
    SwingUtilities.invokeLater(() -> {
        int chartWidth = 600;
        int chartHeight = 400;
        BounceBox box = new BounceBox(chartWidth, chartHeight);
        
        int southCount = southernZone.getCounter();
        int northCount = northernZone.getCounter();
        int baseY = chartHeight; // 窗口底部（y轴向下，baseY为底部）
        int barWidth = 80;
        int scale = 30;
        
        // 南方柱子：y从baseY向上（负高度，确保在窗口内）
        Shape southRect = new Rectangle(
            120,          // x（左）
            baseY - southCount * scale, // 顶部y（baseY - 高度 ≥ 0）
            barWidth,     // 宽度
            southCount * scale  // 高度（向下绘制，y递增，符合Swing坐标系）
        );
        southRect.setColor(new Color(0, 120, 255));
        
        // 北方柱子同理
        Shape northRect = new Rectangle(
            320, 
            baseY - northCount * scale, 
            barWidth, 
            northCount * scale
        );
        northRect.setColor(new Color(255, 80, 80));
        
        // 基线：绘制在底部（y=baseY，与柱子底部对齐）
        Shape baseLine = new Line(0, baseY, chartWidth, baseY);
        baseLine.setColor(Color.BLACK);
        
        box.addShape(baseLine);
        box.addShape(southRect);
        box.addShape(northRect);
        box.start();
    });
}
    private void initializeButtons() {
        // 原有按钮
        JButton displaySouthBtn = new JButton("Display Southern-Zone Animals");
        JButton displayNorthBtn = new JButton("Display Northern-Zone Animals");
        JButton moveAnimalBtn = new JButton("Move Animal Between Zoos");
        JButton addAnimalBtn = new JButton("Add New Animal");
        JButton removeAnimalBtn = new JButton("Remove Animal");
        JButton findAnimalBtn = new JButton("Find Animal");
        
        // 新按钮：显示柱状图
        JButton chartButton = new JButton("Display Animal Count Chart");
        chartButton.addActionListener(e -> drawAnimalCountChart());
        
        // 添加事件监听器
        displaySouthBtn.addActionListener(e -> displayAnimals(southernZone));
        displayNorthBtn.addActionListener(e -> displayAnimals(northernZone));
        moveAnimalBtn.addActionListener(e -> moveAnimalDialog());
        addAnimalBtn.addActionListener(e -> addAnimalDialog());
        removeAnimalBtn.addActionListener(e -> removeAnimalDialog());
        findAnimalBtn.addActionListener(e -> findAnimalDialog());
        
        // 按顺序添加按钮（新增行容纳新按钮）
        buttonPanel.add(displaySouthBtn);
        buttonPanel.add(displayNorthBtn);
        buttonPanel.add(moveAnimalBtn);
        buttonPanel.add(addAnimalBtn);
        buttonPanel.add(removeAnimalBtn);
        buttonPanel.add(findAnimalBtn);
        buttonPanel.add(chartButton); // 新按钮添加到新行
    }
    
}