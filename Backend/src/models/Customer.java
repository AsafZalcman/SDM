package models;

import interfaces.ILocationable;
import interfaces.IUniquely;
import myLocation.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer implements IUniquely {

    private final int m_Id;
    private final String m_Name;
    private List<StorageOrder> m_StorageOrders;

    public Customer(int i_Id, String i_Name) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
    }

    @Override
    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;

    }

    public List<StorageOrder> getOrders() {
        return m_StorageOrders != null ? m_StorageOrders : Collections.emptyList();
    }

    public void addOrder(StorageOrder i_StorageOrder) {
        if (m_StorageOrders == null) {
            m_StorageOrders = new ArrayList<>();
        }
        m_StorageOrders.add(i_StorageOrder);
    }

    public int getAmountOfOrders(){
        if(m_StorageOrders == null){
            return 0;
        }
        return m_StorageOrders.size();
    }
}