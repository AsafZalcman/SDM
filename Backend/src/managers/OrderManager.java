package managers;

import models.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManager {
    private int counter = 0;
    private Map<Store,Order> m_StoreToOrderMap = new HashMap<>();
    private StorageOrder m_CurrentOrder;
    private List<StorageOrder> m_StorageOrders = new ArrayList<>();
    private boolean b_IsCreated = false;
    private Map<Store,Map<Integer,Double>> m_StoresToItemsMap;
    private Map<Store,List<OrderItem>> m_StoresToItemsInDiscounts = new HashMap<>();

    public void addItemFromCurrentStore(Store i_Store ,Item i_Item, double i_AmountOfSells)
    {
        if(m_StoresToItemsMap == null)
        {
            m_StoresToItemsMap = new HashMap<>();
        }

        Map<Integer,Double> currentIdToAmountMap  = m_StoresToItemsMap.get(i_Store);

        if(currentIdToAmountMap == null)
        {
            currentIdToAmountMap=new HashMap<>();
        }

        double currentAmountOfSells =currentIdToAmountMap.getOrDefault(i_Item.getId(),0.0);

        currentIdToAmountMap.put(i_Item.getId(),i_AmountOfSells+currentAmountOfSells);
        m_StoresToItemsMap.put(i_Store,currentIdToAmountMap);
    }

    public void addItemFromCurrentStore(Store i_Store ,OrderItem i_OrderItem)
    {
        List<OrderItem> orderItemsInDiscountOfStore = m_StoresToItemsInDiscounts.getOrDefault(i_Store,Collections.emptyList());
        orderItemsInDiscountOfStore.add(i_OrderItem);
      m_StoresToItemsInDiscounts.put(i_Store,orderItemsInDiscountOfStore);
    }


    public void create(Customer i_Customer, LocalDate i_Date) {
        if (m_StoresToItemsMap == null) {
            throw new IllegalStateException("Cannot create an empty order");
        }
        List<OrderItem> allOrderItems = new ArrayList<>();
        Order tempOrder;
        int totalDeliveryPrice = 0;
        for (Map.Entry<Store, Map<Integer, Double>> entry : m_StoresToItemsMap.entrySet()) {
            tempOrder = entry.getKey().createOrder(i_Date, i_Customer.getLocation(), entry.getValue(),m_StoresToItemsInDiscounts.getOrDefault(entry.getKey(),Collections.emptyList()));
            m_StoreToOrderMap.put(entry.getKey(), tempOrder);
            totalDeliveryPrice += tempOrder.getDeliveryPrice();
            allOrderItems.addAll(tempOrder.getAllItems());
        }

        m_CurrentOrder = new StorageOrder(++counter, new Order(i_Date, i_Customer.getLocation(), totalDeliveryPrice, allOrderItems ), m_StoreToOrderMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getId(),
                        Map.Entry::getValue)) ,i_Customer.getId());
        b_IsCreated = true;
    }
    public void executeOrder()
    {
        for (Map.Entry<Store,Order> entry: m_StoreToOrderMap.entrySet()
        ) {
            entry.getKey().addOrder(entry.getValue());
        }

        m_StorageOrders.add(m_CurrentOrder);
    }
    public void cleanup()
    {
        m_CurrentOrder=null;
        b_IsCreated=false;
        m_StoreToOrderMap.clear();
        m_StoresToItemsMap = null;
        m_StoresToItemsInDiscounts.clear();
    }

    public StorageOrder getCurrentOrder()
    {
        return m_CurrentOrder;
    }

    public boolean isOrderCreated()
    {
        return b_IsCreated;
    }

    public final Collection<StorageOrder> getStorageOrders()
    {
        return m_StorageOrders;
    }

    public Map<Store,List<StoreDiscount>> getAvailableDiscounts()
    {
        Map<Store,List<StoreDiscount>> res = new HashMap<>();
        for (Map.Entry<Store, Map<Integer, Double>> entry:m_StoresToItemsMap.entrySet()
        ){
            res.put(entry.getKey(),entry.getKey().getDiscounts().stream()
                    .filter(storeDiscount -> storeDiscount.isAvailable(entry.getValue()))
                    .collect(Collectors.toList()));
        }
        return res;
    }
}
