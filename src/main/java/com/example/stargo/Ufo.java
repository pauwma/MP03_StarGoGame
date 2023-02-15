package com.example.stargo;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Ufo {
    int x;
    int y;
    private double velocidad;
    private boolean moveRight;

    ImageView imageView;

    public Ufo(Image image) {
        this.x = 100;
        this.y = 900;
        velocidad = 1;
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
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

    public void changeVelocidad(double velocidad){
        this.velocidad = velocidad;
    }

    public void damage() {
        Image image1 = new Image("ufo.png");
        Image image2 = new Image("ufo_damage.png");

        // Crea una secuencia de cambios de imagen con un intervalo de 100ms
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (imageView.getImage() == image1) {
                imageView.setImage(image2);
            } else {
                imageView.setImage(image1);
            }
        }));
        timeline.setCycleCount(6); // Repite la secuencia 6 veces (3 segundos en total)
        timeline.setOnFinished(event -> {
            // Al terminar la secuencia, restaura la imagen original
            imageView.setImage(image1);
        });
        timeline.play();
    }
}