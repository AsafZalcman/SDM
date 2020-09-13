package models;
import java.util.Map;

public class StorageOrder {
    private final int m_OrderID;
    private final Order m_FinalOrder;
    private final Map<Integer,Order> m_StoreIdToOrderMap;
    private final int m_CustomerId;


    public StorageOrder(int i_Id, Order i_Order, Map<Integer,Order> i_StoreIdToOrderMap , int i_CustomerId) {
        m_FinalOrder = i_Order;
        m_StoreIdToOrderMap = i_StoreIdToOrderMap;
        this.m_OrderID = i_Id;
        m_CustomerId=i_CustomerId;
    }

    public int getOrderID() {
        return m_OrderID;
    }

    public Order getOrder() {
        return m_FinalOrder;
    }
    public int getCustomerId()
    {
       return m_CustomerId;
    }

    public final Map<Integer,Order> getStoresIdToOrder()
    {
        return m_StoreIdToOrderMap;
    }



}
