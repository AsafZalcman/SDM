package DtoModel;

import models.Order;
import myLocation.Location;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OrderDto
{
    private final Order m_Order;
    private final Collection<ItemDto> m_ItemsDto;
    public OrderDto(Order i_Order) {
        m_Order = i_Order;
        m_ItemsDto=m_Order.getStoreItems().stream().map(ItemDto::new).collect(Collectors.toList());
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
}
