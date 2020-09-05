package ui.javafx.managers;

import dtoModel.CustomerDto;
import viewModel.CustomerViewModel;

import java.util.Collection;

public class CustomersUIManager {

    private CustomerViewModel m_CustomerViewModel = new CustomerViewModel();

    public Collection<CustomerDto> getAllCustomers()
    {
        return m_CustomerViewModel.getAllCustomers();
    }
}
