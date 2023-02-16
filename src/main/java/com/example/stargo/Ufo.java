package com.example.stargo;


import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Ufo {
    int x;
    int y;
    private double velocidad;
    private boolean moveRight;
    private boolean encogido;
    boolean protegido;

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
        if (!protegido){
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

    public void habilidadEncoger() {

        if (encogido || protegido) {
            return; // Si el objeto ya está encogido o protegido, no hace nada.
        }
        encogido = true;
        double originalWidth = imageView.getFitWidth();
        double originalHeight = imageView.getFitHeight();

        // Primera transición: reduce el tamaño del imageView a la mitad
        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(500), imageView);
        scaleTransition1.setByX(-0.5);
        scaleTransition1.setByY(-0.5);

        // Segunda transición: mantiene el tamaño reducido durante 4 segundos
        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(4), imageView);
        scaleTransition2.setByX(0);
        scaleTransition2.setByY(0);

        // Tercera transición: restaura el tamaño original del imageView
        ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(500), imageView);
        scaleTransition3.setByX(0.5);
        scaleTransition3.setByY(0.5);

        // Añade un EventHandler para iniciar la segunda transición cuando termine la primera
        scaleTransition1.setOnFinished(event -> {
            scaleTransition2.play();
        });

        // Añade un EventHandler para iniciar la tercera transición cuando termine la segunda
        scaleTransition2.setOnFinished(event -> {
            scaleTransition3.play();
            encogido = false;
        });

        // Inicia la primera transición
        scaleTransition1.play();
    }

    public void habilidadEscudo() {
        if (encogido || protegido) {
            return; // Si el objeto ya está encogido o protegido, no hace nada.
        }
        protegido = true;
        // Carga la imagen del escudo.
        Image shieldImage = new Image("ufo_shield.png");

        // Crea una Timeline para cambiar la imagen del ImageView a la del escudo durante 4 segundos.
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(4), event -> {
                    imageView.setImage(new Image("ufo.png")); // Al terminar la transición, cambia la imagen del ImageView a la original.
                    protegido = false;
                })
        );

        // Guarda la imagen original antes de cambiarla.
        Image originalImage = imageView.getImage();

        // Cambia la imagen del ImageView a la del escudo.
        imageView.setImage(shieldImage);

        // Inicia la Timeline.
        timeline.play();
    }


}