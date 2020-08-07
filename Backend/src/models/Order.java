package models;

import myLocation.Location;

import java.util.Date;
import java.util.Map;

public class Order {
    private final Date m_OrderDate;
    private final Location m_CustomerLocation;
    private double m_DeliveryPrice;
    private double m_TotalItemsPrice;
    private double m_TotalOrderPrice;
    private Map<Integer, StoreItem> m_IdToStoreItem;

    public Order(Date i_OrderDate, Location i_CustomerLocation) {
        m_OrderDate = i_OrderDate;
        m_CustomerLocation = i_CustomerLocation;
    }
}
