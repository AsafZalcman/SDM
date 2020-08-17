package DtoModel;

import utils.StorageOrder;

public class StorageOrderDto {
  private final StorageOrder m_StorageOrder;
  private final OrderDto m_OrderDto;

  public StorageOrderDto(StorageOrder i_StorageOrder) {
      m_StorageOrder = i_StorageOrder;
      m_OrderDto= new OrderDto(m_StorageOrder.getOrder());
  }

  public int getOrderID() {
      return m_StorageOrder.getOrderID();
  }

  public int getNumberOfStores()
  {
      return m_StorageOrder.getOrderStores().size();
  }

  public OrderDto getOrderDto() {
      return m_OrderDto;
  }
}
