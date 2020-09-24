package ui.javafx.components.main;


import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import ui.javafx.components.createStore.CreateStoreController;
import ui.javafx.components.map.ViewMapController;
import ui.javafx.components.order.OrdersHistoryController;
import ui.javafx.components.updateStores.UpdateStoresController;
import ui.javafx.components.viewCustomer.ViewCustomerController;
import ui.javafx.components.viewItems.ViewItemsController;
import ui.javafx.components.order.createOrder.CreateOrderController;
import ui.javafx.components.viewStores.ViewStoresController;
import ui.javafx.managers.CustomersUIManager;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.managers.StoreUIManager;
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
    private Button createStoreSideBarButton;

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
    private Label xmlLoadMessageLabel;

    @FXML
    private ProgressBar loadXmlProgressBar;

    @FXML
    private Label loadXmlPercentLabel;

    @FXML
    private Tab viewItemsTransparentTab;

    @FXML
    private Tab viewCustomersTransparentTab;

    @FXML
    private Tab updateStoresTransparentTab;

    @FXML
    private Button viewCustomersSideBarButton;

    @FXML Button updateStoresSideBarButton;

    @FXML
    private Tab viewOrdersTransparentTab;

    @FXML
    private Tab createStoreTransparentTab;

    @FXML
    private Tab makeAnOrderTransparentTab;

    @FXML
    private Tab viewMapTransparentTab;

    @FXML
    void viewCustomersSideBarButtonOnClick(ActionEvent event) {
        viewCustomersComponentController.fetchCustomers();
        mainTabPain.getSelectionModel().select(viewCustomersTransparentTab);
    }


    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isDataLoaded;
    private SimpleStringProperty selectedFileProperty;
    private SimpleStringProperty loadMessageFileProperty;

    private Stage primaryStage;

    private StoreUIManager m_storeUIManager;
    private ItemsUIManger m_ItemsUIManger;
    private CustomersUIManager m_CustomersUIManager;

    public SuperDuperController() {
        selectedFileProperty = new SimpleStringProperty();
        loadMessageFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        isDataLoaded = new SimpleBooleanProperty(false);

        m_storeUIManager = StoreUIManager.getInstance();
        m_ItemsUIManger = ItemsUIManger.getInstance();
        m_CustomersUIManager=new CustomersUIManager();
    }

    @FXML private Pane viewStoresComponent;
    @FXML private ViewStoresController viewStoresComponentController;
    @FXML private Pane viewItemsComponent;
    @FXML private ViewItemsController viewItemsComponentController;
    @FXML private Pane viewCustomersComponent;
    @FXML private ViewCustomerController viewCustomersComponentController;
    @FXML private Pane updateStoresComponent;
    @FXML private UpdateStoresController updateStoresComponentController;
    @FXML private Pane viewOrdersComponent;
    @FXML private OrdersHistoryController viewOrdersComponentController;
    @FXML private Pane makeOrderComponent;
    @FXML private CreateOrderController makeOrderComponentController;
    @FXML private Pane viewMapComponent;
    @FXML private ViewMapController viewMapComponentController;
    @FXML private CreateStoreController createStoreComponentController;


    @FXML private VBox vBox;
    @FXML
    private void initialize() {

        if(viewStoresComponentController != null && viewItemsComponentController != null && viewCustomersComponentController!= null && viewOrdersComponentController != null && makeOrderComponentController != null && viewMapComponentController != null){
            viewStoresComponentController.setMainController(this);
            viewItemsComponentController.setMainController(this);
            viewCustomersComponentController.setMainController(this);
            updateStoresComponentController.setMainController(this);
            viewOrdersComponentController.setMainController(this);
            makeOrderComponentController.setMainController(this);
            viewMapComponentController.setMainController(this);
            createStoreComponentController.setMainController(this);
        }

        itemsSideBarButton.disableProperty().bind(isDataLoaded.not());
        viewMapSideBarButton.disableProperty().bind(isDataLoaded.not());
        makeAnOrderSideBarButton.disableProperty().bind(isDataLoaded.not());
        ordersHistorySideBarButton.disableProperty().bind(isDataLoaded.not());
        viewCustomersSideBarButton.disableProperty().bind(isDataLoaded.not());
        createStoreSideBarButton.disableProperty().bind(isDataLoaded.not());
        storesSideBarButton.disableProperty().bind(isDataLoaded.not());
        updateStoresSideBarButton.disableProperty().bind(isDataLoaded.not());
        xmlFilePathLabel.textProperty().bind(selectedFileProperty);
        xmlLoadMessageLabel.textProperty().bind(loadMessageFileProperty);
        mainTabPain.getSelectionModel().select(loadXmlTransparentTab);

        storesTransparentTab.disableProperty().bind(storesTransparentTab.selectedProperty().not());
        loadXmlTransparentTab.disableProperty().bind(loadXmlTransparentTab.selectedProperty().not());
        viewCustomersTransparentTab.disableProperty().bind(viewCustomersTransparentTab.selectedProperty().not());
        viewItemsTransparentTab.disableProperty().bind(viewItemsTransparentTab.selectedProperty().not());
        updateStoresTransparentTab.disableProperty().bind(updateStoresTransparentTab.selectedProperty().not());
        makeAnOrderTransparentTab.disableProperty().bind(makeAnOrderTransparentTab.selectedProperty().not());
        viewOrdersTransparentTab.disableProperty().bind(viewOrdersTransparentTab.selectedProperty().not());
        createStoreTransparentTab.disableProperty().bind(createStoreTransparentTab.selectedProperty().not());

    }

    @FXML
    void itemsSideBarButtonOnClick(ActionEvent event) {
        viewItemsComponentController.fetchStorageItems();
        mainTabPain.getSelectionModel().select(viewItemsTransparentTab);
    }

    @FXML
    void loadXmlBarButtonOnClick(ActionEvent event) {
        clearXmlTab();
        mainTabPain.getSelectionModel().select(loadXmlTransparentTab);
    }

    @FXML
    void makeAnOrderSideBarButtonOnClick(ActionEvent event) {
        OrdersUIManager.getInstance().cleanLastOrder();
        makeOrderComponentController.fetchMakeOrderComponent();
        mainTabPain.getSelectionModel().select(makeAnOrderTransparentTab);
    }

    @FXML
    void createStoreSideBarButtonOnClick(ActionEvent event) {
        createStoreComponentController.fetchData();
        mainTabPain.getSelectionModel().select(createStoreTransparentTab);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        int numberOfButtons = vBox.getChildren().size() -1;
        vBox.prefHeightProperty().bind(primaryStage.heightProperty());
        updateStoresSideBarButton.prefHeightProperty().bind(Bindings.divide(vBox.heightProperty(), numberOfButtons));
        viewCustomersSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        itemsSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        loadXmlSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        makeAnOrderSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        storesSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        ordersHistorySideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        viewMapSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
        createStoreSideBarButton.prefHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), numberOfButtons));
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
        viewOrdersComponentController.fetchOrders();
        mainTabPain.getSelectionModel().select(viewOrdersTransparentTab);

    }

    @FXML
    void viewMapSideBarButtonOnClick(ActionEvent event) {
        viewMapComponentController.draw();
        mainTabPain.getSelectionModel().select(viewMapTransparentTab);
    }

   @FXML
    void storesSideBarButtonOnClick(ActionEvent event) {
        viewStoresComponentController.fetchStores();
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }

    @FXML
    void updateStoresSideBarButtonOnClick(ActionEvent event){
        updateStoresComponentController.fetchData();
        mainTabPain.getSelectionModel().select(updateStoresTransparentTab);
    }
}
