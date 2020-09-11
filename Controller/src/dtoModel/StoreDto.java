package dtoModel;

import models.Store;
import myLocation.Location;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreDto {
    private final Store m_Store;
    private final Collection<OrderDto> m_OrdersDto;
    private final Double m_DeliveriesIncome;
    private final Collection<StoreDiscountDto> m_Discounts;
    public StoreDto(Store i_Store) {
        m_Store = i_Store;
        m_OrdersDto = m_Store.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
        m_DeliveriesIncome = !m_OrdersDto.isEmpty() ? m_OrdersDto.stream().mapToDouble(OrderDto::getDeliveryPrice).sum() : null;
        if (i_Store.getDiscounts() != null) {
            m_Discounts = i_Store.getDiscounts().stream()
                    .map(StoreDiscountDto::new)
                    .collect(Collectors.toList());
        } else {
            m_Discounts = Collections.emptyList();
        }

    }

    public Point getLocation() {
        return m_Store.getLocation();
    }

    public Integer getId() {
        return m_Store.getId();
    }

    public String getName() {
        return m_Store.getStoreName();
    }

    public Double getPPK() {
        return m_Store.getPPK();
    }

    public Collection<ItemDto> getItemsDto()
    {
        return m_Store.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public Double getDeliveriesIncomes() {
        return m_DeliveriesIncome;
    }

    public Collection<OrderDto> getAllOrders() {
        return m_OrdersDto;
    }

    public Collection<StoreDiscountDto> getAllDiscounts()
    {
        return m_Discounts;
    }
    public double getDeliveryPrice(Point i_DestPoint)
    {
        return m_Store.getLocation().distance(i_DestPoint) * getPPK();
    }
}
