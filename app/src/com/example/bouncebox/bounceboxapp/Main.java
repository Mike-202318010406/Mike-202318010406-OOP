/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.bouncebox.bounceboxapp;

import com.example.bouncebox.bouncebox.BounceBox;
import com.example.bouncebox.bouncebox.Circle;
import com.example.bouncebox.bouncebox.Rectangle;
import com.example.bouncebox.bouncebox.Square;
import com.example.bouncebox.bouncebox.Triangle;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            runBounceBoxSimulation();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Runtime error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void runBounceBoxSimulation() throws FileNotFoundException {
        // Initialize BounceBox and counters
        BounceBox box = new BounceBox(700, 500);
        ShapeCounter counter = new ShapeCounter();
        
        // Verify input file exists
        File inputFile = new File("practical 4.txt");
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input file not found: " + inputFile.getAbsolutePath());
        }

        // Read and process file
        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            while (scanner.hasNextLine()) {
                processShapeLine(scanner.nextLine().trim(), box, counter);
            }
        }

        // Output results
        outputResults(counter);
        
        // Start simulation
        box.start();
    }

    private static void processShapeLine(String line, BounceBox box, ShapeCounter counter) {
        if (line.isEmpty()) return;
        
        String[] parts = line.split("\\s+");
        try {
            switch (parts[0]) {
                case "Circle":
                    processCircle(parts, box, counter);
                    break;
                case "Square":
                    processSquare(parts, box, counter);
                    break;
                case "Triangle":
                    processTriangle(parts, box, counter);
                    break;
                case "Rectangle":
                    processRectangle(parts, box, counter);
                    break;
                default:
                    System.err.println("Unknown shape type: " + parts[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + line);
        } catch (IllegalArgumentException e) {
            System.err.println("Parameter error: " + line + " - " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Insufficient parameters: " + line);
        }
    }

    private static void processCircle(String[] parts, BounceBox box, ShapeCounter counter) {
        // Validate parameter count
        if (parts.length < 4) {
            throw new IllegalArgumentException("Circle requires at least 4 parameters (x,y,radius)");
        }

        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int radius = Integer.parseInt(parts[3]);
        Circle circle = new Circle(x, y, radius);

        // Process velocity and color
        processShapeProperties(parts, 4, circle);

        // Update counters
        counter.addCircle(circle.getMass());
        box.addShape(circle);
    }

    private static void processSquare(String[] parts, BounceBox box, ShapeCounter counter) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Square requires at least 4 parameters (x,y,side)");
        }

        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int side = Integer.parseInt(parts[3]);
        Square square = new Square(x, y, side);

        processShapeProperties(parts, 4, square);

        counter.addSquare(square.getMass());
        box.addShape(square);
    }

    private static void processTriangle(String[] parts, BounceBox box, ShapeCounter counter) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Triangle requires at least 5 parameters (x,y,base,height)");
        }

        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int base = Integer.parseInt(parts[3]);
        int height = Integer.parseInt(parts[4]);
        Triangle triangle = new Triangle(x, y, base, height);

        processShapeProperties(parts, 5, triangle);

        counter.addTriangle(triangle.getMass());
        box.addShape(triangle);
    }

    private static void processRectangle(String[] parts, BounceBox box, ShapeCounter counter) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Rectangle requires at least 5 parameters (x,y,length,width)");
        }

        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int length = Integer.parseInt(parts[3]);
        int width = Integer.parseInt(parts[4]);
        Rectangle rectangle = new Rectangle(x, y, length, width);

        processShapeProperties(parts, 5, rectangle);

        counter.addRectangle(rectangle.getMass());
        box.addShape(rectangle);
    }

    private static void processShapeProperties(String[] parts, int startIndex, Object shape) {
        if (parts.length > startIndex + 1) {
            double vx = Double.parseDouble(parts[startIndex]);
            double vy = Double.parseDouble(parts[startIndex + 1]);
            
            if (shape instanceof Circle) {
                ((Circle) shape).setVelocity(vx, vy);
            } else if (shape instanceof Square) {
                ((Square) shape).setVelocity(vx, vy);
            } else if (shape instanceof Triangle) {
                ((Triangle) shape).setVelocity(vx, vy);
            } else if (shape instanceof Rectangle) {
                ((Rectangle) shape).setVelocity(vx, vy);
            }

            if (parts.length > startIndex + 4) {
                int red = Integer.parseInt(parts[startIndex + 2]);
                int green = Integer.parseInt(parts[startIndex + 3]);
                int blue = Integer.parseInt(parts[startIndex + 4]);
                Color color = new Color(red, green, blue);
                
                if (shape instanceof Circle) {
                    ((Circle) shape).setColor(color);
                } else if (shape instanceof Square) {
                    ((Square) shape).setColor(color);
                } else if (shape instanceof Triangle) {
                    ((Triangle) shape).setColor(color);
                } else if (shape instanceof Rectangle) {
                    ((Rectangle) shape).setColor(color);
                }
            }
        }
    }

    private static void outputResults(ShapeCounter counter) {
        // Console output
        System.out.println("\n===== Shape Statistics =====");
        System.out.printf("Circles: %d\t\tSquares: %d\n", counter.getCircleCount(), counter.getSquareCount());
        System.out.printf("Triangles: %d\tRectangles: %d\n", counter.getTriangleCount(), counter.getRectangleCount());
        System.out.printf("Total Area: %.2f\n", counter.getTotalArea());

        // File output
        try (PrintWriter writer = new PrintWriter("shapesArea.txt")) {
            writer.println("===== Shape Statistics =====");
            writer.printf("Circles: %d\t\tSquares: %d\n", counter.getCircleCount(), counter.getSquareCount());
            writer.printf("Triangles: %d\tRectangles: %d\n", counter.getTriangleCount(), counter.getRectangleCount());
            writer.printf("Total Area: %.2f\n", counter.getTotalArea());
            System.out.println("Results saved to shapesArea.txt");
        } catch (FileNotFoundException e) {
            System.err.println("Failed to write statistics file");
        }
    }

    // Inner class for counting shapes and calculating total area
    private static class ShapeCounter {
        private int circleCount = 0;
        private int squareCount = 0;
        private int triangleCount = 0;
        private int rectangleCount = 0;
        private double totalArea = 0.0;

        public void addCircle(double area) {
            circleCount++;
            totalArea += area;
        }

        public void addSquare(double area) {
            squareCount++;
            totalArea += area;
        }

        public void addTriangle(double area) {
            triangleCount++;
            totalArea += area;
        }

        public void addRectangle(double area) {
            rectangleCount++;
            totalArea += area;
        }

        // Getter methods
        public int getCircleCount() { return circleCount; }
        public int getSquareCount() { return squareCount; }
        public int getTriangleCount() { return triangleCount; }
        public int getRectangleCount() { return rectangleCount; }
        public double getTotalArea() { return totalArea; }
    }
}