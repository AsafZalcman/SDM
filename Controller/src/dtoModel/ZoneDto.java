package dtoModel;

import models.Zone;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class ZoneDto {

    private final  Collection<StorageItemDto> m_StorageItemsDtos;
    private final Collection<StoreDto> m_StoresDtos;
    private final Collection<OrderDto> m_OrdersDtos;
    private final String m_Name;
    private final String m_OwnerName;

    public ZoneDto(Zone i_Zone)
    {
        m_StorageItemsDtos = i_Zone.getAllStorageItems().stream().map(StorageItemDto::new).collect(Collectors.toList());
        m_Name=i_Zone.getName();
        m_OrdersDtos = Collections.emptyList();
        m_OwnerName=i_Zone.getName();
        m_StoresDtos=i_Zone.getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public String getName()
    {
        return m_Name;
    }
    public String getOwnerName()
    {
        return m_OwnerName;
    }
    public Collection<StorageItemDto> getAllItems()
    {
        return m_StorageItemsDtos;
    }

    public Collection<StoreDto> getAllStores()
    {
        return m_StoresDtos;
    }

    public Collection<OrderDto> getAllOrders()
    {
        return m_OrdersDtos;
    }
}
