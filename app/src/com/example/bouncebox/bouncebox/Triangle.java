/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bouncebox.bouncebox;

import com.example.bouncebox.bounceboxframework.Shape;
import java.awt.Graphics2D;

public class Triangle extends Shape {

	private int base;
	private int height;

	public Triangle(int x, int y, int base, int height) {
		super(x, y);
		this.base = base;
		this.height = height;
	}

	public double getContactRadius() {
		return 0.00;
	}

	public int getBase() {
		return base;
	}

	public int getHeight() {
		return height;
	}

	public double getMass() {
		return base * height * 0.5;
	}

	 public void draw(Graphics2D g) {
             g.setColor(getColor());
        int x1 = (int)getX();               
        int y1 = (int)getY() - height/2;   
        
        int x2 = (int)getX() - base/2;   
        int y2 = (int)getY() + height/2;   
        
        int x3 = (int)getX() + base/2;      
        int y3 = (int)getY() + height/2; 
   
        g.fillPolygon(
            new int[]{x1, x2, x3}, 
            new int[]{y1, y2, y3}, 
            3
        );
    }
    }