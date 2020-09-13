package ui.javafx.components.order.createOrder.steps.orderSummary;

import dtoModel.StorageOrderDto;
import dtoModel.StoreDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.components.order.createOrder.steps.orderSummary.storeSummary.OrderStoreSummaryController;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;

public class OrderSummaryController extends StepController {

    @Override
    public String getStepName() {
        return "Order Summary";
    }

    @Override
    public void onStepFinish() {
        OrdersUIManager.getInstance().executeOrder();
    }

    @FXML
    private FlowPane storeOrderSummaryFlowPane;

    @FXML
    private ComboBox<StoreDto> storesComboBox;

    private StorageOrderDto m_StorageOrderDto;

    public void setStorageOrder(StorageOrderDto i_StorageOrder)
    {
        m_StorageOrderDto=i_StorageOrder;
        displayStoresIfNeeded();
    }

    @FXML
    private void initialize() {
        m_StorageOrderDto = OrdersUIManager.getInstance().getCurrentOrder();
        m_StepComplete.set(true);
        displayStoresIfNeeded();
        storesComboBox.setConverter(new StringConverter<StoreDto>() {
            @Override
            public String toString(StoreDto storeDto) {
                return storeDto.getName() + "(ID: " + storeDto.getId() + ")";
            }

            @Override
            public StoreDto fromString(String storeDtoString) {
                return null;
            }
        });
    }

        @FXML
    void storesComboBoxOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.ORDER_STORE_SUMMARY_FXML_RESOURCE);
            Node singleStoreOrderSummary = loader.load();

            OrderStoreSummaryController orderStoreSummaryController = loader.getController();
            StoreDto storeDto = storesComboBox.getValue();
            orderStoreSummaryController.setOrderOfStore(new Pair<>(storeDto, m_StorageOrderDto.getStoresToOrders().get(storeDto)));
            storeOrderSummaryFlowPane.getChildren().add(singleStoreOrderSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayStoresIfNeeded()
    {
        if(m_StorageOrderDto!=null)
        {
            storesComboBox.getItems().addAll(m_StorageOrderDto.getStoresToOrders().keySet());
        }
    }
}
