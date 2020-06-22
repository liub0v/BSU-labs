package Stages;

import Objects.Object;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LevelPage extends Object {

    private Stage stage;
    private Group group;
    private Label label;

    private AnimationTimer timer;
    private int counter = 0;
    private long t = 0;
    private String text = "Choose a game level";
    private char[] words = text.toCharArray();
    private String str = " ";


    LevelPage(Stage primaryStage) {

        stage = new Stage();


        stage.setX(400);
        stage.setY(100);
        group = new Group();

        Button level1 = new Button("Easy");
        Button level2 = new Button("Middle");
        Button level3 = new Button("Hard");
        level1.getStyleClass().add("buttonlevel");
        level2.getStyleClass().add("buttonlevel");
        level3.getStyleClass().add("buttonlevel");
        Label label1 = new Label("Low speed and few balls");
        label1.getStyleClass().add("label2");
        Label label2 = new Label("Low speed and more balls");
        label2.getStyleClass().add("label2");
        Label label3 = new Label("Higher speed many balls");
        label3.getStyleClass().add("label2");
        label1.setTranslateX(400);
        label1.setTranslateY(100);
        label2.setTranslateX(400);
        label2.setTranslateY(300);
        label3.setTranslateX(400);
        label3.setTranslateY(500);
        group.getChildren().addAll(label1, label2, label3);
        level1.setTranslateX(50);
        level1.setTranslateY(0);
        level1.setPrefSize(150, 200);
        level2.setTranslateX(50);
        level2.setTranslateY(200);
        level2.setPrefSize(240, 200);
        level3.setTranslateX(50);
        level3.setTranslateY(400);
        level3.setPrefSize(170, 200);
        level1.setOnAction(event -> {

            Controller controller = new Controller(primaryStage, 10, 1);
            stage.close();
        });
        level2.setOnAction(event -> {

            Controller controller = new Controller(primaryStage, 20, 1);
            stage.close();
        });
        level3.setOnAction(event -> {

            Controller controller = new Controller(primaryStage, 30, 2);

            stage.close();
        });
        group.getChildren().addAll(level1, level2, level3);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene dialogScene = new Scene(group, 800, 600);
        dialogScene.setFill(Color.rgb(181, 201, 225));
        dialogScene.getStylesheets().add((getClass().getResource("/view/DarkTheme.css")).toExternalForm());
        stage.setScene(dialogScene);
        stage.show();
        text();

    }

    private void text() {
        label = new Label(" ");
        label.getStyleClass().add("label2");
        group.getChildren().add(label);
        label.setTranslateX(0);
        label.setTranslateY(0);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }

        };
        timer.start();

    }

    @Override
    public void update() {
        int delay = 50;
        if (System.currentTimeMillis() - t > delay && counter < words.length) {
            t = System.currentTimeMillis();

            str += words[counter];
            label.setText(str);
            counter++;
        }
        if (counter == words.length) {
            timer.stop();
        }
    }

}
