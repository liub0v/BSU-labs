package Objects;

import Stages.Controller;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class Ball extends Object {


    private static final double BALL_RADIUS = 16;
    private static final int BORDER_DIVISION = 8;
    Color color;


    public enum VelocityState {
        UP, DOWN, LEFT, RIGHT
    }

    VelocityState velocityState = VelocityState.DOWN;
    double velocity;

    Circle body;
    int division = BORDER_DIVISION;

    int cnt = BORDER_DIVISION - 1;


    private Circle shield = null;


    public Ball(int velocity) {
        body = new Circle(BALL_RADIUS);
        Color MELLOW = Color.rgb(248, 222, 126);
        Color[] colors = {MELLOW, Color.PLUM, Color.LIGHTPINK, Color.LIGHTSTEELBLUE};
        color = colors[(int) (Math.random() * colors.length)];
        Stop[] stops = {
                new Stop(0, Color.WHITE),
                new Stop(1, color)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        body.setFill(rg);
        body.setStroke(color.darker());
        getChildren().addAll(body);

        this.velocity = velocity;
        setTranslateX(1 / 8.0 * Controller.WINDOW_WIDTH);
        setTranslateY(0);
    }

    public Color getColor() {
        return color;
    }

    Circle getBody() {
        return body;
    }

    private void checkUpdate(boolean condition, boolean condVelocity, VelocityState state) {
        if (condition) {
            --cnt;
            if (condVelocity) {
                velocity = -velocity;
            }
            velocityState = state;
        }
    }

    @Override
    public void update() {
        division = (cnt < 5 ? 4 : 8);
        switch (velocityState) {
            case UP:
                setTranslateY(getTranslateY() + velocity);
                checkUpdate(getTranslateY() + velocity < (Controller.WINDOW_HEIGHT / division),
                        false, Ball.VelocityState.LEFT);
                break;
            case DOWN:
                setTranslateY(getTranslateY() + velocity);
                checkUpdate(getTranslateY() + velocity > (Controller.WINDOW_HEIGHT * (1 - 1.0 / division)),
                        false, Ball.VelocityState.RIGHT);
                break;
            case LEFT:
                setTranslateX(getTranslateX() + velocity);
                checkUpdate(getTranslateX() + velocity < (Controller.WINDOW_WIDTH / division),
                        true, Ball.VelocityState.DOWN);
                break;
            case RIGHT:
                setTranslateX(getTranslateX() + velocity);
                checkUpdate(getTranslateX() + velocity > (Controller.WINDOW_WIDTH * (1 - 1.0 / division)),
                        true, Ball.VelocityState.UP);
                break;
        }
    }

    public void reverse(int times) {
        for (int i = 0; i < times; i++) {
            double step = BALL_RADIUS * 2;
            division = cnt < 3 ? 4 : 8;
            while (step > 0) {
                step -= Math.abs(velocity);
                switch (velocityState) {
                    case UP:
                        setTranslateY(getTranslateY() - velocity);
                        if (getTranslateY() - velocity > (Controller.WINDOW_HEIGHT * (1.0 - 1.0 / division))) {
                            cnt++;
                            velocityState = Ball.VelocityState.RIGHT;
                            velocity = -velocity;
                        }
                        break;
                    case DOWN:
                        setTranslateY(getTranslateY() - velocity);
                        if (cnt < BORDER_DIVISION - 1 && getTranslateY() - velocity < (Controller.WINDOW_HEIGHT / division)) {
                            cnt++;
                            velocityState = Ball.VelocityState.LEFT;
                            velocity = -velocity;
                        }
                        break;
                    case LEFT:
                        setTranslateX(getTranslateX() - velocity);
                        if (getTranslateX() - velocity > (Controller.WINDOW_WIDTH * (1.0 - 1.0 / division))) {
                            cnt++;
                            velocityState = Ball.VelocityState.UP;
                        }
                        break;
                    case RIGHT:
                        setTranslateX(getTranslateX() - velocity);
                        if (cnt < BORDER_DIVISION - 1 && getTranslateX() - velocity < Controller.WINDOW_WIDTH / division) {
                            cnt++;
                            velocityState = Ball.VelocityState.DOWN;
                        }
                        break;
                }
            }
        }
    }

    public static double getRadius() {
        return BALL_RADIUS;
    }


    public boolean checkShield() {
        if (shield != null && shield.getFill() != Color.WHITE) {
            shield.setFill(Color.RED);
            return false;
        }
        if (shield != null) {
            getChildren().remove(shield);
            shield = null;
        }
        return true;
    }


    public boolean getShielded() {
        boolean shielded = false;
        return shielded;
    }

    int getCnt() {
        return cnt;
    }


}
