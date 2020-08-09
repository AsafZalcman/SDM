package utils;

import models.Order;

//maybeShouldBeDto
public class StorageOrder {
    private final int m_OrderID;
    private final Order m_Order;
    private final int m_StoreID;
    private final String m_StoreName;

    public StorageOrder(int i_Id,Order i_Order, int i_StoreID, String i_StoreName) {
        m_Order = i_Order;
        m_StoreID = i_StoreID;
        m_StoreName = i_StoreName;
        this.m_OrderID = i_Id;
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
