package managers;

import models.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomersManager {

    private Map<Integer, Customer> m_CustomerIdToCustomer;

    public CustomersManager(Collection<Customer> i_Customers) {
        m_CustomerIdToCustomer = new HashMap<>();
        for (Customer customer : i_Customers
        ) {
            this.addCustomer(customer);
        }
    }

    public Collection<Integer> getAllCustomersId() {
        return m_CustomerIdToCustomer.keySet();
    }

    public Collection<Customer> getAllCustomers() {
        return m_CustomerIdToCustomer.values();
    }

    public void addCustomer(Customer i_Customer) {
        m_CustomerIdToCustomer.put(i_Customer.getId(), i_Customer);
    }

    public Customer getCustomer(int i_CustomerId) {
        return m_CustomerIdToCustomer.get(i_CustomerId);
    }

    public boolean isCustomerExist(int i_CustomerID) {
        return m_CustomerIdToCustomer.containsKey(i_CustomerID);
    }
}