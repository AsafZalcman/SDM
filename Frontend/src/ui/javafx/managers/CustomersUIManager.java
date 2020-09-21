package ui.javafx.managers;

import dtoModel.CustomerDto;
import javafx.scene.control.Tooltip;
import viewModel.CustomerViewModel;

import java.util.Collection;

public class CustomersUIManager {

    private CustomerViewModel m_CustomerViewModel = new CustomerViewModel();

    public Collection<CustomerDto> getAllCustomers()
    {
        return m_CustomerViewModel.getAllCustomers();
    }

    public Tooltip getCustomerMapToolTip(int i_CustomerID) {
        return new Tooltip(m_CustomerViewModel.getCustomerMapToolTip(i_CustomerID));
    }
}
