package ui.javafx.components.order.createOrder.steps.addDiscounts;

import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.components.storeDiscount.BuyDiscountController;
import ui.javafx.components.storeDiscount.DiscountController;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;
import java.util.Collection;

public class AddDiscountsController extends StepController {

    // need to add the option to buy a discount
    @FXML
    private ComboBox<StoreDto> storesComboBox;

    @FXML
    private FlowPane discountsFlowPane;

    @FXML
    private Label availableDiscountsLabel;

    private OrdersUIManager m_OrdersUIManager;

    @FXML
    void storesComboBoxOnAction(ActionEvent event) {
       m_OrdersUIManager.getAllAvailableDiscountsOfStore(storesComboBox.getValue()).forEach(this::createDiscountController);
    }

    @FXML
    private void initialize() {
        m_OrdersUIManager = OrdersUIManager.getInstance();
        m_StepComplete.set(true);
        Collection<StoreDto> stores = m_OrdersUIManager.getAllStoresWithAvailableDiscount();
        storesComboBox.setConverter(new StringConverter<StoreDto>() {
            @Override
            public String toString(StoreDto storeDto) {
                return "Name:" + storeDto.getName();
            }

            @Override
            public StoreDto fromString(String storeDtoString) {
                return null;
            }
        });
        if (stores.isEmpty()) {
            availableDiscountsLabel.setText("No Available Discounts");
            storesComboBox.setVisible(false);
        } else {
            storesComboBox.getItems().addAll(stores);
        }
    }

    @Override
    public String getStepName() {
        return "Add Discounts";
    }

    @Override
    public void onStepFinish() {
        m_OrdersUIManager.createOrder();
    }

    private void createDiscountController(StoreDiscountDto i_StoreDiscountDto)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.STORE_DISCOUNT_FXML_RESOURCE);
            Node singleDiscount = loader.load();
            //BuyDiscountController discountController = new BuyDiscountController();
           // loader.setController(discountController);
            DiscountController discountController = loader.getController();
            discountController.setStoreDiscountDto(i_StoreDiscountDto);
            discountController.setWithButtons(true);
   //         discountController.setWithButtons(true);
            discountsFlowPane.getChildren().add(singleDiscount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
