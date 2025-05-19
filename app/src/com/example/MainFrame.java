package com.example;
import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class MainFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = 2100312418246593327L;

    public MainFrame() {
       setTitle("ProjectControl");
       setSize(800, 600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       

       JPanel panel = new JPanel(new GridLayout(2, 5));

       addButton(panel, "BankingManagement System    ", "bankingtasklistgui");
       addButton(panel, "BounceBox", "BounceBox");
       addButton(panel, "RestaurantManagement System", "restaurantmanagementsystem");
       addButton(panel, "ZooManagement System", "zoomanagementsystem");

       add(panel);
   }

   private void addButton(JPanel panel, String text, String projectName) {
       JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20)); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false); 
        button.addActionListener(e -> PrijectRunner.runProject(projectName));
       panel.add(button);
   }

   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
   }
}