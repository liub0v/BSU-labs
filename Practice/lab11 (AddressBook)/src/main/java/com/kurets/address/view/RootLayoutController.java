package com.kurets.address.view;

import com.kurets.address.MainApp;
import com.kurets.address.util.CustomSAXHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;


public class RootLayoutController {


    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    @FXML
    private void handleOpen() {


        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter(
                "BIN files (*.dat) ", "*.dat");
        fileChooser.getExtensionFilters().addAll(xmlFilter, binFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            if (fileChooser.getSelectedExtensionFilter().equals(fileChooser.getExtensionFilters().get(0))) {

                mainApp.loadPersonDataFromXMLFile(file);

            }
            if (fileChooser.getSelectedExtensionFilter().equals(fileChooser.getExtensionFilters().get(1))) {

                mainApp.loadPersonDataFromBinaryFile(file);


            }
        }


    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter(
                "BIN files (*.dat) ", "*.dat");
        fileChooser.getExtensionFilters().addAll(xmlFilter, binFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension

            if (fileChooser.getSelectedExtensionFilter().equals(fileChooser.getExtensionFilters().get(0))) {
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                mainApp.savePersonDataToXMLFile(file);
            }
            if (fileChooser.getSelectedExtensionFilter().equals(fileChooser.getExtensionFilters().get(1))) {
                if (!file.getPath().endsWith(".dat")) {
                    file = new File(file.getPath() + ".dat");
                }
                mainApp.savePersonDataToBinaryFile(file);

            }

        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleShowBirthdayStatistics() {
        mainApp.showBirthdayStatistics();
    }

    @FXML
    private void handleOtherStatistics() throws ParserConfigurationException, SAXException, IOException {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().addAll(xmlFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        CustomSAXHandler handler = new CustomSAXHandler();
        if (file != null) {
            parser.parse(file, handler);
            mainApp.showOtherStatistics(handler);
        }

    }
}