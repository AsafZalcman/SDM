package ui.javafx.components.main;


import dtoModel.*;

import dtoModel.ItemDto;
import dtoModel.StorageItemDto;
import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.javafx.components.storeDiscount.DiscountController;
import ui.javafx.managers.CustomersUIManager;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.tasks.LoadXmlTask;
import ui.javafx.utils.FormatUtils;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.File;
import java.io.IOException;

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

   /** Store Items Table **/
   @FXML
   private TableView<ItemDto> storeItemTableViewInViewStoresTab;

   @FXML
   private TableColumn<ItemDto, Integer> storeItemIDTableColumn;

    @FXML
    private TableColumn<ItemDto, String> storeItemNameTableColumn;

    @FXML
    private TableColumn<ItemDto, String> storeItemPurchaseFormTableColumn;

    @FXML
    private TableColumn<ItemDto, Double> storeItemPriceTableColumn;

    @FXML
    private TableColumn<ItemDto, Double> storeItemSoldSoFarTableColumn;

    @FXML
    private FlowPane storeDiscountFlowPane;
    @FXML
    private Label storeDiscountsAvailableLabel;
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

    @FXML
    private Label storeOrdersAvailableLabel;
    /** ====================================ViewItemsTAB end**/

    /** ====================================ViewCustomersTAB start**/

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


    @FXML
    private void initialize() {
        itemsSideBarButton.disableProperty().bind(isDataLoaded.not());
        viewMapSideBarButton.disableProperty().bind(isDataLoaded.not());
        makeAnOrderSideBarButton.disableProperty().bind(isDataLoaded.not());
        ordersHistorySideBarButton.disableProperty().bind(isDataLoaded.not());
        viewCustomersSideBarButton.disableProperty().bind(isDataLoaded.not());
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

        storesCollectionListView.getSelectionModel().selectedItemProperty().addListener((observable, PrevStoreDto, currentStoreDto) ->{
            createStoreDiscounts(currentStoreDto);
            storeItemTableViewInViewStoresTab.getItems().clear();
            storeItemTableViewInViewStoresTab.getItems().addAll(m_storeUIManager.getAllItemsOfStore(currentStoreDto.getId()));
        });


        initViewItemsTabComponents();

        initViewCustomersTabComponents();

        initViewStoreItemsTabComponents();
    }

    private void initViewStoreItemsTabComponents(){
        storeItemIDTableColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        storeItemNameTableColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemName()));
        storeItemPurchaseFormTableColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getPurchaseForm().getValue()));
        storeItemPriceTableColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        storeItemSoldSoFarTableColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getAmountOfSell()).asObject());
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
        storesCollectionListView.getItems().clear();
        storesCollectionListView.getItems().addAll(m_storeUIManager.getAllStores());
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }

    private void createStoreDiscounts(StoreDto i_StoreDto)
    {
        storeDiscountFlowPane.getChildren().clear();
        storeDiscountsAvailableLabel.setVisible(i_StoreDto.getAllDiscounts().isEmpty());
        for (StoreDiscountDto storeDiscountDto : i_StoreDto.getAllDiscounts()
                ) {
            createStoreDiscount(storeDiscountDto);
        }
    }
    private void createStoreDiscount(StoreDiscountDto i_DiscountDto)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.STORE_DISCOUNT_FXML_RESOURCE);
            Node singleDiscount = loader.load();

            DiscountController discountController = loader.getController();
            discountController.setStoreDiscountDto(i_DiscountDto);
            storeDiscountFlowPane.getChildren().add(singleDiscount);
           // wordToTileController.put(word, singleWordController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
