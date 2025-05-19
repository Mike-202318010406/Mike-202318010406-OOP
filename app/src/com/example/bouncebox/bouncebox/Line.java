package com.example.bouncebox.bouncebox;

import com.example.bouncebox.bounceboxframework.Shape;
import java.awt.*;

public class Line extends Shape {
    private final int x1, y1, x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        super(x1, y1); // 初始位置（起点）
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor()); // 从父类获取颜色
        g.drawLine(x1, y1, x2, y2); // 绘制线段（固定坐标，不受父类x/y影响）
    }

    @Override
    public double getContactRadius() {
        return 0; // 线段无碰撞（或根据需求调整）
    }

    @Override
    public double getMass() {
        return 0; // 线段无质量（或根据需求调整）
    }
}