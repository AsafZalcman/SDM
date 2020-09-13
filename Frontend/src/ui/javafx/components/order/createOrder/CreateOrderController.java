package ui.javafx.components.order.createOrder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.components.order.createOrder.steps.buyItems.BuyItemsController;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.SDMResourcesConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateOrderController {

    @FXML
    private RadioButton insertDetailsRadioButton;

    @FXML
    private ToggleGroup orderProgressToggleGroup;

    @FXML
    private RadioButton buyItemsRadioButton;

    @FXML
    private RadioButton orderSummaryRadioButton;

    @FXML
    private Button endOfStepButton;

    @FXML
    private RadioButton addDiscountsRadioButton;

    @FXML
    private FlowPane currentStepFlowPane;

    private OrdersUIManager m_OrdersUIManager;

    private StepController m_CurrentStepController;

    @FXML
    private void initialize() {
        loadCurrentStepController(SDMResourcesConstants.ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE);
        m_StepsButtons = new ArrayList<>(
                Arrays.asList(insertDetailsRadioButton, buyItemsRadioButton,addDiscountsRadioButton, orderSummaryRadioButton));
        m_OrdersUIManager = OrdersUIManager.getInstance();
        insertDetailsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE);
        buyItemsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_BUY_ITEMS_FXML_RESOURCE);
        addDiscountsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_ADD_DISCOUNTS_FXML_RESOURCE);
        orderProgressToggleGroup.selectedToggleProperty().addListener((observable, prevButton, currentButton) ->
                loadCurrentStepController((URL) currentButton.getUserData()));
    }

    @FXML
    void endOfStepButtonOnAction(ActionEvent event) {
        m_CurrentStepController.onStepFinish();
        if (!isLastStep()) {
            endOfStepButton.setText("Next");
            m_StepsButtons.get(m_CurrentButtonIndex).setDisable(true);
            m_CurrentButtonIndex++;
            m_StepsButtons.get(m_CurrentButtonIndex).setSelected(true);
            m_StepsButtons.get(m_CurrentButtonIndex).setDisable(false);

            if (isLastStep()) {
                endOfStepButton.setText("Finish");
            }
        }
    }

    private List<RadioButton> m_StepsButtons;
    private int m_CurrentButtonIndex;

    public CreateOrderController() {

        m_CurrentButtonIndex = 0;
    }

    private boolean isLastStep() {
        return m_CurrentButtonIndex == m_StepsButtons.size();
    }

    private void loadCurrentStepController(URL i_PathToControllerResource) {
        try {
            currentStepFlowPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(i_PathToControllerResource);
            Node currentStep = loader.load();
            m_CurrentStepController = loader.getController();
            BooleanProperty enableProperty = new SimpleBooleanProperty();
            m_CurrentStepController.addBind(enableProperty);
            endOfStepButton.disableProperty().bind(enableProperty.not());
            currentStepFlowPane.getChildren().add(currentStep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
