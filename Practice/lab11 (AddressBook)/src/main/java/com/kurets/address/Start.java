package com.kurets.address;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class Start {
    private long t = 0;
    private AnimationTimer timer;
    private Stage primaryStage;
    private Stage stage;

    Start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        Group group = new Group();
        Image image = new Image("images/start1.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitHeight(400);
        iv1.setFitWidth(800);
        group.getChildren().add(iv1);
        iv1.setY(0);
        iv1.setX(0);
        Scene dialogScene = new Scene(group, 800, 400);


        stage.setScene(dialogScene);
        stage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                delay();
            }
        };
        timer.start();
    }

    private void delay() {
        if (t == 100) {
            timer.stop();
            stage.close();
            primaryStage.show();
        }
        t++;
    }
}
