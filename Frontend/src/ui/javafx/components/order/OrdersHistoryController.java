package ui.javafx.components.order;

import dtoModel.StorageOrderDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.components.order.createOrder.steps.orderSummary.OrderSummaryController;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;
import java.util.Collection;

public class OrdersHistoryController {

    @FXML
    private ComboBox<StorageOrderDto> ordersComboBox;

    @FXML
    private FlowPane orderSummaryFlowPane;

    @FXML
    private Label noOrdersToShowLabel;

    @FXML
    private Label ordersLabel;


    private SuperDuperController superDuperController;

    @FXML
    void ordersComboBoxOnSelected(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.ORDER_SUMMARY_FXML_RESOURCE);
            Node singleOrderSummary = loader.load();

            OrderSummaryController orderSummaryController = loader.getController();
            orderSummaryController.setStorageOrder(ordersComboBox.getValue());
            orderSummaryFlowPane.getChildren().setAll(singleOrderSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        ordersComboBox.setConverter(new StringConverter<StorageOrderDto>() {
            @Override
            public String toString(StorageOrderDto storageOrderDto) {
                return "Id:" + storageOrderDto.getOrderID();
            }

            @Override
            public StorageOrderDto fromString(String storeDtoString) {
                return null;
            }
        });

    }

  public void fetchOrders()
  {
      displayOrdersIfExists();
  }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }

    private void displayOrdersIfExists() {
        Collection<StorageOrderDto> storageOrderDtos = OrdersUIManager.getInstance().getAllOrders();
        if (storageOrderDtos.isEmpty()) {
            ordersComboBox.setVisible(false);
            ordersLabel.setVisible(false);
            noOrdersToShowLabel.setVisible(true);
        } else {
            ordersComboBox.setVisible(true);
            ordersLabel.setVisible(true);
            noOrdersToShowLabel.setVisible(false);
            ordersComboBox.getItems().setAll(storageOrderDtos);
        }
    }
}
