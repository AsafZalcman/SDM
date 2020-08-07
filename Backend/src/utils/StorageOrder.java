package utils;

import models.Order;

//maybeShouldBeDto
public class StorageOrder {
    private static Integer s_IdGenerator; // maybe it should be in orderManager
    private final int m_OrderID;
    private final Order m_Order;
    private final int m_StoreID;
    private final String m_StoreName;

    public StorageOrder(Order m_order, int m_storeID, String m_storeName) {
        m_Order = m_order;
        m_StoreID = m_storeID;
        m_StoreName = m_storeName;
        this.m_OrderID = ++s_IdGenerator;
    }

    public int getM_OrderID() {
        return m_OrderID;
    }

    public Order getM_Order() {
        return m_Order;
    }

    public int getM_StoreID() {
        return m_StoreID;
    }

    public String getM_StoreName() {
        return m_StoreName;
    }
}
