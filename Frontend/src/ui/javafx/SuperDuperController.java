package ui.javafx;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.tasks.LoadXmlTask;

import java.io.File;
import java.util.Collection;

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

    @FXML
    private Label storeBasicDetailsNameLabel;

    @FXML
    private Label storeIDLabel;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storePPKLabel;

    @FXML
    private Label storeIncomesDeliveriesLabel;

    @FXML
    private ListView<StoreDto> storesCollectionListView;

    @FXML
    private ListView<ItemDto> storeItemCollectionListView;

    //StoreItem Labels:
    @FXML
    private Label storeItemPurchaseFormLabel;

    @FXML
    private Label storeItemNameLabel;

    @FXML
    private Label storeItemIDLabel;

    @FXML
    private Label storeItemPriceLabel;

    @FXML
    private Label storeItemSoldSoFarLabel;



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

    private StoreUIManager m_storeUIManager;


    public SuperDuperController() {
        //    totalWords = new SimpleLongProperty(0);
        //    totalLines = new SimpleLongProperty(0);
        //    totalDistinctWords = new SimpleIntegerProperty(0);
        //    totalProcessedWords = new SimpleIntegerProperty(0);
        selectedFileProperty = new SimpleStringProperty();
        loadMessageFileProperty = new SimpleStringProperty();

        isFileSelected = new SimpleBooleanProperty(false);
        isDataLoaded = new SimpleBooleanProperty(false);
        m_storeUIManager = new StoreUIManager();

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
        storesCollectionListView.setCellFactory(param -> new ListCell<StoreDto>(){
            @Override
            protected void updateItem(StoreDto storeDto, boolean empty){
                super.updateItem(storeDto, empty);

                if(empty || storeDto == null){
                    setText(null);
                }else{
                    setText(storeDto.getName());
                }
            }
        });
        storeItemCollectionListView.setCellFactory(param -> new ListCell<ItemDto>(){
            @Override
            protected void updateItem(ItemDto itemDto, boolean empty){
                super.updateItem(itemDto, empty);

                if(empty || itemDto == null){
                    setText(null);
                }else{
                    setText(itemDto.getItemName());
                }
            }
        });


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
        storesCollectionListView.getItems().clear();
        storesCollectionListView.getItems().addAll(m_storeUIManager.getAllStores());
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }

}
