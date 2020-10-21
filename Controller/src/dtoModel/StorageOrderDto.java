package dtoModel;

import models.Order;
import models.StorageOrder;
import models.Store;
import myLocation.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageOrderDto {
    private  final int m_OrderId;
    private final OrderDto m_OrderDto;
    private final Map<StoreDto, OrderDto> m_StoreToOrderMap;
    private final StoreDto m_StoreDto;
    private final int m_UserId;

    public StorageOrderDto(StorageOrder i_StorageOrder, Map<StoreDto, OrderDto> i_StoreToOrderMap) {
        m_OrderDto = new OrderDto(i_StorageOrder.getOrder());
        m_StoreToOrderMap = i_StoreToOrderMap;
        m_StoreDto = isDynamicOrder() ? null : new ArrayList<>(m_StoreToOrderMap.keySet()).get(0);
        m_OrderId=i_StorageOrder.getOrderID();
        m_UserId=i_StorageOrder.getCustomerId();
    }

    public StorageOrderDto(Order i_Order, Map<StoreDto, OrderDto> i_StoreToOrderMap,int i_UserId , int i_OrderId) {
        m_OrderDto = new OrderDto(i_Order);
        m_StoreToOrderMap = i_StoreToOrderMap;
        m_StoreDto = isDynamicOrder() ? null : new ArrayList<>(m_StoreToOrderMap.keySet()).get(0);
        m_UserId=i_UserId;
        m_OrderId=i_OrderId;
    }

    public Integer getOrderID() {
        return m_OrderId;
    }

    public int getNumberOfStores() {
        return m_StoreToOrderMap.keySet().size();
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
