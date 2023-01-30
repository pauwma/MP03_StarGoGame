package com.example.stargo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private Stage stage;
    private List<Asteroide> asteroides;
    private Random rand = new Random();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onStartClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("game.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());
        stage.setScene(gameScene);
        createAsteroids();
    }

    public void createAsteroids() {
        asteroides = new ArrayList<>();
        int numAsteroids = rand.nextInt(10) + 5; //generate between 5 to 15 asteroids
        for(int i = 0; i < numAsteroids; i++) {
            double x = rand.nextInt(800); //generate x coordinate between 0 to 800
            double y = rand.nextInt(800); //generate y coordinate between 0 to 800
            asteroides.add(new Asteroide(x, y));
        }
    }
}
