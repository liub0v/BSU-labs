import Stages.StartPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Zuma extends Application {

    @Override
    public void start(Stage primaryStage) {
        StartPage startPage = new StartPage(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
