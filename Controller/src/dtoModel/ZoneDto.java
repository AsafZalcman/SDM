package dtoModel;

import models.Zone;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class ZoneDto {

    private final Zone m_Zone;
    public ZoneDto(Zone i_Zone)
    {
        m_Zone=i_Zone;
    }

    public String getName()
    {
        return m_Zone.getName();
    }
    public String getOwnerName()
    {
        return "not implemented";
        //m_Zone.getOwnerName();
    }
    public Collection<ItemDto> getAllItems()
    {
        return m_Zone.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public Collection<StoreDto> getAllStores()
    {
        return m_Zone.getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public Collection<OrderDto> getAllOrders()
    {
        return Collections.emptyList();
        //return m_Zone.getAllOrders();
    }
}
