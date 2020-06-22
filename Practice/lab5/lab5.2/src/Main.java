import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private final static Pattern DATE = Pattern.compile("29.02.((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))"
            + "|((0[1-9]|1[0-9]|2[0-8]).02.((19|2[0-9])[0-9]{2}))"
            + "|((0[1-9]|[12][0-9]|3[01]).(0[13578]|10|12).((19|2[0-9])[0-9]{2}))"
            + "|((0[1-9]|[12][0-9]|30).(0[469]|11).((19|2[0-9])[0-9]{2}))");
    private FlowPane flowPane = new FlowPane(10, 10);
    private TextArea textAreaInput = new TextArea();
    private TextArea textAreaOutput = new TextArea();
    private Button findDates = new Button("Find");

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Finding dates in text");
        textAreaInput.setPrefColumnCount(50);
        textAreaInput.setPrefRowCount(5);
        textAreaOutput.setPrefColumnCount(50);
        textAreaOutput.setPrefRowCount(5);
        flowPane.getChildren().add(textAreaInput);
        flowPane.getChildren().add(textAreaOutput);
        flowPane.getChildren().add(findDates);



        findDates.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textAreaOutput.setText("");
                String input = textAreaInput.getText();
                Matcher matcher = DATE.matcher(input);
                StringBuilder output = new StringBuilder();
                while (matcher.find()) {
                    output.append(matcher.group());
                    output.append("\n");
                }
                textAreaOutput.setText(output.toString());
            }
        });
        primaryStage.setScene(new Scene(flowPane, 600, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
