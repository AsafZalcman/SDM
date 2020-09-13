package ui.javafx.components.order;

import dtoModel.StorageOrderDto;
import dtoModel.StoreDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.components.order.storeSummary.OrderStoreSummaryController;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;

public class OrdersHistoryController {

    @FXML
    private ComboBox<StorageOrderDto> ordersComboBox;

    @FXML
    private FlowPane storeOrderSummaryFlowPane;

    @FXML
    private ComboBox<StoreDto> storesComboBox;

    private SuperDuperController superDuperController;

    @FXML
    void ordersComboBoxOnSelected(ActionEvent event) {
        storesComboBox.getItems().clear();
        storesComboBox.getItems().addAll(ordersComboBox.getValue().getStoresToOrders().keySet());
    }

    @FXML
    void storesComboBoxOnSelected(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.ORDER_STORE_SUMMARY_FXML_RESOURCE);
            Node singleStoreOrderSummary = loader.load();

            OrderStoreSummaryController orderStoreSummaryController = loader.getController();
            StoreDto storeDto = storesComboBox.getValue();
            orderStoreSummaryController.setOrderOfStore(new Pair<>(storeDto, ordersComboBox.getValue().getStoresToOrders().get(storeDto)));
            storeOrderSummaryFlowPane.getChildren().add(singleStoreOrderSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }
}
