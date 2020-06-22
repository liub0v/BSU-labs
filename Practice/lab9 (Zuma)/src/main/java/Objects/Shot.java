package Objects;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.List;

public class Shot extends Ball {

    private static final double SHOT_VELOCITY = -20;

    private enum ShotState {
        SHOT, MOVING
    }

    private double velocityX, velocityY;
    private ShotState shotState = ShotState.SHOT;

    public Shot(Sun sun) {
        super(1);

        this.color = sun.getMouthColor();
        this.body.setFill(color);
        velocityX = SHOT_VELOCITY * Math.sin(Math.toRadians(sun.getRotate()));
        velocityY = -SHOT_VELOCITY * Math.cos(Math.toRadians(sun.getRotate()));
        setTranslateX(sun.getTranslateX() - Sun.SUN_RADIUS / 5 * Math.sin(Math.toRadians(sun.getRotate())));
        setTranslateY(sun.getTranslateY() + Sun.SUN_RADIUS / 5 * Math.cos(Math.toRadians(sun.getRotate())));
    }

    public static void addShot(Shot shot, List<Shot> shots, Group root) {
        shots.add(shot);
        root.getChildren().add(shot);
        Color color = shot.getColor();
        Stop[] stops = {
                new Stop(0, Color.WHITE),
                new Stop(1, color)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);

        shot.getBody().setFill(rg);
    }

    public void becomeMoving(Ball neighbor) {
        shotState = ShotState.MOVING;
        velocity = neighbor.velocity;
        velocityState = neighbor.velocityState;
        cnt = neighbor.cnt;
        division = neighbor.division;
        setTranslateX(neighbor.getTranslateX());
        setTranslateY(neighbor.getTranslateY());
    }

    @Override
    public void update() {
        if (shotState.equals(ShotState.SHOT)) {
            double x = getTranslateX();
            double y = getTranslateY();
            setTranslateX(x + velocityX);
            setTranslateY(y + velocityY);
        } else {
            super.update();
        }
    }

}
