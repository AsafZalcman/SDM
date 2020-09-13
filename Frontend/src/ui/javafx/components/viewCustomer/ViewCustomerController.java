package ui.javafx.components.viewCustomer;

import java.net.URL;
import java.util.ResourceBundle;

import dtoModel.CustomerDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.managers.CustomersUIManager;
import ui.javafx.utils.FormatUtils;

public class ViewCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<CustomerDto> customersTableViewInViewCustomersTab;

    @FXML
    private TableColumn<CustomerDto, Integer> customerIdTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerNameTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> LocationTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, Integer> customerHowManyOrdersTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerAveragePriceExcludeDeliveryTableColumnInViewCustomers;

    @FXML
    private TableColumn<CustomerDto, String> customerAverageDeliveryPaymentTableColumnInViewCustomer;

    @FXML
    void initialize() {
        initViewCustomersTabComponents();
    }

    private CustomersUIManager m_CustomersUIManager;
    private SuperDuperController superDuperController;

    public ViewCustomerController(){
        m_CustomersUIManager = new CustomersUIManager();
    }

    public void setMainController(SuperDuperController superDuperController){
        this.superDuperController = superDuperController;
    }

    private void initViewCustomersTabComponents() {
        customerIdTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("name"));
        LocationTableColumnInViewCustomers.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getLocation().x +"," + cellData.getValue().getLocation().y));
        customerHowManyOrdersTableColumnInViewCustomers.setCellValueFactory(new PropertyValueFactory<>("ordersCount"));
        customerAveragePriceExcludeDeliveryTableColumnInViewCustomers.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAverageOrdersPriceExcludeDelivery())));
        customerAverageDeliveryPaymentTableColumnInViewCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAverageDeliveryPayment())));
    }

    public void fetchCustomers(){
        customersTableViewInViewCustomersTab.getItems().setAll((m_CustomersUIManager.getAllCustomers()));
    }
}
