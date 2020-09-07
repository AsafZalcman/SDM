package ui.javafx.components.main;


import dtoModel.*;


import dtoModel.StorageItemDto;


import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import ui.javafx.components.viewStores.ViewStoresController;
import ui.javafx.managers.CustomersUIManager;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.tasks.LoadXmlTask;
import ui.javafx.utils.FormatUtils;


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

    /** ====================================ViewItemsTAB**/
    /** ====================================ViewItemsTAB start**/

    @FXML
    private Tab viewItemsTransparentTab;

    @FXML
    private TableView<StorageItemDto> itemsTableViewInViewItemsTab;

    @FXML
    private TableColumn<StorageItemDto, Integer> itemIdTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemNameTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemPurchaseFormTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, Long> itemHowManyStoresSellTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemAveragePriceTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemTotalSoldSoFarTableColumnInViewItems;

    /**ViewItemsTAB end**/

    /**ViewCustomersTAB start**/

    @FXML
    private Tab viewCustomersTransparentTab;

    @FXML
    private TableView<CustomerDto> customersTableViewInViewCustomersTab;

    @FXML
    private TableColumn<CustomerDto, Integer> customerIdTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerNameTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> LocationTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, Integer> customerHowManyOrdersTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerAveragePriceExcludeDeliveryTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerAverageDeliveryPaymentTableColumnInViewCustomer;

    @FXML
    private Button viewCustomersSideBarButton;

    @FXML
    void viewCustomersSideBarButtonOnClick(ActionEvent event) {
        customersTableViewInViewCustomersTab.getItems().clear();
        customersTableViewInViewCustomersTab.getItems().addAll((m_CustomersUIManager.getAllCustomers()));
        mainTabPain.getSelectionModel().select(viewCustomersTransparentTab);
    }

    private void initViewCustomersTabComponents() {
        customerIdTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("name"));
        LocationTableColumnInViewCustomers.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getLocation().x +"," + cellData.getValue().getLocation().y));
        customerHowManyOrdersTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("ordersCount"));
        customerAveragePriceExcludeDeliveryTableColumnInViewCustomers.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAverageOrdersPriceExcludeDelivery())));
        customerAverageDeliveryPaymentTableColumnInViewCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAverageDeliveryPayment())));
    }

    /** ====================================ViewCustomersTAB end**/


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
    private ItemsUIManger m_ItemsUIManger;
    private CustomersUIManager m_CustomersUIManager;

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
        m_ItemsUIManger = new ItemsUIManger();
        m_CustomersUIManager=new CustomersUIManager();
        //    wordToTileController = new HashMap<>();
    }

    @FXML private Pane viewStoresComponent;
    @FXML private ViewStoresController viewStoresComponentController;


    @FXML
    private void initialize() {

        if(viewStoresComponentController != null){
            viewStoresComponentController.setMainController(this);
        }

        itemsSideBarButton.disableProperty().bind(isDataLoaded.not());
        viewMapSideBarButton.disableProperty().bind(isDataLoaded.not());
        makeAnOrderSideBarButton.disableProperty().bind(isDataLoaded.not());
        ordersHistorySideBarButton.disableProperty().bind(isDataLoaded.not());
        viewCustomersSideBarButton.disableProperty().bind(isDataLoaded.not());
        storesSideBarButton.disableProperty().bind(isDataLoaded.not());
        xmlFilePathLabel.textProperty().bind(selectedFileProperty);
        xmlLoadMessageTextArea.textProperty().bind(loadMessageFileProperty);
        mainTabPain.getSelectionModel().select(loadXmlTransparentTab);


        initViewItemsTabComponents();

        initViewCustomersTabComponents();
    }

    private void initViewItemsTabComponents() {
        itemIdTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getItemDto().getId()).asObject());
        itemNameTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemDto().getItemName()));
        itemPurchaseFormTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemDto().getPurchaseForm().getValue()));
        itemHowManyStoresSellTableColumnInViewItems.setCellValueFactory(new PropertyValueFactory<>("storesSellIt"));
        itemAveragePriceTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAvgPrice())));
        itemTotalSoldSoFarTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getSales())));
    }

    @FXML
    void itemsSideBarButtonOnClick(ActionEvent event) {
        itemsTableViewInViewItemsTab.getItems().clear();
        itemsTableViewInViewItemsTab.getItems().addAll((m_ItemsUIManger.getAllStorageItems()));
        mainTabPain.getSelectionModel().select(viewItemsTransparentTab);
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
        viewStoresComponentController.fetchStores();
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }

}
