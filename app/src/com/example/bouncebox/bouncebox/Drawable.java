/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bouncebox.bouncebox;

/**
 *
 * @author JG
 */
import java.awt.*;
public interface Drawable {
    void draw(Graphics2D g);
    void setVelocity(int dx, int dy);
    void move();
}