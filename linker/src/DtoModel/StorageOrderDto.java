package DtoModel;

import utils.StorageOrder;

import java.util.Collection;
import java.util.Date;

public class StorageOrderDto {
    private final int m_OrderID;
    private final Date m_OrderDate;
    private final double m_DeliveryPrice;
    private final double m_TotalItemsPrice;
    private final double m_TotalOrderPrice;
    private final int m_TotalItemsCount;
    private final Collection<StoreDto> m_StoreDtos;
    private final StorageOrder m_StorageOrder;
    private final int m_TotalItemsKind;

    public StorageOrderDto(StorageOrder i_StorageOrder) {
        m_StorageOrder = i_StorageOrder;
        m_OrderDate = m_StorageOrder.getOrder().getOrderDate();
        m_DeliveryPrice = m_StorageOrder.getOrder().getDeliveryPrice();
        m_StoreDtos = m_StorageOrder.getStores();
        this.m_OrderID = m_StorageOrder.getOrderID();
        m_TotalItemsCount = m_StorageOrder.getOrder().getStoreItems().stream().map(item -> item.getAmountOfSells() == Math.floor(item.getAmountOfSells()) ? (int) item.getAmountOfSells() : 1).reduce(0, Integer::sum);
        m_TotalItemsKind = m_StorageOrder.getOrder().getStoreItems().size();
        m_TotalItemsPrice = m_StorageOrder.getOrder().getTotalItemsPrice();
        m_TotalOrderPrice = m_StorageOrder.getOrder().getTotalOrderPrice();

    }

    public int getOrderID() {
        return m_OrderID;
    }

    public int getStoreID() {
        return m_StoreID;
    }

    public String getStoreName() {
        return m_StoreName;
    }

    public int getTotalItemsCount() {
        return m_TotalItemsCount;
    }

    public Date getDate() {
        return m_OrderDate;
    }

    public double getDeliveryPrice() {
        return m_DeliveryPrice;
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
