package viewModel;

import dtoModel.CustomerDto;
import dtoModel.ItemDto;
import dtoModel.StorageItemDto;
import managers.SuperDuperManager;
import models.Customer;
import models.Store;
import models.StoreItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerViewModel {

    private SuperDuperManager m_SuperDuperManager;

    public CustomerViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<CustomerDto> getAllCustomers()
    {
        return m_SuperDuperManager.getAllCustomers().stream().map(CustomerDto::new).collect(Collectors.toList());
    }

    public String getCustomerMapToolTip(int i_CustomerID) {
        Customer customer = m_SuperDuperManager.getCustomer(i_CustomerID);
        StringBuilder sb = new StringBuilder();
        sb.append("Location: ").append(customer.getLocation()).append("\n")
                .append("ID: ").append(customer.getId()).append("\n")
                .append("Name: ").append(customer.getName()).append("\n")
                .append("Amount of Orders: ").append(customer.getAmountOfOrders()).append("\n");

        return sb.toString();
    }
}