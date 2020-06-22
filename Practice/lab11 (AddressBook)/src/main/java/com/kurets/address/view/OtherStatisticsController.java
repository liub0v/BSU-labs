package com.kurets.address.view;

import com.kurets.address.util.CustomSAXHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OtherStatisticsController {

    @FXML
    private Label averageAgeLabel;
    @FXML
    private Label numberPersonLabel;
    private Stage stage;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleOK() {
        stage.close();

    }

    public void ShowStatistic(CustomSAXHandler handler) {

        averageAgeLabel.setText(String.valueOf(handler.getAverageOld()));
        numberPersonLabel.setText(String.valueOf(handler.getPersonsNumber()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
