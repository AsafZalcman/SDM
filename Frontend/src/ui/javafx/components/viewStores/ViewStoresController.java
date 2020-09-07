package ui.javafx.components.viewStores;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import dtoModel.ItemDto;
import dtoModel.OrderDto;
import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.components.storeDiscount.DiscountController;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.SDMResourcesConstants;

public class ViewStoresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label storeIDLabel;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storePPKLabel;

    @FXML
    private Label storeIncomesDeliveriesLabel;

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
    private Label storeOrdersAvailableLabel;

    @FXML
    private TableView<OrderDto> ordersTableViewInViewStoresTab;

    @FXML
    private TableColumn<OrderDto, Date> orderDateTableColumnInViewStores;

    @FXML
    private TableColumn<OrderDto, Integer> orderTotalItemsTableColumnInViewStores;

    @FXML
    private TableColumn<OrderDto, Double> orderItemsCostTableColumnInViewStores;

    @FXML
    private TableColumn<OrderDto, Double> orderDeliveryCostTableColumnInViewStores;

    @FXML
    private TableColumn<OrderDto, Double> orderTotalCostTableColumnInViewStores;

    @FXML
    private ScrollPane storeDiscountScrollPane;

    @FXML
    private FlowPane storeDiscountFlowPane;

    @FXML
    private Label storeDiscountsAvailableLabel;

    @FXML
    private ListView<StoreDto> storesCollectionListView;

    private SuperDuperController superDuperController;

    @FXML
    void initialize() {

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
            setStoreItemsForTheSelectedStoreInViewStoreTab(currentStoreDto);
            setBasicDetailsForTheSelectedStoreInViewStoreTab(currentStoreDto);
            setStoreOrdersForTheSelectedStoreInViewStoreTab(currentStoreDto);
        });

        initViewStoreItemsTabComponents();

        initViewStoreOrdersTabComponents();
    }

    private StoreUIManager m_StoreUIManager;

    public ViewStoresController(){
        m_StoreUIManager = new StoreUIManager();
    }

    public void setMainController(SuperDuperController superDuperController){
        this.superDuperController = superDuperController;
    }


    private void setStoreOrdersForTheSelectedStoreInViewStoreTab(StoreDto i_CurrentStoreDto) {
        if(i_CurrentStoreDto.getAllOrders().size() < 1){
            storeOrdersAvailableLabel.setVisible(true);
            ordersTableViewInViewStoresTab.setVisible(false);
        }
        else{
            storeOrdersAvailableLabel.setVisible(false);
            ordersTableViewInViewStoresTab.setVisible(true);
            ordersTableViewInViewStoresTab.getItems().clear();
            ordersTableViewInViewStoresTab.getItems().addAll(i_CurrentStoreDto.getAllOrders());
        }
    }

    private void initViewStoreOrdersTabComponents(){
        orderDateTableColumnInViewStores.setCellValueFactory(cellData-> new SimpleObjectProperty<Date>(cellData.getValue().getDate()));
        orderTotalItemsTableColumnInViewStores.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getTotalItemsCount()).asObject());
        orderItemsCostTableColumnInViewStores.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getTotalItemsPrice()).asObject());
        orderDeliveryCostTableColumnInViewStores.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getDeliveryPrice()).asObject());
        orderTotalCostTableColumnInViewStores.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getTotalOrderPrice()).asObject());
    }

    private void setBasicDetailsForTheSelectedStoreInViewStoreTab(StoreDto i_CurrentStoreDto) {
        storeIDLabel.setText(i_CurrentStoreDto.getId().toString());
        storeNameLabel.setText(i_CurrentStoreDto.getName());
        storePPKLabel.setText(i_CurrentStoreDto.getPPK().toString());
        storeIncomesDeliveriesLabel.setText(i_CurrentStoreDto.getDeliveriesIncomes() == null ? "No deliveries income to show":i_CurrentStoreDto.getDeliveriesIncomes().toString());
    }

    private void setStoreItemsForTheSelectedStoreInViewStoreTab(StoreDto i_CurrentStoreDto){
        storeItemTableViewInViewStoresTab.getItems().clear();
        storeItemTableViewInViewStoresTab.getItems().addAll(m_StoreUIManager.getAllItemsOfStore(i_CurrentStoreDto.getId()));
    }

    private void initViewStoreItemsTabComponents(){
        storeItemIDTableColumn.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        storeItemNameTableColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemName()));
        storeItemPurchaseFormTableColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getPurchaseForm().getValue()));
        storeItemPriceTableColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        storeItemSoldSoFarTableColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(cellData.getValue().getAmountOfSell()).asObject());
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

    public void fetchStores() {
        storesCollectionListView.getItems().clear();
        storesCollectionListView.getItems().addAll(m_StoreUIManager.getAllStores());
    }
}
