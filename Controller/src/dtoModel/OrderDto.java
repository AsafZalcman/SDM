package dtoModel;

import models.Order;
import myLocation.Location;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OrderDto {
    private final Order m_Order;
    private final Collection<ItemDto> m_ItemsDto;
    private final int m_TotalItemsCount;

    public OrderDto(Order i_Order) {
        m_Order = i_Order;
        m_ItemsDto = m_Order.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
        m_TotalItemsCount = m_Order.getAllItems().stream().map(orderItem -> orderItem.getStoreItem().getAmountOfSells() == Math.floor(orderItem.getStoreItem().getAmountOfSells()) ? (int) orderItem.getStoreItem().getAmountOfSells() : 1).reduce(0, Integer::sum);
    }

    public Collection<ItemDto> getItemsDto() {
        return m_ItemsDto;
    }

    public Location getDestLocation() {
        return m_Order.getCustomerLocation();
    }

    public double getDeliveryPrice() {
        return m_Order.getDeliveryPrice();
    }


    public int getTotalItemsCount() {
        return m_TotalItemsCount;
    }

    public LocalDate getDate() {
        return m_Order.getOrderDate();
    }

    public double getTotalItemsPrice() {
        return m_Order.getTotalItemsPrice();
    }

    public double getTotalOrderPrice() {
        return m_Order.getTotalOrderPrice();
    }

    public int getTotalItemsKind() {
        return m_Order.getTotalItemsKinds();
    }
}
