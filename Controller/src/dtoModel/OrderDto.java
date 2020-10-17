package dtoModel;

import models.Order;
import myLocation.Location;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OrderDto {
    private final Collection<ItemDto> m_ItemsDto;
    private final int m_TotalItemsCount;
    private final Location m_DestLocation;
    private final double m_DeliveryPrice;
    private final String m_Date;
    private final double m_TotalItemsPrice;
    private final double m_TotalOrderPrice;
    private final int m_TotalItemsKind;
    public OrderDto(Order i_Order) {
        m_ItemsDto = i_Order.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
        m_TotalItemsCount = i_Order.getAllItems().stream().map(orderItem -> orderItem.getStoreItem().getAmountOfSells() == Math.floor(orderItem.getStoreItem().getAmountOfSells()) ? (int) orderItem.getStoreItem().getAmountOfSells() : 1).reduce(0, Integer::sum);
        m_DestLocation=i_Order.getCustomerLocation();
        m_DeliveryPrice=i_Order.getDeliveryPrice();
        m_Date =  i_Order.getOrderDate().toString();
        m_TotalItemsKind = i_Order.getTotalItemsKinds();
        m_TotalItemsPrice=i_Order.getTotalItemsPrice();
        m_TotalOrderPrice=i_Order.getTotalOrderPrice();
    }

    public Collection<ItemDto> getItemsDto() {
        return m_ItemsDto;
    }

    public Location getDestLocation() {
        return m_DestLocation;
    }

    public double getDeliveryPrice() {
        return m_DeliveryPrice;
    }


    public int getTotalItemsCount() {
        return m_TotalItemsCount;
    }

    public String getDate() {
        return m_Date;
    }

    public double getTotalItemsPrice() {
        return m_TotalItemsPrice;
    }

    public double getTotalOrderPrice() {
        return m_TotalOrderPrice;
    }

    public int getTotalItemsKind() {
        return m_TotalItemsKind;
    }
}
