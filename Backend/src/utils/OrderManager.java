package utils;

import models.Item;
import models.Order;
import models.Store;
import myLocation.Location;
import myLocation.LocationException;
import myLocation.LocationManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderManager {
    private int counter = 0;
    private Store m_CurrentStore;
    private Date m_CurrentOrderDate;
    private Location m_CurrentCustomerLocation;
    private Map<Integer, Double> m_CurrentIdToStoreItem;
    private Order m_CurrentOrder;
    private List<StorageOrder> m_StorageOrders = new ArrayList<>();
    private boolean b_IsCreated = false;

    public void setStore(Store i_Store)
    {
        m_CurrentStore =i_Store;
    }

    public void setOrderDate(String i_OrderDate) throws ParseException {

        DateFormat format = new SimpleDateFormat("dd/mm-hh:mm", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(i_OrderDate);
        } catch (ParseException e) {
            throw new ParseException("the given date: " + i_OrderDate + " is not in the correct pattern, which is dd/mm-hh:mm",e.getErrorOffset());
        }

        this.m_CurrentOrderDate = date;
    }

    public void setCustomerLocation(int x, int y) throws LocationException {
      LocationManager.isLocationAvailable(x,y);
      LocationManager.isValidLocation(x,y);
        this.m_CurrentCustomerLocation = new Location(x,y);
    }

    public void addItem(Item item, double amountOfSells) throws Exception {

        if(!m_CurrentStore.getAllItemsId().contains(item.getId()))
        {
            throw new Exception("The item with " + item.getId() + " id is not for sell in the requested store");
        }

        if(item.getPurchaseForm() == PurchaseForm.QUANTITY)
        {
            if ((amountOfSells != Math.floor(amountOfSells)) || Double.isInfinite(amountOfSells)) {
                throw new Exception("Item with " + item.getId() + " id is sold only in whole numbers");
            }
        }

        if(m_CurrentIdToStoreItem == null)
        {
            m_CurrentIdToStoreItem=new HashMap<>();
        }

        double currentAmountOfSells =m_CurrentIdToStoreItem.getOrDefault(item.getId(),0.0);

        m_CurrentIdToStoreItem.put(item.getId(),amountOfSells+currentAmountOfSells);
    }

    public void create()
    {
        m_CurrentOrder = m_CurrentStore.createOrder(m_CurrentOrderDate,m_CurrentCustomerLocation,m_CurrentIdToStoreItem);
        b_IsCreated=true;
    }
    public void executeOrder()
    {
        m_CurrentStore.addOrder(m_CurrentOrder);
        m_StorageOrders.add(new StorageOrder(++counter,m_CurrentOrder, m_CurrentStore.getId(), m_CurrentStore.getStoreName()));
        cleanup();
    }
    public void cleanup()
    {
        m_CurrentStore=null;
        m_CurrentCustomerLocation=null;
        m_CurrentIdToStoreItem=null;
        m_CurrentOrderDate=null;
        m_CurrentOrder=null;
        b_IsCreated=false;

    }

    public Store getStore() {
        return m_CurrentStore;
    }

    public Order getCurrentOrder()
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
