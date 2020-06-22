
package Objects;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Sun extends Object implements EventHandler<MouseEvent> {

    static final double SUN_RADIUS = 70;
    private final Color MELLOW = Color.rgb(248, 222, 126);
    private final Color[] colors = {MELLOW, Color.PLUM, Color.LIGHTPINK, Color.LIGHTSTEELBLUE};
    private Circle mouth;
    private Color mouthColor;


    public Sun(double x, double y) {


        Circle body = new Circle(SUN_RADIUS);
        body.setStrokeWidth(1);
        body.setStroke(Color.rgb(240, 214, 118));
        Stop[] stopsBody = {
                new Stop(0, Color.LIGHTGOLDENRODYELLOW),
                new Stop(1, MELLOW),
        };
        RadialGradient rgBody = new RadialGradient(0, 0, 0.5, 0.5, 0.6, true, CycleMethod.NO_CYCLE, stopsBody);
        body.setFill(rgBody);

        mouth = new Circle(SUN_RADIUS / 4);
        mouthColor = colors[(int) (Math.random() * colors.length)];
        Stop[] stopsMouth = {
                new Stop(0, Color.WHITE),
                new Stop(1, mouthColor)
        };
        RadialGradient rgMouth = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stopsMouth);
        mouth.setFill(rgMouth);
        mouth.setStrokeWidth(1);
        mouth.setStroke(mouthColor.darker());
        mouth.setTranslateY(SUN_RADIUS / 4);


        Arc leftEye = new Arc(-SUN_RADIUS / 3.3 + 3, -SUN_RADIUS / 2 - 5, 10, 25, 170, 200);
        leftEye.setStroke(Color.BLACK);
        leftEye.setFill(Color.WHITE);
        leftEye.setType(ArcType.CHORD);
        leftEye.setRotate(180);
        Arc rightEye = new Arc(SUN_RADIUS / 3.3 - 3, -SUN_RADIUS / 2 - 5, 10, 25, 170, 200);
        rightEye.setStroke(Color.BLACK);
        rightEye.setFill(Color.WHITE);
        rightEye.setType(ArcType.CHORD);
        rightEye.setRotate(180);

        Circle leftCornea = new Circle(-SUN_RADIUS / 3.3 + 3, -SUN_RADIUS / 2 + 13, 7);
        leftCornea.setFill(Color.BLACK);
        leftCornea.setStrokeWidth(3);
        leftCornea.setStroke(Color.LIGHTBLUE);
        Circle rightCornea = new Circle(SUN_RADIUS / 3.3 - 3, -SUN_RADIUS / 2 + 13, 7);
        rightCornea.setFill(Color.BLACK);
        rightCornea.setStrokeWidth(3);
        rightCornea.setStroke(Color.LIGHTBLUE);


        Rectangle leftBlush = new Rectangle(-SUN_RADIUS / 2 - 5 - 2, -7, 15, 10);
        leftBlush.setFill(Color.LIGHTPINK);
        leftBlush.setArcHeight(5);
        leftBlush.setArcWidth(5);

        Rectangle righBlush = new Rectangle(SUN_RADIUS / 2 - 5, -7, 15, 10);
        righBlush.setFill(Color.LIGHTPINK);
        righBlush.setArcHeight(5);
        righBlush.setArcWidth(5);

        Rectangle ray1 = new Rectangle(-SUN_RADIUS, -SUN_RADIUS, SUN_RADIUS * 2, SUN_RADIUS * 2);
        Color DARKMELLOW = Color.rgb(244, 218, 122);
        ray1.setFill(DARKMELLOW);
        Rectangle ray2 = new Rectangle(-SUN_RADIUS, -SUN_RADIUS, SUN_RADIUS * 2, SUN_RADIUS * 2);
        ray2.setFill(DARKMELLOW);
        ray2.setRotate(60);
        Rectangle ray3 = new Rectangle(-SUN_RADIUS, -SUN_RADIUS, SUN_RADIUS * 2, SUN_RADIUS * 2);
        ray3.setFill(DARKMELLOW);
        ray3.setRotate(120);
        Group rays = new Group();
        rays.getChildren().addAll(ray1, ray2, ray3);

        //move rays
        Duration t = Duration.seconds(1);
        ScaleTransition st = new ScaleTransition(t, rays);
        //амплитуда мигания
        st.setFromX(1);
        st.setToX(1.1);
        st.setFromY(1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();

        //sun
        Group eyes = new Group();
        getChildren().addAll(rays, body, mouth, eyes, leftBlush, righBlush);
        setTranslateX(x);
        setTranslateY(y);


        Line lineLeft = new Line(-SUN_RADIUS / 3.3 + 3 - 10, -SUN_RADIUS / 2 + 20, -SUN_RADIUS / 3.3 + 3 + 10, -SUN_RADIUS / 2 + 20);
        lineLeft.setFill(Color.BLACK);
        lineLeft.setStroke(Color.BLACK);
        Line lineRight = new Line(SUN_RADIUS / 3.3 - 3 - 10, -SUN_RADIUS / 2 + 20, SUN_RADIUS / 3.3 - 3 + 10, -SUN_RADIUS / 2 + 20);
        lineRight.setFill(Color.BLACK);
        lineRight.setStroke(Color.BLACK);
        eyes.getChildren().addAll(leftEye, rightEye, leftCornea, rightCornea, lineLeft, lineRight);

    }

    public void setRandomMouthColor() {
        mouthColor = colors[(int) (Math.random() * colors.length)];
        mouth.setStroke(mouthColor.darker());
        Stop[] stops = {
                new Stop(0, Color.WHITE),
                new Stop(1, mouthColor)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        mouth.setFill(rg);


    }

    Color getMouthColor() {
        return mouthColor;
    }


    @Override
    public void update() {
    }

    @Override
    public void handle(MouseEvent event) {
        double x = event.getX(),
                y = event.getY();
        double dx = getTranslateX() - x, dy = y - getTranslateY();
        double alpha = 90 - Math.toDegrees(Math.atan(dy / dx));
        if (x > getTranslateX()) {
            alpha -= 180;
        }
        setRotate(alpha);
    }

}
