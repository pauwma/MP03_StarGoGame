package com.example.stargo;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {
    List<Asteroide> asteroides;
    AnimationTimer timer;

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


        // * Ufo



        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getSceneX() + ", Y: " + event.getSceneY());
            }
        });

        asteroides = new ArrayList<>();

        for (int i = 0; i < 999; i++) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Asteroide asteroide = new Asteroide(new Image("asteroide_1.png"), 2);
                            asteroide.getImageView().setScaleX(0.18);
                            asteroide.getImageView().setScaleY(0.18);
                            root.getChildren().add(asteroide.getImageView());
                            asteroides.add(asteroide);

                            // Agregar evento de mouse para cada asteroide
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
            }, i * 800);
        }


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Asteroide asteroide : asteroides) {
                    asteroide.move();
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