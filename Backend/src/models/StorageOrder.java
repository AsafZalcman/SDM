package models;
import java.util.Map;

public class StorageOrder {
    private final Order m_FinalOrder;
    private final Map<Integer,Order> m_StoreIdToOrderMap;


    public StorageOrder(Order i_Order, Map<Integer,Order> i_StoreIdToOrderMap) {
        m_FinalOrder = i_Order;
        m_StoreIdToOrderMap = i_StoreIdToOrderMap;
    }


    public Order getOrder() {
        return m_FinalOrder;
    }

    public final Map<Integer,Order> getStoresIdToOrder()
    {
        return m_StoreIdToOrderMap;
    }



}
