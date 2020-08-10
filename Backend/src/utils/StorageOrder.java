package utils;

import models.Order;

public class StorageOrder {
    private final int m_OrderID;
    private final Order m_Order;
    private final int m_StoreID;
    private final String m_StoreName;

    public StorageOrder(int i_Id, Order i_Order, int i_StoreID, String i_StoreName) {
        m_Order = i_Order;
        m_StoreID = i_StoreID;
        m_StoreName = i_StoreName;
        this.m_OrderID = i_Id;
    }

    public int getOrderID() {
        return m_OrderID;
    }

    public Order getOrder() {
        return m_Order;
    }

    public int getStoreID() {
        return m_StoreID;
    }

    public String getStoreName() {
        return m_StoreName;
    }
}
