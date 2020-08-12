package DtoModel;

import models.Store;
import models.StoreItem;
import utils.StorageOrder;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class StorageOrderDto {
  private final StorageOrder m_StorageOrder;
  private final int m_TotalItemsCount;
  private final double m_PPK;
  private final double m_Distance;
  private final OrderDto m_OrderDto;

  public StorageOrderDto(StorageOrder i_StorageOrder) {
      m_StorageOrder = i_StorageOrder;
 //     m_OrderDate = m_StorageOrder.getOrder().getOrderDate();
 //     m_DeliveryPrice = m_StorageOrder.getOrder().getDeliveryPrice();
 //     m_OrderID = m_StorageOrder.getOrderID();
      m_TotalItemsCount = m_StorageOrder.getOrder().getStoreItems().stream().map(item -> item.getAmountOfSells() == Math.floor(item.getAmountOfSells()) ? (int) item.getAmountOfSells() : 1).reduce(0, Integer::sum);
//     m_TotalItemsKind = m_StorageOrder.getOrder().getStoreItems().size();
//     m_TotalItemsPrice = m_StorageOrder.getOrder().getTotalItemsPrice();
//     m_TotalOrderPrice = m_StorageOrder.getOrder().getTotalOrderPrice();
      m_PPK = m_StorageOrder.getOrderStores().stream().mapToDouble(Store::getPPK).sum();
      m_Distance = m_StorageOrder.getOrderStores().stream().mapToDouble(store -> store.getLocation().distance(m_StorageOrder.getOrder().getCustomerLocation())).sum();
      m_OrderDto= new OrderDto(m_StorageOrder.getOrder());
  }

  public int getOrderID() {
      return m_StorageOrder.getOrderID();
  }

  public Collection<Integer> getStoresID() {
      return  m_StorageOrder.getOrderStores().stream().map(Store::getId).collect(Collectors.toList());
  }

  public Collection<String> getStoreName() {
      return m_StorageOrder.getOrderStores().stream().map(Store::getStoreName).collect(Collectors.toList());
  }

  public int getTotalItemsCount() {
    return  m_TotalItemsCount;
  }

  public Date getDate() {
      return m_StorageOrder.getOrder().getOrderDate();
  }

  public double getDeliveryPrice() {
      return m_StorageOrder.getOrder().getDeliveryPrice();
  }

  public double getTotalItemsPrice() {
      return m_StorageOrder.getOrder().getTotalItemsPrice();
  }

  public double getTotalOrderPrice() {
      return m_StorageOrder.getOrder().getTotalOrderPrice();
  }

  public int getTotalItemsKind() {
      return m_StorageOrder.getOrder().getTotalItemsKinds();
  }
  public double getPPK()
  {
      return m_PPK;
  }
  public double getDistance() {
      return m_Distance;
  }
  public OrderDto getOrderDto()
  {
      return m_OrderDto;
  }
}
