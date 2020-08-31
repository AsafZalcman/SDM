package ui.javafx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.javafx.tasks.LoadXmlTask;

import java.io.File;

public class SuperDuperController {
    @FXML
    private Button loadXmlSideBarButton;

    @FXML
    private Button storesSideBarButton;

    @FXML
    private Button itemsSideBarButton;

    @FXML
    private Button ordersHistorySideBarButton;

    @FXML
    private Button viewMapSideBarButton;

    @FXML
    private Button makeAnOrderSideBarButton;

    @FXML
    private TabPane mainTabPain;

    @FXML
    private Tab storesTransparentTab;

    @FXML
    private Tab loadXmlTransparentTab;

    @FXML
    private Button openFileButton;

    @FXML
    private Label xmlFilePathLabel;

    @FXML
    private TextArea xmlLoadMessageTextArea;

    @FXML
    private ProgressBar loadXmlProgressBar;

    @FXML
    private Label loadXmlPercentLabel;


    //   private SimpleLongProperty totalWords;
//   private SimpleLongProperty totalLines;
//   private SimpleIntegerProperty totalDistinctWords;
//   private SimpleIntegerProperty totalProcessedWords;
//   private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isDataLoaded;
    private SimpleStringProperty selectedFileProperty;
    private SimpleStringProperty loadMessageFileProperty;

    private Stage primaryStage;


    public SuperDuperController() {
        //    totalWords = new SimpleLongProperty(0);
        //    totalLines = new SimpleLongProperty(0);
        //    totalDistinctWords = new SimpleIntegerProperty(0);
        //    totalProcessedWords = new SimpleIntegerProperty(0);
        selectedFileProperty = new SimpleStringProperty();
        loadMessageFileProperty = new SimpleStringProperty();

        isFileSelected = new SimpleBooleanProperty(false);
        isDataLoaded = new SimpleBooleanProperty(false);

        //    wordToTileController = new HashMap<>();
    }


    @FXML
    private void initialize() {
        itemsSideBarButton.disableProperty().bind(isDataLoaded.not());
        viewMapSideBarButton.disableProperty().bind(isDataLoaded.not());
        makeAnOrderSideBarButton.disableProperty().bind(isDataLoaded.not());
        ordersHistorySideBarButton.disableProperty().bind(isDataLoaded.not());
        storesSideBarButton.disableProperty().bind(isDataLoaded.not());
        xmlFilePathLabel.textProperty().bind(selectedFileProperty);
        xmlLoadMessageTextArea.textProperty().bind(loadMessageFileProperty);
        mainTabPain.getSelectionModel().select(loadXmlTransparentTab);


    }

    @FXML
    void itemsSideBarButtonOnClick(ActionEvent event) {

    }

    @FXML
    void loadXmlBarButtonOnClick(ActionEvent event) {
        clearXmlTab();
        mainTabPain.getSelectionModel().select(loadXmlTransparentTab);

    }

    @FXML
    void makeAnOrderSideBarButtonOnClick(ActionEvent event) {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    void openFileButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
        LoadXmlTask loadXmlTask = new LoadXmlTask(absolutePath);
        loadXmlProgressBar.progressProperty().bind(loadXmlTask.progressProperty());
        isDataLoaded.bind(loadXmlTask.valueProperty());
        loadMessageFileProperty.bind(loadXmlTask.messageProperty());
        loadXmlPercentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        loadXmlTask.progressProperty(),
                                        100)),
                        " %"));
        new Thread(loadXmlTask).start();
    }

    private void clearXmlTab() // not sure we want this method - clean the xml tab
    {
//        xmlFilePathLabel.textProperty().unbind();
//        xmlFilePathLabel.textProperty().setValue("");
//        loadXmlMessageLabel.textProperty().unbind();
//        loadXmlMessageLabel.textProperty().set("");
//        loadXmlProgressBar.progressProperty().unbind();
//        loadXmlProgressBar.progressProperty().setValue(0);

    }

    @FXML
    void ordersHistorySideBarButtonOnClick(ActionEvent event) {

    }

    @FXML
    void viewMapSideBarButtonOnClick(ActionEvent event) {

    }

    @FXML
    void storesSideBarButtonOnClick(ActionEvent event) {
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }
}
