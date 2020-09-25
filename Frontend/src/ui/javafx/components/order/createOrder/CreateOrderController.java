package ui.javafx.components.order.createOrder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.components.order.createOrder.steps.StepController;
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
    private Label endOfOrderLabel;

    @FXML
    private void initialize() {
        m_StepsButtons = new ArrayList<>(
                Arrays.asList(insertDetailsRadioButton, buyItemsRadioButton, addDiscountsRadioButton, orderSummaryRadioButton));
        m_OrdersUIManager = OrdersUIManager.getInstance();
        insertDetailsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE);
        buyItemsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_BUY_ITEMS_FXML_RESOURCE);
        addDiscountsRadioButton.setUserData(SDMResourcesConstants.ORDER_STEPS_ADD_DISCOUNTS_FXML_RESOURCE);
        orderSummaryRadioButton.setUserData(SDMResourcesConstants.ORDER_SUMMARY_FXML_RESOURCE);
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
            selectCurrentButton();

            if (isLastStep()) {
                endOfStepButton.setText("Finish");
            }
        } else {
            currentStepFlowPane.getChildren().clear();
            endOfOrderLabel.setText("Your order was executed");
            endOfStepButton.disableProperty().unbind();
            endOfStepButton.setDisable(true);
        }
    }

    private List<RadioButton> m_StepsButtons;
    private int m_CurrentButtonIndex;
    private  SuperDuperController superDuperController;

    private boolean isLastStep() {
        return m_CurrentButtonIndex == m_StepsButtons.size() -1;
    }

    private void loadCurrentStepController(URL i_PathToControllerResource) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(i_PathToControllerResource);
            Node currentStep = loader.load();
            m_CurrentStepController = loader.getController();
            BooleanProperty enableProperty = new SimpleBooleanProperty();
            m_CurrentStepController.addBind(enableProperty);
            endOfStepButton.disableProperty().bind(enableProperty.not());
            currentStepFlowPane.getChildren().setAll(currentStep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }

    public void fetchMakeOrderComponent(){
        endOfStepButton.setText("Next");
        endOfOrderLabel.setText(null);
        m_CurrentButtonIndex = 0;
        selectCurrentButton();
    }

    private void selectCurrentButton()
    {
        m_StepsButtons.get(m_CurrentButtonIndex).setSelected(true);
        m_StepsButtons.get(m_CurrentButtonIndex).setDisable(false);
    }
}
