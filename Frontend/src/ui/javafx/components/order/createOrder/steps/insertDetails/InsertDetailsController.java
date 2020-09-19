package ui.javafx.components.order.createOrder.steps.insertDetails;

import dtoModel.CustomerDto;
import dtoModel.StoreDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.managers.CustomersUIManager;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.FormatUtils;

import java.time.LocalDate;

public class InsertDetailsController extends StepController {

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private ComboBox<CustomerDto> customersComboBox;

    @FXML
    private Label storesLabel;

    @FXML
    private ComboBox<StoreDto> storesComboBox;

    @FXML
    private RadioButton staticOrderRadioButton;

    @FXML
    private ToggleGroup orderTypeToggleGroup;

    @FXML
    private RadioButton dynamicOrderRadioButton;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private Label deliveryPriceValueLabel;

    private OrdersUIManager m_OrdersUIManager;
    private StoreUIManager m_StoreUIManager;
    private CustomersUIManager m_CustomersUIManager;


    @FXML
    void customersComboBoxOnAction(ActionEvent event) {
        customerChosen.set(true);
        displayDeliveryPriceIfNeeded(storesComboBox.getValue(),customersComboBox.getValue());
    }

    @FXML
    void dynamicOrderRadioButtonOnAction(ActionEvent event) {
        deliveryPriceValueLabel.setText(null);
        storesComboBox.getItems().clear();
        storeChosen.set(true);
    }
    @FXML
    void orderDatePickerOnAction(ActionEvent event) {
        dateChosen.set(true);
    }

    @FXML
    void staticOrderRadioButtonOnAction(ActionEvent event) {
        storesComboBox.getItems().setAll(m_StoreUIManager.getAllStores());
        storeChosen.set(false);
    }

    @FXML
    void storesComboBoxOnAction(ActionEvent event) {
        if (!customersComboBox.getItems().isEmpty()) {
            displayDeliveryPriceIfNeeded(storesComboBox.getValue(),customersComboBox.getValue());
            storeChosen.set(true);
        }
    }

    @FXML
    private void initialize() {
        storesLabel.visibleProperty().bind(staticOrderRadioButton.selectedProperty());
        storesComboBox.visibleProperty().bind(staticOrderRadioButton.selectedProperty());
        deliveryPriceLabel.visibleProperty().bind(staticOrderRadioButton.selectedProperty());
        deliveryPriceValueLabel.visibleProperty().bind(staticOrderRadioButton.selectedProperty());
        customersComboBox.getItems().addAll(m_CustomersUIManager.getAllCustomers());
        m_StepComplete.bind(Bindings.and(dateChosen,Bindings.and(storeChosen, customerChosen)));
        storesComboBox.setConverter(new StringConverter<StoreDto>() {
            @Override
            public String toString(StoreDto storeDto) {
                return "Id:" + storeDto.getId() + ", Name:" + storeDto.getName() + ", Location:(" + storeDto.getLocation().x + "," + storeDto.getLocation().y + ")";
            }

            @Override
            public StoreDto fromString(String storeDtoString) {
                return null;
            }
        });

        customersComboBox.setConverter(new StringConverter<CustomerDto>() {
            @Override
            public String toString(CustomerDto customerDto) {
                return "Id:" + customerDto.getId() + ", Name:" + customerDto.getName();

            }

            @Override
            public CustomerDto fromString(String string) {
                return null;
            }
        });



    //    storesComboBox.setCellFactory(param -> new ListCell<StoreDto>() {
    //        @Override
    //        protected void updateItem(StoreDto storeDto, boolean empty) {
    //            super.updateItem(storeDto, empty);
//
    //            if (empty || storeDto == null) {
    //                setText(null);
    //            } else {
    //                setText("Id:" + storeDto.getId() + ", Name:" + storeDto.getName() + ", Location:(" + storeDto.getLocation().x + "," + storeDto.getLocation().y + ")");
    //            }
    //        }
    //    });
    }

    public InsertDetailsController() {
        m_StoreUIManager = new StoreUIManager();
        m_CustomersUIManager = new CustomersUIManager();
        m_OrdersUIManager = OrdersUIManager.getInstance();
        storeChosen = new SimpleBooleanProperty(true);
        customerChosen = new SimpleBooleanProperty(false);
        dateChosen = new SimpleBooleanProperty(false);
    }

    private SimpleBooleanProperty customerChosen;
    private SimpleBooleanProperty storeChosen;
    private SimpleBooleanProperty dateChosen;

    @Override
    public String getStepName() {
        return "Insert Details";
    }

    @Override
    public void onStepFinish() {
        m_OrdersUIManager.setDate(orderDatePicker.getValue());
        m_OrdersUIManager.setCustomer(customersComboBox.getValue());
        m_OrdersUIManager.setStore(storesComboBox.getValue());
    }

    private void displayDeliveryPriceIfNeeded(StoreDto i_StoreDto,CustomerDto i_CustomerDto)
    {
        if(i_StoreDto!=null && i_CustomerDto!=null) {
            deliveryPriceValueLabel.setText(FormatUtils.DecimalFormat.format(i_StoreDto
                    .getDeliveryPrice(i_CustomerDto.getLocation())));
        }
    }
}
