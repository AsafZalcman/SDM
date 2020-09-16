package ui.javafx.components.order.createOrder.steps.orderSummary.storeSummary;

import dtoModel.ItemDto;
import dtoModel.OrderDto;
import dtoModel.StoreDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import ui.javafx.utils.FormatUtils;

public class OrderStoreSummaryController {

    @FXML
    private TableView<ItemDto> itemsTableView;

    @FXML
    private TableColumn<ItemDto, Integer> itemIdColumn;

    @FXML
    private TableColumn<ItemDto, String> itemNameColumn;

    @FXML
    private TableColumn<ItemDto, String> itemPurchaseFormColumn;

    @FXML
    private TableColumn<ItemDto, Double> itemAmountColumn;

    @FXML
    private TableColumn<ItemDto, Double> itemPriceColumn;

    @FXML
    private TableColumn<ItemDto, Double> itemTotalPriceColumn;

    @FXML
    private TableColumn<ItemDto, Double> itemFromDiscountColumn;

    @FXML
    private Label storeIdLabel;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label ppkLabel;

    @FXML
    private Label distanceFromCustomerLabel;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private Label totalItemsPriceLabel;

    @FXML
    private Label numberOfDifferentItemsLabel;

    @FXML
    private Label locationLabel;

    private Pair<StoreDto, OrderDto> m_StoreAndOrderPair;

    private SimpleBooleanProperty isOrderStoreAvailable;

    public void setOrderOfStore(Pair<StoreDto,OrderDto> i_StoreAndOrderPair)
    {
        m_StoreAndOrderPair= i_StoreAndOrderPair;
        isOrderStoreAvailable.set(true);
    }

    public OrderStoreSummaryController()
    {
        isOrderStoreAvailable = new SimpleBooleanProperty(false);
    }

    private void initTableView() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPurchaseFormColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseForm"));
        itemAmountColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(Double.parseDouble(FormatUtils.DecimalFormat.format(cellData.getValue().getAmountOfSell()))).asObject());
        itemPriceColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(Double.parseDouble(FormatUtils.DecimalFormat.format(cellData.getValue().getPrice()))).asObject());
        itemTotalPriceColumn.setCellValueFactory(cellData-> new SimpleDoubleProperty(Double.parseDouble(FormatUtils.DecimalFormat.format(cellData.getValue().getTotalPrice()))).asObject());
        itemFromDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("fromDiscount"));
    }

    @FXML
    private void initialize() {
        initTableView();
        isOrderStoreAvailable.addListener((observable, oldValue, newValue) -> {
            StoreDto storeDto = m_StoreAndOrderPair.getKey();
            OrderDto orderDto = m_StoreAndOrderPair.getValue();
            storeIdLabel.setText(storeDto.getId().toString());
            storeNameLabel.setText(storeDto.getName());
            deliveryPriceLabel.setText(FormatUtils.DecimalFormat.format(orderDto.getDeliveryPrice()));
            ppkLabel.setText(FormatUtils.DecimalFormat.format(storeDto.getPPK()));
            distanceFromCustomerLabel.setText(FormatUtils.DecimalFormat.format(storeDto.getLocation().distance(orderDto.getDestLocation())));
            totalItemsPriceLabel.setText(FormatUtils.DecimalFormat.format(orderDto.getTotalItemsPrice()));
            numberOfDifferentItemsLabel.setText(FormatUtils.DecimalFormat.format(orderDto.getTotalItemsKind()));
            locationLabel.setText("(" + storeDto.getLocation().x +"," + storeDto.getLocation().y +")");
            itemsTableView.getItems().addAll(orderDto.getItemsDto());
        });
    }
}
