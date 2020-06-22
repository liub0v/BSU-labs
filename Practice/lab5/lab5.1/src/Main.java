import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private final static Pattern NATURAL_NUMBER = Pattern.compile("[1-9]+[0-9]*");
    private final static Pattern INTEGER = Pattern.compile("([0]{1})|([-+]?([1-9]+)([0-9]*))");
    private final static Pattern REAL_NUMBER = Pattern.compile("([-+]?)(([1-9]+|[0]{1})([.]?)([0-9]+))([eE][-+]?[0-9]+)?");
    private final static Pattern DATE = Pattern.compile("29.02.((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))"
            + "|((0[1-9]|1[0-9]|2[0-8]).02.((19|2[0-9])[0-9]{2}))"
            + "|((0[1-9]|[12][0-9]|3[01]).(0[13578]|10|12).((19|2[0-9])[0-9]{2}))"
            + "|((0[1-9]|[12][0-9]|30).(0[469]|11).((19|2[0-9])[0-9]{2}))");
    private final static Pattern TIME = Pattern.compile("(([0-1][0-9])|([2][0-3])):[0-5][0-9]");
    private final static Pattern EMAIL = Pattern.compile("^[_A-Za-z0-9+]+(\\.[_A-Za-z0-9]+)*@[-a-z]+(\\.[-a-z]{2,})$");
    private CheckBox natural_number = new CheckBox("Natural number");
    private CheckBox integer = new CheckBox("Integer");
    private CheckBox real_number = new CheckBox("Real number");
    private CheckBox date = new CheckBox("Date");
    private CheckBox time = new CheckBox("Time");
    private CheckBox email = new CheckBox("E-Mail");
    private TextField textField = new TextField();
    private FlowPane flowPane = new FlowPane(20, 50);
    private Circle circle = new Circle(0, 0, 10);

    private void check(String newValue, final Pattern pattern, Circle circle) {

        Matcher m;
        m = pattern.matcher(newValue);
        if (m.matches()) circle.setFill(Color.GREEN);
        else circle.setFill(Color.RED);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Regular expressions");
        textField.setPrefColumnCount(40);
        flowPane.getChildren().addAll(natural_number, real_number, integer, time, date, email);
        flowPane.getChildren().add(textField);
        flowPane.getChildren().add(circle);

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int pattern = (natural_number.isSelected() ? 1 : 0)
                        | (real_number.isSelected() ? 2 : 0)
                        | (integer.isSelected() ? 3 : 0)
                        | (time.isSelected() ? 4 : 0)
                        | (date.isSelected() ? 5 : 0)
                        | (email.isSelected() ? 6 : 0);
                switch (pattern) {
                    case 1:
                        check(newValue, NATURAL_NUMBER, circle);
                        break;
                    case 5:
                        check(newValue, DATE, circle);
                        break;

                    case 3:
                        check(newValue, INTEGER, circle);
                        break;
                    case 6:
                        check(newValue, EMAIL, circle);
                        break;

                    case 4:
                        check(newValue, TIME, circle);
                        break;

                    case 2:
                        check(newValue, REAL_NUMBER, circle);
                        break;
                }
            }
        });

        primaryStage.setScene(new Scene(flowPane, 520, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
