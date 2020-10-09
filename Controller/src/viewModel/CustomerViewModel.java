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

    //return the unique id of the created user
    // raise exception if the user is already exists
    public int addCustomer(String i_Name) throws Exception {
        return m_SuperDuperManager.addCustomer(i_Name);
    }
}