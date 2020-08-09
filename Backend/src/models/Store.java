package models;

import interfaces.IDelivery;
import interfaces.IUniquely;
import myLocation.Location;

import java.util.*;

public class Store implements IDelivery, IUniquely {
    private final Integer m_StoreID;
    private final String m_StoreName;
    private double m_PPK;
    private final Location m_Location;
    private Map<Integer, StoreItem> m_IdToStoreItem;
    private List<Order> m_Orders;
    private double m_TotalCostOfDelivery = 0;

    public Store(int i_StoreID, String i_StoreName, Location i_Location, double i_PPK) {
        this.m_StoreID = i_StoreID;
        this.m_StoreName = i_StoreName;
        this.m_Location = i_Location;
        this.m_PPK = i_PPK;
        m_IdToStoreItem = new HashMap<>();
        m_Orders = new ArrayList<>();
    }

    public Store(int i_StoreID, String i_StoreName, Location i_Location, double i_PPK, Collection<StoreItem> i_Items) {
        this(i_StoreID, i_StoreName, i_Location, i_PPK);
        createIdToStoreMapFromCollection(i_Items);
    }

    private void createIdToStoreMapFromCollection(Collection<StoreItem> i_Items) {
        for (StoreItem storeItem : i_Items
        ) {
            m_IdToStoreItem.put(storeItem.getItemId(), storeItem);
        }
    }

    public String getStoreName() {
        return m_StoreName;
    }

    public double getPPK() {
        return m_PPK;
    }

    public void setPPK(double m_PPK) {
        this.m_PPK = m_PPK;
    }

    @Override
    public Collection<Object> getDetails() {
        List<Object> details = new ArrayList<>();
        details.add(this.m_StoreID);
        details.add(this.m_StoreName);
        details.add(this.m_PPK);
        return details;
    }

    @Override
    public double getDeliveryPrice(Location i_DestLocation) {
        return this.m_Location.distance(i_DestLocation) * m_PPK;
    }

    @Override
    public Integer getId() {
        return m_StoreID;
    }

    public Collection<Integer> getAllItemsId() {
        return m_IdToStoreItem.keySet();
    }

    public Collection<StoreItem> getAllItems() {
        return m_IdToStoreItem.values();
    }

    public StoreItem getStoreItem(int i_Id) {
        return m_IdToStoreItem.get(i_Id);
    }

    public double getTotalCostOfDelivery() {
        return m_TotalCostOfDelivery;
    }

    public Order createOrder(Date i_orderDate, Location i_customerLocation, Map<Integer, Double> i_itemIdToAmountOfSellMap) {
        Map<Integer, StoreItem> itemIdToStoreItemsMap = new HashMap<>();
        i_itemIdToAmountOfSellMap.keySet().forEach(itemId -> {
            StoreItem storeItem = new StoreItem(m_IdToStoreItem.get(itemId));
            storeItem.setAmountOfSell(i_itemIdToAmountOfSellMap.get(storeItem.getItemId()));
            itemIdToStoreItemsMap.put(storeItem.getItemId(),storeItem);
        });
        return new Order(i_orderDate, i_customerLocation, getDeliveryPrice(i_customerLocation), itemIdToStoreItemsMap);
    }

    public void addOrder(Order i_Order)
    {
        m_Orders.add(i_Order);
        m_TotalCostOfDelivery += i_Order.getDeliveryPrice();
        for (StoreItem item : i_Order.getStoreItems()
        ) {
            m_IdToStoreItem.get(item.getItemId()).addAmountOfSells(item.getAmountOfSells());
        }
    }

    public Location getLocation()
    {
        return m_Location;
    }

    //only for debug
    @Override
    public String toString() {

        StringBuilder itemsString = new StringBuilder();
        for (StoreItem item : m_IdToStoreItem.values()
        ) {
            itemsString.append(item.toString());
        }
        return "Store{" +
                "m_StoreID=" + m_StoreID +
                ", m_StoreName='" + m_StoreName + '\'' +
                ", m_PPK=" + m_PPK +
                ", m_Location=" + m_Location +
                ", m_IdToStoreItem=" + itemsString +
                '}';
    }

}
