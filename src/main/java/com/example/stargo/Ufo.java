package com.example.stargo;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ufo {
    int x;
    int y;
    private double velocidad;
    private double rotation;
    private boolean moveRight;

    ImageView imageView;

    public Ufo(Image image) {
        this.x = 100;
        this.y = 900;
        velocidad = 1;
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        this.rotation = 0;
        this.moveRight = true;
        imageView.setFitWidth(105);
        imageView.setFitHeight(80);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move(){
        if (moveRight) {
            x += velocidad;
        } else {
            x -= velocidad;
        }
        if (x >= 700 || x <= 0) {
            moveRight = !moveRight;
        }
        imageView.setX(x);
        imageView.setY(y);
    }
}