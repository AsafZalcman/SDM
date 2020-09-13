package models;

import myLocation.Location;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class Order {
    private final LocalDate m_OrderDate;
    private final Location m_CustomerLocation;
    private final double m_DeliveryPrice;
    private double m_TotalItemsPrice;
    private double m_TotalOrderPrice;
    private final Collection< OrderItem> m_OrderItems;

    //maybe should be the same as Store item - only contain id and extra details (if have)
    public Order(LocalDate i_OrderDate, Location i_CustomerLocation, double i_DeliveryPrice, Collection<OrderItem> i_OrderItems) {
        m_OrderDate = i_OrderDate;
        m_CustomerLocation = i_CustomerLocation;
        m_DeliveryPrice = i_DeliveryPrice;
        m_OrderItems = i_OrderItems;
        m_TotalItemsPrice = m_OrderItems.stream().mapToDouble(orderItem -> orderItem.getStoreItem().getAmountOfSells() * orderItem.getStoreItem().getPrice()).sum() ;
        m_TotalOrderPrice = m_TotalItemsPrice + m_DeliveryPrice;
    }

    public LocalDate getOrderDate() {
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
        return  m_OrderItems.size();
    }

    public Collection<OrderItem> getAllItems()
    {
        return m_OrderItems;
    }
    public Collection<OrderItem> getAllDiscountItems()
    {
        return m_OrderItems.stream().filter(OrderItem::isFromDiscount).collect(Collectors.toList());
    }
    public Collection<OrderItem> getAllNonDiscountItems()
    {
        return m_OrderItems.stream().filter(orderItem -> !orderItem.isFromDiscount()).collect(Collectors.toList());
    }


}
