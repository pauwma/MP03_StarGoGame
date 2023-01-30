package com.example.stargo;

import javafx.scene.canvas.GraphicsContext;

public class Asteroide {
    private double x, y;
    private int size;
    private double speed;
    public Asteroide(double x, double y) {
        this(x, y, 10, 1);
    }
    public Asteroide(double x, double y, int size, double speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public void render(GraphicsContext gc) {
        gc.fillOval(x, y, size, size);
    }
}

