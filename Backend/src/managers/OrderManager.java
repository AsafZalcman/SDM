package managers;

import enums.PurchaseForm;
import models.Item;
import models.Order;
import models.Store;
import models.StoreItem;
import myLocation.Location;
import myLocation.LocationException;
import myLocation.LocationManager;
import models.StorageOrder;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManager {
    private int counter = 0;
    private Store m_CurrentStore;
    private Date m_CurrentOrderDate;
    private Location m_CurrentCustomerLocation;
    private Map<Integer, Double> m_CurrentIdToStoreItem;
    private Map<Store,Order> m_StoreToOrderMap = new HashMap<>();
    private StorageOrder m_CurrentOrder;
    private List<StorageOrder> m_StorageOrders = new ArrayList<>();
    private boolean b_IsCreated = false;
    //badName
    private Map<Store,Map<Integer,Double>> m_StoresToItemsMap;

    public void setStore(Store i_Store)
    {
        m_CurrentStore =i_Store;
    }

    public void setOrderDate(Date i_OrderDate) {
        this.m_CurrentOrderDate = i_OrderDate;
    }

    public void setCustomerLocation(int x, int y) throws LocationException {
        LocationManager.isLocationAvailable(x,y);
        LocationManager.isValidLocation(x,y);
        this.m_CurrentCustomerLocation = new Location(x,y);
    }

    public void addItem(Item i_Item, double i_AmountOfSells) throws Exception {

        if(i_Item.getPurchaseForm() == PurchaseForm.QUANTITY)
        {
            if ((i_AmountOfSells != Math.floor(i_AmountOfSells)) || Double.isInfinite(i_AmountOfSells)) {
                throw new Exception("Error: The item with ID: " + i_Item.getId() + " is sold only in whole numbers");
            }
        }

        addItemFromCurrentStore(i_Item,i_AmountOfSells);
    }

    private void addItemFromCurrentStore(Item i_Item, double i_AmountOfSells) throws Exception
    {
        if(!m_CurrentStore.getAllItemsId().contains(i_Item.getId()))
        {
            throw new Exception("Error: The item with ID: " + i_Item.getId() + " is not for sell in the requested store");
        }

        if(m_StoresToItemsMap == null)
        {
            m_StoresToItemsMap = new HashMap<>();
        }

        m_CurrentIdToStoreItem = m_StoresToItemsMap.get(m_CurrentStore);

        if(m_CurrentIdToStoreItem == null)
        {
            m_CurrentIdToStoreItem=new HashMap<>();
        }

        double currentAmountOfSells =m_CurrentIdToStoreItem.getOrDefault(i_Item.getId(),0.0);

        m_CurrentIdToStoreItem.put(i_Item.getId(),i_AmountOfSells+currentAmountOfSells);
        m_StoresToItemsMap.put(m_CurrentStore,m_CurrentIdToStoreItem);
    }

    public void create() {
        if (m_StoresToItemsMap == null) {
            throw new IllegalStateException("Error: Cannot create an empty order");
        }
        Map<Integer, StoreItem> allItems = new HashMap<>();
        Order tempOrder;
        int totalDeliveryPrice = 0;
        for (Map.Entry<Store, Map<Integer, Double>> entry : m_StoresToItemsMap.entrySet()) {
            tempOrder = entry.getKey().createOrder(m_CurrentOrderDate, m_CurrentCustomerLocation, entry.getValue());
            m_StoreToOrderMap.put(entry.getKey(), tempOrder);
            totalDeliveryPrice += tempOrder.getDeliveryPrice();
            for (StoreItem storeItems : tempOrder.getStoreItems()
            ) {
                allItems.put(storeItems.getItem().getId(), storeItems);
            }
        }
        m_CurrentOrder = new StorageOrder(++counter, new Order(m_CurrentOrderDate, m_CurrentCustomerLocation, totalDeliveryPrice, allItems), m_StoreToOrderMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getId(),
                        Map.Entry::getValue)));
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
        m_CurrentStore=null;
        m_CurrentCustomerLocation=null;
        m_CurrentIdToStoreItem.clear();
        m_CurrentOrderDate=null;
        m_CurrentOrder=null;
        b_IsCreated=false;
        m_StoreToOrderMap.clear();
    }

    public Store getStore() {
        return m_CurrentStore;
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
}
