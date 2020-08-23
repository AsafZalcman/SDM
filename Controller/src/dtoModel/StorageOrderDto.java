package dtoModel;

import models.StorageOrder;

import java.util.ArrayList;
import java.util.Map;

public class StorageOrderDto {
    private final StorageOrder m_StorageOrder;
    private final OrderDto m_OrderDto;
    private final Map<StoreDto, OrderDto> m_StoreToOrderMap;
    private final StoreDto m_StoreDto;

    public StorageOrderDto(StorageOrder i_StorageOrder, Map<StoreDto, OrderDto> i_StoreToOrderMap) {
        m_StorageOrder = i_StorageOrder;
        m_OrderDto = new OrderDto(m_StorageOrder.getOrder());
        m_StoreToOrderMap = i_StoreToOrderMap;
        m_StoreDto = isDynamicOrder() ? null : new ArrayList<>(m_StoreToOrderMap.keySet()).get(0);
    }

    public int getOrderID() {
        return m_StorageOrder.getOrderID();
    }

    public int getNumberOfStores() {
        return m_StorageOrder.getStoresIdToOrder().size();
    }

    public OrderDto getOrderDto() {
        return m_OrderDto;
    }

    //bad name
    public Map<StoreDto, OrderDto> getStoresToOrders() {
        return m_StoreToOrderMap;
    }

    public boolean isDynamicOrder()
    {
        return getNumberOfStores() > 1;
    }

    public String getStoreName() {
       return m_StoreDto==null? null: m_StoreDto.getName();
    }

    public Integer getStoreId() {
        return m_StoreDto==null? null: m_StoreDto.getId();
    }

    public Double getDistanceFromSource() {

        return m_StoreDto==null? null: m_OrderDto.getDestLocation().distance(m_StoreDto.getLocation());
    }

    public Double getPPK() {
        return m_StoreDto==null? null: m_StoreDto.getPPK();
    }
}
