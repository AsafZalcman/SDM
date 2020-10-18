package managers;

import models.*;
import myLocation.Location;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManager {
    private int counter = 0;
    private Map<Integer,StorageOrder> m_StorageOrders = new HashMap<>();
    private Map<Integer,StorageOrder> m_OrdersToExecute = new HashMap<>();


  synchronized  public void executeOrder(int i_StorageOrderId)
    {
        StorageOrder storageOrder = m_OrdersToExecute.get(i_StorageOrderId);
        m_StorageOrders.put(i_StorageOrderId,storageOrder);
        m_OrdersToExecute.remove(i_StorageOrderId);
    }

    public final Collection<StorageOrder> getStorageOrders()
    {
        return m_StorageOrders.values();
    }

    public StorageOrder getStorageOrder(int i_StorageOrderId)
    {
        StorageOrder storageOrder = m_StorageOrders.get(i_StorageOrderId);
        if (storageOrder == null)
        {
            storageOrder = m_OrdersToExecute.get(i_StorageOrderId);
        }
        return storageOrder;
    }

    public StorageOrder createOrder(LocalDate i_Date, Location i_Location, Map<Store, Map<Integer, Double>> i_storesToItemsMap, Map<Store, List<OrderItem>> i_storesToItemsInDiscounts,int i_UserId) {
        if (i_storesToItemsMap == null) {
            throw new IllegalStateException("Cannot create an empty order");
        }
        Map<Store, Order> storeToOrderMap = new HashMap<>();

        List<OrderItem> allOrderItems = new ArrayList<>();
        Order tempOrder;
        int totalDeliveryPrice = 0;
        for (Map.Entry<Store, Map<Integer, Double>> entry : i_storesToItemsMap.entrySet()) {
            tempOrder = entry.getKey().createOrder(i_Date, i_Location, entry.getValue(), i_storesToItemsInDiscounts.getOrDefault(entry.getKey(), Collections.emptyList()));
            storeToOrderMap.put(entry.getKey(), tempOrder);
            totalDeliveryPrice += tempOrder.getDeliveryPrice();
            allOrderItems.addAll(tempOrder.getAllItems());
        }

        synchronized (this) {
            StorageOrder storageOrder = new StorageOrder(++counter, new Order(i_Date, i_Location, totalDeliveryPrice, allOrderItems), storeToOrderMap.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getId(),
                            Map.Entry::getValue)), i_UserId);
            m_OrdersToExecute.put(storageOrder.getOrderID(), storageOrder);
            return storageOrder;
        }
    }

    public void abortOrder(int i_storageOrderIdToAbort) {
        m_OrdersToExecute.remove(i_storageOrderIdToAbort);
    }
}
