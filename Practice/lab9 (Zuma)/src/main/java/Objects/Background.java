
package Objects;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Background extends Group {

    private final Color MELLOW = Color.rgb(248, 222, 126);
    private static double left = -10;
    private static double right = 10;
    private static double height = 25;
    private static double[] points = {
            left, 0,
            right, 0,
            left + 2, height * 2 / 3,
            0, -height / 3,
            right - 2, height * 2 / 3,
            left, 0
    };


    public Background() {
    }

    public Background(double width, double height) {

        Rectangle background = new Rectangle(0, 0, width + 10, height + 10);
        Stop[] stops = {
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BLACK)
        };

        LinearGradient lg = new LinearGradient(0, 0, 0.9, 0, true, CycleMethod.NO_CYCLE, stops);
        background.setFill(lg);
        getChildren().add(background);
        //star
        Polygon star = new Polygon(points);
        Stop[] stop = {
                new Stop(0, Color.LIGHTGOLDENRODYELLOW),
                new Stop(1, MELLOW),
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.4, true, CycleMethod.NO_CYCLE, stop);
        star.setFill(rg);
        // addStar(this,Zuma.WINDOW_WIDTH,Zuma.WINDOW_HEIGHT,1,2);


    }

    public void addStar(Group group, double width, double height, double koeff1, double koeff2) {

        Polygon star = new Polygon(points);
        Stop[] stop = {
                new Stop(0, Color.LIGHTGOLDENRODYELLOW),
                new Stop(1, MELLOW),
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.4, true, CycleMethod.NO_CYCLE, stop);
        star.setFill(rg);


        double x = width / 2 * koeff1 + Math.random() * width / koeff2 + 10;
        double y = Math.random() * height;
        star.setTranslateX(x);
        star.setTranslateY(y);
        group.getChildren().add(star);
        Duration t = Duration.seconds(0.7);
        ScaleTransition st = new ScaleTransition(t, this);
        st.setNode(star);
        st.setFromX(0.8);
        st.setToX(1.2);
        st.setFromY(0.8);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();


    }

}
