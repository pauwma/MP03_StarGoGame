package com.example.stargo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {
    List<Asteroide> asteroides;
    AnimationTimer timer;

    @FXML
    Text textPuntos;


    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Canvas canvas = new Canvas(800, 1000);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image("space.png"),0,0);

        NivelController nivel = new NivelController();
        //textPuntos.setText("0");

        // ? Ufo
        Ufo ufo = new Ufo(new Image("ufo.png"));
        root.getChildren().add(ufo.getImageView());
        AnimationTimer timerUfo = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ufo.move();
            }
        };
        timerUfo.start();

        // ? Spawn de asteroides
        Timer timerAsteroides = new Timer();
        asteroides = new ArrayList<>();
        for (int i = 0; i < nivel.nAsteroides; i++) {
            timerAsteroides.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Asteroide asteroide = new Asteroide(new Image("asteroide_1.png"), nivel.velocidad);
                            asteroide.getImageView().setScaleX(0.18);
                            asteroide.getImageView().setScaleY(0.18);
                            root.getChildren().add(asteroide.getImageView());
                            asteroides.add(asteroide);
                            asteroide.getImageView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    javafx.animation.ScaleTransition transition = new javafx.animation.ScaleTransition();
                                    transition.setDuration(Duration.seconds(0.2f));
                                    transition.setNode(asteroide.getImageView());
                                    transition.setToX(0);
                                    transition.setToY(0);
                                    transition.setOnFinished(event1 -> {
                                        root.getChildren().remove(asteroide.getImageView());
                                        asteroides.remove(asteroide);
                                    });
                                    transition.play();
                                }
                            });
                        }
                    });
                }
            }, i * ((int) (Math.random() * (nivel.maxSpawnTime - nivel.minSpawnTime + 1) + nivel.minSpawnTime)));
        }

        /* POSICIÓN RATÓN /
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getSceneX() + ", Y: " + event.getSceneY());
            }
        }); */

        Timer puntosTimer = new Timer();
        puntosTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nivel.nPuntos += nivel.puntosPorSegundo;
                //Platform.runLater(() -> textPuntos.setText(Integer.toString(nivel.nPuntos)));
                System.out.println(nivel.nPuntos);
                switch (nivel.nPuntos){
                    case 200:
                        System.out.println("NIVEL 2");
                        nivel.velocidad++;
                        break;
                    case 400:
                        System.out.println("NIVEL 3");
                        nivel.velocidad++;
                        break;
                    case 600:
                        System.out.println("NIVEL 4");
                        nivel.velocidad++;
                        break;
                }
            }
        }, 1000, 1000);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Asteroide asteroide : asteroides) {
                    asteroide.move();
                    Bounds asteroideBounds = asteroide.getImageView().getBoundsInParent();
                    asteroideBounds = new BoundingBox(asteroideBounds.getMinX() + 40, asteroideBounds.getMinY() + 10, asteroideBounds.getWidth() - 20, asteroideBounds.getHeight() - 20);

                    Bounds ufoBounds = ufo.getImageView().getBoundsInParent();
                    ufoBounds = new BoundingBox(ufoBounds.getMinX() + 40, ufoBounds.getMinY() + 40, ufoBounds.getWidth() - 40, ufoBounds.getHeight() - 20);

                    if (asteroideBounds.intersects(ufoBounds) && asteroide.isColisionado() == false) {
                        asteroide.setColisionado(true);
                        nivel.vida = nivel.vida - 10;
                        System.out.println("Hit - " + nivel.vida);
                    }
                    if (asteroide.y > 1000 && asteroide.isColisionado() == false ){
                        root.getChildren().remove(asteroide.getImageView());
                    }
                }
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}