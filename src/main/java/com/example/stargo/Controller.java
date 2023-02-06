package com.example.stargo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller extends Application {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onStartClick() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("game.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());
        stage.setScene(gameScene);

        Game game = new Game();
        game.start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}