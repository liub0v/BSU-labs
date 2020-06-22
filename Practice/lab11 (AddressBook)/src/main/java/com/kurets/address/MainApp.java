package com.kurets.address;

import com.kurets.address.model.Person;
import com.kurets.address.util.CustomSAXHandler;
import com.kurets.address.util.ReadObjectsHelper;
import com.kurets.address.util.WriteObjectsHelper;
import com.kurets.address.view.*;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));

    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        this.primaryStage.getIcons().add(new Image("file:com/kurets/address/resources/images/address_book_32.png"));
        initRootLayout();
        Start start = new Start(primaryStage);
        showPersonOverview();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromXMLFile(file);
        }
    }

    private void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("images/edit.png"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("images/calendar.png"));

            // Set the persons into the controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOtherStatistics(CustomSAXHandler handler) {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/OtherStatistics.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setTitle("Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("images/calendar.png"));

            // Set the persons into the controller.
            OtherStatisticsController controller = loader.getController();
            controller.ShowStatistic(handler);
            controller.setStage(dialogStage);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    public void loadPersonDataFromXMLFile(File file) {

        personData.clear();
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc = null;
        try {

            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();

            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                doc = db.parse(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert doc != null;
            doc.getDocumentElement().normalize();

            NodeList nodeList1;
            NodeList nodeList;

            nodeList = doc.getElementsByTagName("Person");
            for (int s = 0; s < nodeList.getLength(); s++) {
                Node node = nodeList.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Person person = new Person();
                    Element el = (Element) node;
                    nodeList1 = el.getElementsByTagName("firstName");
                    person.setFirstName(getValue(nodeList1));
                    nodeList1 = el.getElementsByTagName("lastName");
                    person.setLastName((getValue(nodeList1)));
                    nodeList1 = el.getElementsByTagName("street");
                    person.setStreet(getValue(nodeList1));
                    nodeList1 = el.getElementsByTagName("city");
                    person.setCity(getValue(nodeList1));
                    nodeList1 = el.getElementsByTagName("postalCode");
                    person.setPostalCode(Integer.parseInt(getValue(nodeList1)));
                    nodeList1 = el.getElementsByTagName("birthday");
                    person.setBirthday(LocalDate.parse(getValue(nodeList1)));

                    personData.add(person);
                }
            }

        } catch (Exception ignored) {

        }

    }

    public void loadPersonDataFromBinaryFile(File file) {
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            personData.clear();
            ListProperty<String> personsStr = new SimpleListProperty<>(ReadObjectsHelper.readListProp(stream));
            for (String s : personsStr) {
                personData.add(new Person(s));
            }
            stream.close();
        } catch (Exception ignored) {

        }

    }

    private String getValue(NodeList fields) {
        NodeList list = fields.item(0).getChildNodes();
        if (list.getLength() > 0) {
            return list.item(0).getNodeValue();
        } else {
            return "";
        }
    }

    public void savePersonDataToBinaryFile(File file) {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));

            ListProperty<Person> persons = new SimpleListProperty<>(personData);

            WriteObjectsHelper.writeListProp(stream, persons);
            stream.close();
        } catch (Exception ignored) {

        }
    }

    public void savePersonDataToXMLFile(File file) {
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element root = doc.createElement("Persons");
            root.setAttribute("lang", "en");
            doc.appendChild(root);
            if (personData.size() == 0)
                return;
            for (Person person : personData) {

                Element user = doc.createElement("Person");
                root.appendChild(user);
                Element e1 = doc.createElement("firstName");
                e1.setTextContent(person.getFirstName());
                user.appendChild(e1);
                Element e2 = doc.createElement("lastName");
                e2.setTextContent(person.getLastName());
                user.appendChild(e2);
                Element e3 = doc.createElement("street");
                e3.setTextContent(person.getStreet());
                user.appendChild(e3);
                Element e4 = doc.createElement("city");
                e4.setTextContent(person.getCity());
                user.appendChild(e4);
                Element e5 = doc.createElement("postalCode");
                e5.setTextContent(Integer.toString(person.getPostalCode()));
                user.appendChild(e5);
                Element e6 = doc.createElement("birthday");
                e6.setTextContent(person.getBirthdayStr());
                user.appendChild(e6);
            }

        } catch (ParserConfigurationException ignored) {

        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc, file);
        }
    }

    private void writeDocument(Document document, File path)
            throws TransformerFactoryConfigurationError {
        Transformer trf;
        DOMSource src;
        FileOutputStream fos;
        try {
            trf = TransformerFactory.newInstance().newTransformer();
            src = new DOMSource(document);
            fos = new FileOutputStream(path);

            StreamResult result = new StreamResult(fos);
            trf.setOutputProperty(OutputKeys.INDENT, "yes");
            trf.transform(src, result);
        } catch (TransformerException | IOException ignored) {

        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}