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
    private StoreDto m_StoreDto;
    private final int m_TotalItemsCount;
    public OrderDto(Order i_Order) {
        m_Order = i_Order;
        m_ItemsDto=m_Order.getStoreItems().stream().map(ItemDto::new).collect(Collectors.toList());
        m_TotalItemsCount = m_Order.getStoreItems().stream().map(item -> item.getAmountOfSells() == Math.floor(item.getAmountOfSells()) ? (int) item.getAmountOfSells() : 1).reduce(0, Integer::sum);
    }
    public OrderDto(Order i_Order,StoreDto i_StoreDto) {
        this(i_Order);
        m_StoreDto=i_StoreDto;
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

    public boolean isDynamicOrder()
    {
        return m_StoreDto == null;
    }

    public String getStoreName()
    {
        return isDynamicOrder()?null:m_StoreDto.getName();
    }
    public Integer getStoreId()
    {
       return isDynamicOrder()? null:m_StoreDto.getId();
    }

    public Double getDistanceFromSource() {
        if(!isDynamicOrder())
        {
            return m_Order.getCustomerLocation().distance(m_StoreDto.getLocation());
        }
        return null;
    }

    public Double getPPK() {
       return  isDynamicOrder()?null :m_StoreDto.getPPK();
    }

    public int getTotalItemsCount() {
        return  m_TotalItemsCount;
    }

    public Date getDate() {
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
