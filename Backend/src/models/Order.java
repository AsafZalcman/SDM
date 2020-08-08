package models;

import myLocation.Location;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Order {
    private final Date m_OrderDate;
    private final Location m_CustomerLocation;
    private final double m_DeliveryPrice;
    private double m_TotalItemsPrice;
    private double m_TotalOrderPrice;
    private final Map<Integer, StoreItem> m_IdToStoreItem;
//maybe should be the same as Store item - only contain id and extra details (if have)
    public Order(Date i_OrderDate, Location i_CustomerLocation, double i_DeliveryPrice, Map<Integer, StoreItem> i_IdToStoreItem) {
        m_OrderDate = i_OrderDate;
        m_CustomerLocation = i_CustomerLocation;
        m_DeliveryPrice = i_DeliveryPrice;
        m_IdToStoreItem = i_IdToStoreItem;
        m_TotalItemsPrice = m_IdToStoreItem.values().stream().map(item -> item.getAmountOfSells() * item.getPrice()).reduce(0.0, Double::sum);
        m_TotalOrderPrice = m_TotalItemsPrice + m_DeliveryPrice;
    }


    public Date getOrderDate() {
        return m_OrderDate;
    }

    public Location getCustomerLocation() {
        return m_CustomerLocation;
    }

    public double getDeliveryPrice() {
        return m_DeliveryPrice;
    }

    public double getTotalItemsPrice() {
        return m_TotalItemsPrice;
    }

    public double getTotalOrderPrice() {
        return m_TotalOrderPrice;
    }

    public int getTotalItemsKinds()
    {
        return  m_IdToStoreItem.size();
    }

    public Collection<StoreItem> getStoreItems()
    {
        return m_IdToStoreItem.values();
    }

}
