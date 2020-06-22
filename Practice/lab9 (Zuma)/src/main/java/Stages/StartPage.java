package Stages;

import Objects.Object;
import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class StartPage extends Object {

    private Button start = new Button("START");
    private Group group;
    private Label label;
    private long t = 0;
    private int counter = 0;
    private String str = " ";
    private AnimationTimer timer2;
    private Stage primaryStage;
    private Stage stage;
    private int delay = 50;
    private String text = "The light believes that it is \n faster than everyone, but it is \n mistaken:" +
            " no matter how fast \n the light flies, the darkness is \n already in place and waiting...";
    private char[] words = text.toCharArray();

    public StartPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        stage = new Stage();


        stage.setX(400);
        stage.setY(100);
        group = new Group();

        Button faster = new Button(">>");
        faster.getStyleClass().add("button1");
        group.getChildren().add(faster);
        faster.setPrefSize(50, 10);
        faster.setTranslateY(540);
        faster.setTranslateX(750);
        faster.setOnAction(event -> delay = 0);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene dialogScene = new Scene(group, 800, 600);
        dialogScene.setFill(Color.rgb(181, 201, 225));

        stage.setScene(dialogScene);
        stage.show();

        image();
        text();

        dialogScene.getStylesheets().add((getClass().getResource("/view/DarkTheme.css")).toExternalForm());
    }

    private void startButton(Stage primaryStage) {
        group.getChildren().add(start);
        start.getStyleClass().add("button2");
        start.setPrefSize(100, 40);
        start.setTranslateX(330 + 185);
        start.setTranslateY(500);
        Duration t = Duration.seconds(0.7);
        ScaleTransition st = new ScaleTransition(t, start);
        st.setNode(start);
        st.setFromX(1);
        st.setToX(1.1);
        st.setFromY(1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();
        start.setOnAction(event -> {
            LevelPage levelPage = new LevelPage(primaryStage);
            stage.close();
        });
    }

    private void image() {
        Image image = new Image("/image/back1.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitHeight(620);
        iv1.setFitWidth(350);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(-1);
        dropShadow.setColor(Color.BLACK);
        iv1.setEffect(dropShadow);
        group.getChildren().add(iv1);
        iv1.setY(-20);
        iv1.setX(-20);

    }

    private void text() {
        label = new Label(" ");
        label.getStyleClass().add("label1");
        group.getChildren().add(label);
        label.setTranslateX(370);
        label.setTranslateY(100);

        timer2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }

        };
        timer2.start();

    }

    @Override
    public void update() {

        if (System.currentTimeMillis() - t > delay && counter < words.length) {
            t = System.currentTimeMillis();

            str += words[counter];
            label.setText(str);
            counter++;
        }
        if (counter == words.length) {
            timer2.stop();
            startButton(primaryStage);
        }


    }

}
