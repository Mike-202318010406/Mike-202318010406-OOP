/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bouncebox.bouncebox;

import com.example.bouncebox.bounceboxframework.Shape;
import java.awt.*;

public class Rectangle extends Shape {
    private final int width, height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y); // 初始位置（左上角）
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.fillRect((int) getX(), (int) getY(), width, height); // 使用父类坐标（动态位置，可移动）
    }

    @Override
    public double getContactRadius() {
        return Math.hypot(width, height) / 2; // 对角线一半作为碰撞半径
    }

    @Override
    public double getMass() {
        return width * height * 0.1; // 质量与面积相关（示例）
    }
}