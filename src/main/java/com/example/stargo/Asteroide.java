package com.example.stargo;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Asteroide {
    int x;
    int y;
    private double velocidad;
    private double rotation;

    ImageView imageView;

    public Asteroide(Image image,double velocidad) {
        this.x = (int) (Math.random() * 600 + -200);
        this.y = -400;
        this.velocidad = velocidad;
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        this.rotation = 0;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move() {
        y += velocidad;
        imageView.setY(y);
        imageView.setRotate(rotation);
        rotation += 0.4;
    }
}
