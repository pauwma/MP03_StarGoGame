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

        if (encogido==true){return;}
        if (protegido==true){return;}
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

    public void shield() {
        if (encogido==true){return;}
        if (protegido==true){return;}
        // Obtiene la imagen original del imageView
        Image originalImage = imageView.getImage();

        // Define la imagen del escudo
        Image shieldImage = new Image("ufo_shield.png");

        // Define la transición para mostrar la imagen del escudo durante 3 segundos
        FadeTransition showShield = new FadeTransition(Duration.seconds(3), imageView);
        showShield.setFromValue(0);
        showShield.setToValue(1);

        // Define la transición para parpadear la imagen del escudo
        TranslateTransition blinkShield = new TranslateTransition(Duration.millis(500), imageView);
        blinkShield.setByX(10);
        blinkShield.setCycleCount(6);
        blinkShield.setAutoReverse(true);

        // Define la transición para intercambiar entre la imagen original y la del escudo
        ParallelTransition swapImages = new ParallelTransition(
                new FadeTransition(Duration.millis(200), imageView),
                new PauseTransition(Duration.millis(100)),
                new FadeTransition(Duration.millis(200), imageView)
        );
        swapImages.setCycleCount(3);

        // Define la transición para ocultar la imagen del escudo
        FadeTransition hideShield = new FadeTransition(Duration.seconds(1), imageView);
        hideShield.setFromValue(1);
        hideShield.setToValue(0);

        // Añade un EventHandler para intercambiar entre la imagen original y la del escudo al finalizar la transición de parpadeo
        blinkShield.setOnFinished(event -> {
            imageView.setImage(originalImage);
            swapImages.play();
        });

        // Añade un EventHandler para volver a la imagen original al finalizar la última transición
        swapImages.setOnFinished(event -> {
            imageView.setImage(originalImage);
            protegido = false;
        });

        // Reproduce las transiciones en secuencia
        showShield.play();
        showShield.setOnFinished(event -> {
            imageView.setImage(shieldImage);
            blinkShield.play();
        });
        blinkShield.setOnFinished(event -> {
            hideShield.play();
        });
    }


}