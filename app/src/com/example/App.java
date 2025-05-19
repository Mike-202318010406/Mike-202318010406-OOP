package com.example;
	import javax.swing.*;
	import java.awt.*;
	import java.io.Serial;

	public class App extends JFrame {
	   @Serial
	   private static final long serialVersionUID = 2100312418246593327L;

	   public App() {
	      setTitle("项目控制面板");
	      setSize(400, 300);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      JPanel panel = new JPanel(new GridLayout(2, 2));

	      addButton(panel, "银行任务列表", "bankingtasklistgui");
	      addButton(panel, "弹跳盒子", "BounceBox");
	      addButton(panel, "餐厅管理系统", "restaurantmanagementsystem");
	      addButton(panel, "动物园管理系统", "zoomanagementsystem");

	      add(panel);
	  }

	  private void addButton(JPanel panel, String text, String projectName) {
	      JButton button = new JButton(text);
	      button.addActionListener(e -> PrijectRunner.runProject(projectName));
	      panel.add(button);
	  }

	  public static void main(String[] args) {
	      SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	  }
	}