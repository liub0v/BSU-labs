package Stages;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Objects.Background;
import Objects.Object;

import static javafx.application.Platform.exit;

public class FinalPage extends Object {

    private AnimationTimer timer;
    private Stage stage;
    private long t = 0;
    private Group group;
    private Background background = new Background();
    private int counter = 0;

    FinalPage(boolean win, Stage primaryStage, int score, int time) {
        primaryStage.close();
        stage = new Stage();

        group = new Group();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene dialogScene = new Scene(group, 800, 600);
        dialogScene.getStylesheets().add((getClass().getResource("/view/DarkTheme.css")).toExternalForm());
        //win=true;
        Label label = new Label();
        Button button = new Button("Exit");
        if (win) {
            button.getStyleClass().add("buttonexit1");
            Image image = new Image("/image/sun3.png");
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitHeight(1000);
            iv1.setFitWidth(1000);
            group.getChildren().add(iv1);
            iv1.setTranslateX(-100);
            iv1.setTranslateY(-70);
            dialogScene.setFill(Color.rgb(181, 201, 225));
            //dialogScene.setFill(Color.WHITE);
            label.setText("Congratulation");
            Label scoreLabel = new Label("    Score: " + score + "\n    Time: " + time);
            group.getChildren().add(label);
            label.getStyleClass().add("labelwin");
            label.setPrefSize(450, 30);
            label.setTranslateX(175);
            label.setTranslateY(200);
            scoreLabel.setPrefSize(250, 120);
            scoreLabel.setTranslateX(275);
            scoreLabel.setTranslateY(350);
            scoreLabel.getStyleClass().add("labelscore");
            group.getChildren().add(scoreLabel);
            Duration t = Duration.seconds(1);
            ScaleTransition st = new ScaleTransition(t, label);
            st.setNode(label);

            st.setFromX(0.9);
            st.setToX(1.2);
            st.setFromY(0.9);
            st.setToY(1.2);
            st.setAutoReverse(true);
            st.setCycleCount(Timeline.INDEFINITE);
            st.play();

        } else {
            button.getStyleClass().add("buttonexit");
            dialogScene.setFill(Color.BLACK);
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }

            };
            timer.start();
            label.setText("Game Over");
            Label scoreLabel = new Label("    Score: " + score + "\n    Time: " + time);
            group.getChildren().add(label);
            label.getStyleClass().add("labellose");
            label.setPrefSize(340, 30);
            label.setTranslateX(245);
            label.setTranslateY(230);
            scoreLabel.setPrefSize(250, 120);
            scoreLabel.setTranslateX(275);
            scoreLabel.setTranslateY(350);
            scoreLabel.getStyleClass().add("labelscore1");
            group.getChildren().add(scoreLabel);
            Duration t = Duration.seconds(1);
            ScaleTransition st = new ScaleTransition(t, label);
            st.setNode(label);
            //амплитуда мигания
            st.setFromX(0.9);
            st.setToX(1.2);
            st.setFromY(0.9);
            st.setToY(1.2);
            st.setAutoReverse(true);
            st.setCycleCount(Timeline.INDEFINITE);
            st.play();
        }
        stage.setScene(dialogScene);
        stage.show();


        group.getChildren().add(button);
        button.setPrefSize(100, 20);
        button.setTranslateY(550);
        button.setTranslateX(350);
        button.setOnAction(event -> {
            stage.close();
            exit();
        });

    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - t > 50) {
            t = System.currentTimeMillis();
            background.addStar(group, 400, 200, 0, 0.1);
            counter++;
        }
        if (counter == 150) {
            timer.stop();
        }

    }
}
