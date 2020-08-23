package dtoModel;

import models.Store;
import myLocation.Location;

import java.util.Collection;
import java.util.stream.Collectors;

public class StoreDto {
    private final Store m_Store;
    private final Collection<OrderDto> m_OrdersDto;
    private final Double m_DeliveriesIncome;
    public StoreDto(Store i_Store) {
        m_Store = i_Store;
        m_OrdersDto=m_Store.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
        m_DeliveriesIncome = !m_OrdersDto.isEmpty()?  m_OrdersDto.stream().mapToDouble(OrderDto::getDeliveryPrice).sum():null;
    }

    public Location getLocation() {
        return m_Store.getLocation();
    }

    public int getId() {
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
}
