package utils;

import models.Order;
import models.Store;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class StorageOrder {
    private final int m_OrderID;
    private final Order m_FinalOrder;
    private final Map<Integer,Order> m_StoreIdToOrderMap;

    public StorageOrder(int i_Id, Order i_Order, Map<Integer,Order> i_StoreIdToOrderMap) {
        m_FinalOrder = i_Order;
        m_StoreIdToOrderMap = i_StoreIdToOrderMap;
        this.m_OrderID = i_Id;
    }

    public int getOrderID() {
        return m_OrderID;
    }

    public Order getOrder() {
        return m_FinalOrder;
    }

    public final Map<Integer,Order> getStoresIdToOrder()
    {
        return m_StoreIdToOrderMap;
    }



}
