package Stages;

import Objects.Shot;
import Objects.Sun;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

import static javafx.application.Platform.exit;

class Menu extends Group {
    private Label time;
    private Label score;
    private boolean pause = false;

    Menu(AnimationTimer timer, Scene scene, Group root, Sun sun, List<Shot> shots) {
        Controller controller = new Controller();
        Button pause = new Button("pause");
        pause.getStyleClass().add("stopbutton");
        getChildren().add(pause);
        pause.setTranslateX(10);
        pause.setTranslateY(10);
        pause.setPrefSize(90, 20);

        pause.setOnAction(event -> {
            if (!Menu.this.pause) {
                timer.stop();
                scene.setOnMouseClicked(null);
                Menu.this.pause = true;
                pause.setText("play");
            } else {
                pause.setText("pause");
                timer.start();
                scene.setOnMouseClicked(k -> {
                    Shot.addShot(new Shot(sun), shots, root);
                    sun.setRandomMouthColor();
                });
                Menu.this.pause = false;
            }
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("stopbutton");
        getChildren().add(exit);
        exit.setTranslateX(10);
        exit.setTranslateY(650);
        exit.setPrefSize(90, 20);
        exit.setOnAction(event -> exit());

        score = new Label();
        score.setText("Score: " + controller.getPoints());
        score.getStyleClass().add("labelinfo");
        score.setTranslateX(10);
        score.setTranslateY(150);
        getChildren().add(score);

        time = new Label();
        time.setText("Time: " + controller.getSec());
        time.getStyleClass().add("labelinfo");
        time.setTranslateX(10);
        time.setTranslateY(200);
        getChildren().add(time);

    }


    Label getScore() {
        return score;
    }

    Label getTime() {
        return time;
    }


}
