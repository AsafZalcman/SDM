package utils;

import models.Order;

public class StorageOrder {
    private static Integer s_IdGenerator;
    private int m_OrderID;
    private Order m_Order;
    private int m_StoreID;
    private String m_StoreName;

    public StorageOrder() {
        this.m_OrderID = ++s_IdGenerator;
    }
}
