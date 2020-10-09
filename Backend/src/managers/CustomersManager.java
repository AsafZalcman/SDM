package managers;

import models.Customer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomersManager {

    private Map<Integer, Customer> m_CustomerIdToCustomer;

    public CustomersManager()
    {
        this(Collections.emptyList());
    }
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

    private void addCustomer(Customer i_Customer) {
        m_CustomerIdToCustomer.put(i_Customer.getId(), i_Customer);
    }

    public int addCustomer(String i_CustomerName) throws Exception {
        int id = i_CustomerName.toLowerCase().hashCode();
        if (m_CustomerIdToCustomer.get(id) != null) {
            throw new Exception("User:\"" + i_CustomerName.toLowerCase() + "\" is already exists");
        }
        addCustomer(new Customer(id, i_CustomerName));
        return id;
    }

    public Customer getCustomer(int i_CustomerId) {
        return m_CustomerIdToCustomer.get(i_CustomerId);
    }

    public boolean isCustomerExist(int i_CustomerID) {
        return m_CustomerIdToCustomer.containsKey(i_CustomerID);
    }
}