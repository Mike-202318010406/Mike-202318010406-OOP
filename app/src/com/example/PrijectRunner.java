
package com.example;

import java.lang.reflect.Method;

public class PrijectRunner {
    public static void runProject(String projectName) {
        try {
            
            String className = "";
            switch (projectName) {
                case "bankingtasklistgui":
                    className = "com.example.bankingtasklistgui.Bankingtasklistgui"; 
                    break;
                case "BounceBox":
                    className = "com.example.bouncebox.bounceboxapp.Main"; 
                    break;
                case "restaurantmanagementsystem":
                    className = "com.example.restaurantmanagementsystem.RestaurantManagementGUI"; 
                    break;
                case "zoomanagementsystem":
                    className = "com.example.zoomanagementsystem.ZooManagementGUI";
                    break;
                default:
                    System.out.println("Invalid project name: " + projectName);
                    return;
            }
            Class<?> clazz = Class.forName(className);
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
