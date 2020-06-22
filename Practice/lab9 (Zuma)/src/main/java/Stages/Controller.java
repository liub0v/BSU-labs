package Stages;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import Objects.*;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static final double WINDOW_WIDTH = 1200;
    public static final double WINDOW_HEIGHT = 700;
    private int ballscount = 0;
    private int velocity = 0;
    private AnimationTimer timer;

    private static Group root;
    private Moon moon;
    private Sun sun;

    private static List<Ball> balls = new ArrayList<>();
    private static List<Shot> shots = new ArrayList<>();

    private Scene scene;
    private long t = 0;
    private int sec = 0;
    private static int balls_cnt = 0;
    private int points = 0;
    private Background background;
    private Stage primaryStage;
    private Menu menu;

    Controller() {
    }

    Controller(Stage primaryStage, int ballscount, int velocity) {

        this.ballscount = ballscount;
        this.velocity = velocity;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage = primaryStage;
        root = new Group();

        background = new Background(WINDOW_WIDTH, WINDOW_HEIGHT);
        sun = new Sun(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        moon = new Moon((3.0 / 4) * WINDOW_WIDTH, WINDOW_HEIGHT / 2);
        balls.add(new Ball(velocity));
        root.getChildren().addAll(background, sun, moon, balls.get(0));


        scene = new Scene(root, WINDOW_WIDTH + 100, WINDOW_HEIGHT);
        scene.setFill(Color.BLACK);
        scene.getStylesheets().add((getClass().getResource("/view/DarkTheme.css")).toExternalForm());
        scene.setOnMouseMoved(sun);

        scene.setOnMouseClicked(k -> {
            Shot.addShot(new Shot(sun), shots, root);
            sun.setRandomMouthColor();
        });
        primaryStage.setTitle("Zuma(by LK)");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        menu = new Menu(timer, scene, root, sun, shots);
        menu.setTranslateX(1200);
        menu.setTranslateY(0);
        root.getChildren().add(menu);
        timer.start();
        primaryStage.show();


    }


    private void stopClick() {
        scene.setOnMouseClicked(null);
    }


    private void update() {

        // Adding time.
        if (System.currentTimeMillis() - t > 1000) {
            sec++;
            t = System.currentTimeMillis();

            if (Math.random() * 100 > 50) {
                Polygon z = moon.makeZ(moon.getX(), moon.getY());
                moon.addZ(z, root);
            }
            //звездочки
            if (Math.random() * 100 > 60)
                background.addStar(background, WINDOW_WIDTH, WINDOW_HEIGHT, 1, 2);


        }
        menu.getTime().setText("Time: " + sec);
        //добавление мячей в ленту
        if (balls.size() != 0 && balls.get(balls.size() - 1).getTranslateY() >= Ball.getRadius() * 2) {
            if (balls_cnt < ballscount) {
                Ball ball = new Ball(velocity);
                balls.add(ball);
                root.getChildren().add(ball);
                balls_cnt++;
            }
        }
        balls.forEach(Ball::update);


        //механизм
        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.update();
            if (shot.getTranslateX() < 0 || shot.getTranslateX() > WINDOW_WIDTH
                    || shot.getTranslateY() < 0 || shot.getTranslateY() > WINDOW_HEIGHT) {
                root.getChildren().remove(shot);
                shots.remove(shot);
            } else {
                for (int j = 0; j < balls.size(); j++) {
                    if (shot.getBoundsInParent().intersects(balls.get(j).getBoundsInParent())) {
                        crashLogic(shot, j);
                        break;
                    }
                }
            }
        }
        sun.update();

        //проигрыш

        if (!balls.isEmpty() && balls.get(0).getTranslateX() == moon.getX() && balls.get(0).getTranslateY() == moon.getY() + 30) {
            stopClick();
            moon.openEyes();
            FinalPage finalPage = new FinalPage(false, primaryStage, points, sec);
            timer.stop();

        }
        //выигрыш
        if (balls.size() == 0) {
            moon.openEyes();
            stopClick();
            FinalPage finalPage = new FinalPage(true, primaryStage, points, sec);
            timer.stop();
        }

    }


    int getSec() {
        return sec;
    }

    int getPoints() {
        return points;
    }

    private void crashLogic(Shot shot, int j) {

        Ball hitBall = balls.get(j);
        Ball prevBall = (j - 1 >= 0) ? balls.get(j - 1) : null;
        Ball nextBall = (j + 1 < balls.size()) ? balls.get(j + 1) : null;
        if (nextBall != null && shot.getColor().equals(nextBall.getColor())) {
            j++;
            hitBall = nextBall;
            nextBall = (j + 1 < balls.size()) ? balls.get(j + 1) : null;
            prevBall = (j - 1 >= 0) ? balls.get(j - 1) : null;
        }
        if (shot.getColor().equals(hitBall.getColor())
                && ((prevBall != null && shot.getColor().equals(prevBall.getColor()))
                || (nextBall != null && shot.getColor().equals(nextBall.getColor())))) {
            root.getChildren().remove(shot);
            int sameColorCnt = 0, k;
            //next
            for (k = j + 1; k < balls.size() && balls.get(k).getColor().equals(shot.getColor()); k++) {
                // counting
            }
            int destroyed = 0;
            int totalCurrScore = 1;
            if (shot.getColor() == Color.YELLOW) {
                totalCurrScore = 2;
            }
            for (int m = k - 1; m >= j + 1; m--) {
                if (balls.get(m).checkShield()) {
                    root.getChildren().remove(balls.get(m));

                    int currScore = 1;
                    if (balls.get(m).getColor() == Color.YELLOW) {
                        currScore = 2;
                    }
                    if (balls.get(m).getShielded()) {
                        currScore *= 2;
                    }
                    totalCurrScore += currScore;

                    balls.remove(m);
                    destroyed++;
                } else {
                    balls.get(m).reverse(destroyed);
                }

            }
            //previous
            for (k = j; k >= 0 && balls.get(k).getColor().equals(shot.getColor()); k--) {
                if (balls.get(k).checkShield()) {
                    root.getChildren().remove(balls.get(k));

                    int currScore = 1;
                    if (balls.get(k).getColor() == Color.YELLOW) {
                        currScore = 2;
                    }
                    if (balls.get(k).getShielded()) {
                        currScore *= 2;
                    }
                    totalCurrScore += currScore;

                    balls.remove(k);
                    destroyed++;
                } else {
                    balls.get(k).reverse(destroyed);
                }
            }
            //reverse
            for (int m = k; m >= 0; m--) {
                balls.get(m).reverse(destroyed);
            }

            points += totalCurrScore;
            menu.getScore().setText("Score: " + points);

        } else {
            if (nextBall != null) {
                shot.becomeMoving(nextBall);
                balls.add(j + 1, shot);
                for (int k = j + 2; k < balls.size(); k++) {
                    Ball hit = balls.get(k);
                    hit.reverse(1);
                }
            } else {
                shot.becomeMoving(balls.get(balls.size() - 1));
                balls.add(balls.size(), shot);
                shot.reverse(1);
            }
        }
        shots.remove(shot);
    }
}
