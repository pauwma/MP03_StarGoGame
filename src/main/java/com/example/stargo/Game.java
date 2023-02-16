package com.example.stargo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends Application {
    List<Asteroide> asteroides;
    AnimationTimer timer;
    @FXML
    Text textPuntos;
    Ufo ufo;
    Group root;
    Canvas canvas;
    boolean cerrar;

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        cerrar = false;
        root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        canvas = new Canvas(800, 1000);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image("space.png"),0,0);

        NivelController nivel = new NivelController(ufo);

        // ? Mostrar niveles
        Label nivelLabel = new Label();
        nivelLabel.setAlignment(Pos.CENTER);
        nivelLabel.setStyle("-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-font-size: 24px;");
        nivelLabel.setOpacity(0);
        root.getChildren().add(nivelLabel);

        // Obtener dimensiones de la ventana
        Scene sceneDimensions = root.getScene();
        double screenWidth = sceneDimensions.getWidth();
        double screenHeight = sceneDimensions.getHeight();

        // Posicionar Label en el centro
        nivelLabel.setTranslateX(screenWidth / 2 - nivelLabel.getWidth() / 2);
        nivelLabel.setTranslateY(screenHeight / 2 - nivelLabel.getHeight() / 2);

        Timeline timelineLevel = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(nivelLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(nivelLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(2.5), new KeyValue(nivelLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(3), new KeyValue(nivelLabel.opacityProperty(), 0))
        );


        // ? Ufo
        ufo = new Ufo(new Image("ufo.png"));
        root.getChildren().add(ufo.getImageView());
        AnimationTimer timerUfo = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ufo.move();
            }
        };
        timerUfo.start();

        Button button = new Button("Cambiar Velocidad");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //ufo.habilidadEscudo();
                nivel.vida = 10;
            }
        });
        root.getChildren().add(button);

        // ? Spawn de asteroides
        Timer timerAsteroides = new Timer();
        asteroides = new ArrayList<>();
        for (int i = 0; i < nivel.nAsteroides; i++) {
            if (cerrar){break;}
            timerAsteroides.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            // ? Randomizadores
                            Random random = new Random();

                            int numeroAleatorio = random.nextInt(6) + 1;
                            String rutaImagen = "Asteroide_" + numeroAleatorio + ".png";
                            Asteroide asteroide = new Asteroide(new Image(rutaImagen), nivel.velocidad);

                            double randomScale = nivel.minSize + (nivel.maxSize - nivel.minSize) * random.nextDouble();
                            asteroide.getImageView().setScaleX(randomScale);
                            asteroide.getImageView().setScaleY(randomScale);
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
            }, i * nivel.spawnTime);
        }

        // ? Timer controlador de niveles
        Timer nivelTimer = new Timer();
        nivelTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nivel.nPuntos += nivel.puntosPorSegundo;
                //Platform.runLater(() -> textPuntos.setText(Integer.toString(nivel.nPuntos)));
                if (cerrar==false){
                    System.out.println(nivel.nPuntos);
                }
                if (nivel.nPuntos % 150 == 0 && cerrar == false){
                    nivel.nNivel++;
                    System.out.println("Nivel - " + nivel.nNivel);
                    Platform.runLater(() -> nivelLabel.setText("Nivel " + (nivel.nNivel)));
                    timelineLevel.playFromStart();
                    nivel.cambiosNivel();
                    switch (nivel.nNivel){
                        case 4: ufo.changeVelocidad(1.5f);break;
                        case 6: ufo.changeVelocidad(2f);break;
                        case 8: ufo.changeVelocidad(2.5f);break;
                        case 10: ufo.changeVelocidad(3f);break;
                    }
                }
            }
        }, 1000, 1000);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Asteroide asteroide : asteroides) {
                    if (cerrar){break;}
                    asteroide.move();
                    Bounds asteroideBounds = asteroide.getImageView().getBoundsInParent();
                    asteroideBounds = new BoundingBox(asteroideBounds.getMinX() + 40, asteroideBounds.getMinY() + 40, asteroideBounds.getWidth() - 40, asteroideBounds.getHeight() - 40);

                    Bounds ufoBounds = ufo.getImageView().getBoundsInParent();
                    ufoBounds = new BoundingBox(ufoBounds.getMinX() + 40, ufoBounds.getMinY() + 40, ufoBounds.getWidth() - 40, ufoBounds.getHeight() - 20);

                    if (asteroideBounds.intersects(ufoBounds) && asteroide.isColisionado() == false && ufo.protegido == false) {
                        asteroide.setColisionado(true);
                        nivel.vida = nivel.vida - 10;
                        System.out.println("Hit - " + nivel.vida);
                        ufo.damage();
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

                        if (nivel.vida <= 0) {
                            primaryStage.close(); // Cierra la ventana actual
                            mostrarGameOver(nivel.nPuntos);
                        }
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

    @FXML
    private void mostrarGameOver(int nPuntos) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameover.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Obtener la referencia al Text nPuntos
            Text nPuntosText = (Text) loader.getNamespace().get("nPuntos");

            // Asignar el valor de nPuntos al Text nPuntos
            nPuntosText.setText(Integer.toString(nPuntos));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}