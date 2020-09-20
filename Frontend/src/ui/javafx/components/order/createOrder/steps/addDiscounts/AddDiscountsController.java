package ui.javafx.components.order.createOrder.steps.addDiscounts;

import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.components.storeDiscount.DiscountController;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;
import java.util.Collection;

public class AddDiscountsController extends StepController {

    @FXML
    private ComboBox<StoreDto> storesComboBox;

    @FXML
    private FlowPane discountsFlowPane;

    @FXML
    private Label availableDiscountsLabel;

    @FXML
    private Label storesLabel;

    @FXML
    private ScrollPane discountsScrollPane;

    @FXML
    private Label buyDiscountLabel;

    @FXML
    private Label storeDiscountLabel;

    @FXML
    private ComboBox<StoreDiscountDto> discountComboBox;

    private OrdersUIManager m_OrdersUIManager;

    private void fetchAllAvailableDiscountsOfSelectedStore() {
        StoreDiscountDto currentStoreDiscountDto = discountComboBox.getValue();
        discountComboBox.getItems().setAll(m_OrdersUIManager.getAllAvailableDiscountsOfStore(storesComboBox.getValue()));
        if (discountComboBox.getItems().isEmpty()) {
            discountsFlowPane.getChildren().clear();
            fetchAllStoresWithAvailableDiscounts();
        }
        else
        {
            if(!discountComboBox.getItems().contains(currentStoreDiscountDto)) {
                discountsFlowPane.getChildren().clear();
            }
            storeDiscountLabel.setVisible(true);
            discountComboBox.setVisible(true);
            storeDiscountLabel.setText(storesComboBox.getValue().getName() + "'s Discounts:");
            discountComboBox.getSelectionModel().selectFirst();

        }
    }

    private void fetchAllStoresWithAvailableDiscounts() {
        storeDiscountLabel.setVisible(false);
        discountComboBox.setVisible(false);
        Collection<StoreDto> stores = m_OrdersUIManager.getAllStoresWithAvailableDiscount();
        if (stores.isEmpty()) {
            setNoDiscountsAvailable();
        } else {
            storesComboBox.getItems().setAll(stores);
            storesComboBox.getSelectionModel().selectFirst();
         //not working in the first time (dont know why????)
        }

    }

    @FXML
    private void initialize() {
        m_OrdersUIManager = OrdersUIManager.getInstance();
        m_StepComplete.set(true);
        storesComboBox.setConverter(new StringConverter<StoreDto>() {
            @Override
            public String toString(StoreDto storeDto) {
                return storeDto.getName();
            }

            @Override
            public StoreDto fromString(String storeDtoString) {
                return null;
            }
        });

        storesComboBox.valueProperty().addListener((observable,oldValue,newValue) ->
        {
            if (!storesComboBox.getItems().isEmpty()) {
                fetchAllAvailableDiscountsOfSelectedStore();
            }
        });

        discountComboBox.setConverter(new StringConverter<StoreDiscountDto>() {
            @Override
            public String toString(StoreDiscountDto storeDiscountDto) {
                return storeDiscountDto.getName();
            }

            @Override
            public StoreDiscountDto fromString(String storeDtoString) {
                return null;
            }
        });

        discountComboBox.valueProperty().addListener((observable,oldValue,newValue) ->
        {
            if (!discountComboBox.getItems().isEmpty()) {
                createDiscountController(discountComboBox.getSelectionModel().getSelectedItem());
            }
        });
        fetchAllStoresWithAvailableDiscounts();

    }

    @Override
    public String getStepName() {
        return "Add Discounts";
    }

    @Override
    public void onStepFinish() {
        m_OrdersUIManager.createOrder();
    }

    private void createDiscountController(StoreDiscountDto i_StoreDiscountDto) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SDMResourcesConstants.STORE_DISCOUNT_FXML_RESOURCE);
            Node singleDiscount = loader.load();
            DiscountController discountController = loader.getController();
            discountController.setStoreDiscountDto(i_StoreDiscountDto);
            discountController.setWithButtons(true);
            discountController.addListenerAfterBuying(() -> {
                buyDiscountLabel.setText("The Discount named \"" + i_StoreDiscountDto.getName() + "\" was added to the current order");
                fetchAllAvailableDiscountsOfSelectedStore();
            });
            discountsFlowPane.getChildren().setAll(singleDiscount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNoDiscountsAvailable() {
        availableDiscountsLabel.setText("No Available Discounts");
        storesComboBox.setVisible(false);
        discountsFlowPane.setVisible(false);
        storesLabel.setVisible(false);
        discountsScrollPane.setVisible(false);
        buyDiscountLabel.setVisible(false);
        storeDiscountLabel.setVisible(false);
        discountComboBox.setVisible(false);
    }
}