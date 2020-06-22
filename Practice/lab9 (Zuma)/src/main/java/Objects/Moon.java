
package Objects;

import Stages.Controller;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Moon extends Object {

    private static final double MOON_RADIUS = 50;

    private double x;
    private double y;

    private Circle leftPupil;
    private Circle rightPupil;
    private Arc leftEye;
    private Arc rightEye;
    private long t = 0;
    private long cnt = 0;

    public Moon(double x, double y) {
        this.x = x;
        this.y = y;
        Circle body = new Circle(MOON_RADIUS);

        Stop[] stops = {
                new Stop(0, Color.WHITE),
                new Stop(1, Color.LIGHTBLUE)
        };
        LinearGradient lg = new LinearGradient(0, 0, 0.7, 0, true, CycleMethod.NO_CYCLE, stops);
        body.setFill(lg);


        leftEye = new Arc(-MOON_RADIUS / 3.3, -MOON_RADIUS / 2 - 5, 10, 25, 180, 180);
        rightEye = new Arc(MOON_RADIUS / 3.3, -MOON_RADIUS / 2 - 5, 10, 25, 180, 180);

        leftEye.setStroke(Color.BLACK);
        leftEye.setFill(Color.GRAY);
        leftEye.setType(ArcType.CHORD);
        leftEye.setRotate(180);

        rightEye.setStroke(Color.BLACK);
        rightEye.setFill(Color.GRAY);
        rightEye.setType(ArcType.CHORD);
        rightEye.setRotate(180);

        leftPupil = new Circle(-MOON_RADIUS / 3.3, -MOON_RADIUS / 2 + 15, 5);
        rightPupil = new Circle(MOON_RADIUS / 3.3, -MOON_RADIUS / 2 + 15, 5);


        Circle mounth = new Circle(0, MOON_RADIUS / 2, 5);
        mounth.setFill(Color.BLACK);
        ScaleTransition sc = new ScaleTransition(Duration.seconds(1), mounth);
        sc.setByX(1.1);
        sc.setByY(1.1);
        sc.setAutoReverse(true);
        sc.setCycleCount(Animation.INDEFINITE);
        sc.play();


        Polygon kapa = new Polygon(-MOON_RADIUS + 5, 0, 0, -MOON_RADIUS, MOON_RADIUS - 5, 0);
        kapa.setTranslateY(-MOON_RADIUS + 7);
        kapa.setFill(Color.DARKGRAY);
        Rectangle kaparec = new Rectangle(-MOON_RADIUS, -MOON_RADIUS + 7, MOON_RADIUS * 2, 10);
        kaparec.setArcWidth(10);
        kaparec.setArcHeight(10);
        kaparec.setFill(Color.WHITE);
        Circle kapacircle = new Circle(0, -MOON_RADIUS * 2 + 7, 10, Color.WHITE);

        getChildren().addAll(body, leftEye, rightEye, mounth, kapa, kaparec, kapacircle);
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addZ(Polygon z, Group root) {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), z);
        double angle = Math.random() * 80;
        rt.setFromAngle(angle);
        rt.setToAngle(-angle);

        rt.setAutoReverse(true);
        rt.setCycleCount(Animation.INDEFINITE);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), z);
        tt.setByY(-Controller.WINDOW_HEIGHT / 2 - 40);
        tt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        tt.play();
        root.getChildren().add(z);
    }

    public Polygon makeZ(double x, double y) {
        double top = y - 15;
        double bottom = y + 15;
        double left = x - 10;
        double right = x + 10;
        double offset = 5;
        Polygon z = new Polygon(left, top,
                right, top,
                right, top + offset,
                left + offset, bottom - offset,
                right, bottom - offset,
                right, bottom,
                left, bottom,
                left, bottom - offset,
                right - offset, top + offset,
                left, top + offset);

        z.setFill(Color.WHITE);
        z.setStrokeWidth(2);
        z.setStroke(Color.BLACK);
        return z;

    }

    public void openEyes() {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - t > 500) {
                    t = System.currentTimeMillis();
                    if (cnt == 4) {
                        this.stop();
                    }
                    if (cnt % 2 == 0) {
                        rightEye.setFill(Color.WHITE);
                        leftEye.setFill(Color.WHITE);
                        getChildren().addAll(leftPupil, rightPupil);
                    } else {
                        rightEye.setFill(Color.GREY);
                        leftEye.setFill(Color.GREY);
                        getChildren().removeAll(leftPupil, rightPupil);
                    }
                    cnt++;
                }

            }
        }.start();

    }


}
