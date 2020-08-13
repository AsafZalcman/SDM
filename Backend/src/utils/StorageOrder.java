package utils;

import models.Order;
import models.Store;

import java.util.Collection;

public class StorageOrder {
    private final int m_OrderID;
    private final Order m_Order;
    private final Collection<Store> m_OrderStores;

    public StorageOrder(int i_Id, Order i_Order, Collection<Store> i_OrderStores) {
        m_Order = i_Order;
        m_OrderStores = i_OrderStores;
        this.m_OrderID = i_Id;
    }

    public int getOrderID() {
        return m_OrderID;
    }

    public Order getOrder() {
        return m_Order;
    }

    public final Collection<Store> getOrderStores()
    {
        return m_OrderStores;
    }

}
