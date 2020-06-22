import Observer.DisplayLogger;
import Observer.KeyLogger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Key;

import java.awt.*;
import java.io.IOException;

public class MainApp extends Application {

    private Label text = new Label();
    private ListView<Key> listView = new ListView<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws AWTException, IOException {

        KeyLogger keyLogger = new KeyLogger();
        BorderPane pane = new BorderPane();
        primaryStage.setTitle("Key Logger");
        DisplayLogger dp = new DisplayLogger(keyLogger, listView, text);
        pane.setLeft(listView);
        pane.setCenter(text);

        Scene scene = new Scene(pane, 700, 500);
        scene.getStylesheets().add((getClass().getResource("/view/style.css")).toExternalForm());
        pane.getStyleClass().add("background");

        scene.getRoot().requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getRoot().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Key key = new Key(event.getCode().getName());
                keyLogger.setDate(key);

            }
        });


    }

}
