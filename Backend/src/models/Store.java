package models;

import interfaces.IDelivery;
import interfaces.ILocationable;
import interfaces.IUniquely;
import myLocation.Location;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Store implements IDelivery, IUniquely, ILocationable {
    private final Integer m_StoreID;
    private final String m_StoreName;
    private double m_PPK;
    private final Location m_Location;
    private Map<Integer, StoreItem> m_IdToStoreItem;
    private List<Order> m_Orders;
    private List<StoreDiscount> m_StoreDiscounts;
    private double m_IncomesFromDeliveries = 0;
    private final String m_OwnerName;
    private List<StoreFeedback> m_StoreFeedbacks;

    public Store(int i_StoreID, String i_StoreName, Location i_Location, double i_PPK , String i_OwnerName) {
        this.m_StoreID = i_StoreID;
        this.m_StoreName = i_StoreName;
        this.m_Location = i_Location;
        this.m_PPK = i_PPK;
        m_IdToStoreItem = new HashMap<>();
        m_Orders = new ArrayList<>();
        m_OwnerName =i_OwnerName;
        m_StoreFeedbacks = new ArrayList<>();
    }

    public Store(int i_StoreID, String i_StoreName, Location i_Location, double i_PPK,Collection<StoreItem> i_Items,String i_OwnerName) {
        this(i_StoreID, i_StoreName, i_Location, i_PPK,i_OwnerName);
        createIdToStoreMapFromCollection(i_Items);
    }

    private void createIdToStoreMapFromCollection(Collection<StoreItem> i_Items) {
        for (StoreItem storeItem : i_Items
        ) {
            m_IdToStoreItem.put(storeItem.getItem().getId(), storeItem);
        }
    }

    public boolean isItemExists(Integer i_StoreItemID){
        return m_IdToStoreItem.containsKey(i_StoreItemID);
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

    public String getOwnerName()
    {
        return m_OwnerName;
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

    public double getStoreItemPrice(Integer i_StoreItemID){
        return this.isItemExists(i_StoreItemID) ? m_IdToStoreItem.get(i_StoreItemID).getPrice() : 0;
    }

    public StoreItem getStoreItem(Integer i_Id) {
        return m_IdToStoreItem.get(i_Id);
    }

    public double getIncomesFromDeliveries() {
        return m_IncomesFromDeliveries;
    }

    public Order createOrder(LocalDate i_orderDate, Location i_customerLocation, Map<Integer, Double> i_itemIdToAmountOfSellMap) {
        return createOrder(i_orderDate,i_customerLocation,i_itemIdToAmountOfSellMap,Collections.emptyList());
    }
    public Order createOrder(LocalDate i_orderDate, Location i_customerLocation, Map<Integer, Double> i_itemIdToAmountOfSellMap, Collection<OrderItem> i_ItemsInDiscount) {
        Map<Integer, OrderItem> itemIdToStoreItemsMap = new HashMap<>();
        i_itemIdToAmountOfSellMap.keySet().forEach(itemId -> {
            StoreItem storeItem = new StoreItem(m_IdToStoreItem.get(itemId));
            storeItem.setAmountOfSell(i_itemIdToAmountOfSellMap.get(storeItem.getItem().getId()));
            itemIdToStoreItemsMap.put(storeItem.getItem().getId(),new OrderItem(storeItem,false));
        });

        List<OrderItem> allItems = Stream.concat(
                itemIdToStoreItemsMap.values().stream(),
                i_ItemsInDiscount.stream())
                .collect(Collectors.toList());
        return new Order(i_orderDate, i_customerLocation, getDeliveryPrice(i_customerLocation), allItems);
    }

    public void addOrder(Order i_Order)
    {
        m_Orders.add(i_Order);
        m_IncomesFromDeliveries += i_Order.getDeliveryPrice();
        for (OrderItem orderItem : i_Order.getAllItems()
        ) {
            m_IdToStoreItem.get(orderItem.getStoreItem().getItem().getId()).addAmountOfSells(orderItem.getStoreItem().getAmountOfSells());
        }
    }
    @Override
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

    public Collection<Order> getOrders()
    {
        return m_Orders;
    }

    public void addStoreItem(StoreItem i_StoreItem){
        this.m_IdToStoreItem.put(i_StoreItem.getItem().getId(),i_StoreItem);
    }

    public void removeStoreItem(int i_StoreItemID) throws Exception{
        if(m_IdToStoreItem.size() == 1){
            throw new Exception("Operation failed: This is the only item that the store selling, so it can not be remove");
        }
        //remove all discounts related to this item
        if(haveDiscounts()) {
            m_StoreDiscounts.removeIf(storeDiscount -> storeDiscount.getDiscountCondition().getItemId() == i_StoreItemID || storeDiscount.getItemIdToStoreOfferMap().containsKey(i_StoreItemID));
        }
        this.m_IdToStoreItem.remove(i_StoreItemID);
    }

    public Collection<StoreDiscount> getDiscounts()
    {
        return m_StoreDiscounts;
    }

    public boolean isDiscountExists(String i_DiscountName)
    {

        return this.haveDiscounts() && m_StoreDiscounts.stream().
                filter(storeDiscount -> storeDiscount.getName().equals(i_DiscountName)).count() == 1;
    }

    public void addDiscount(StoreDiscount i_StoreDiscount)
    {
        if(m_StoreDiscounts ==null)
        {
            m_StoreDiscounts = new ArrayList<>();
        }
        m_StoreDiscounts.add(i_StoreDiscount);
    }

    public boolean haveDiscounts()
    {
        return m_StoreDiscounts!=null && !m_StoreDiscounts.isEmpty();
    }

    public int getTotalAmountOfOrders(){
        return m_Orders.size();
    }

    public void addFeedback(StoreFeedback i_StoreFeedback)
    {
        m_StoreFeedbacks.add(i_StoreFeedback);
    }
    public Collection<StoreFeedback> getAllFeedbacks(){
        return m_StoreFeedbacks;
    }

    public static class StoreFeedback
    {
        private final int m_Rank;
        private final String m_Description;
        private final String m_UserName;
        private final LocalDate m_Date;
        public StoreFeedback(int i_Rank, String i_Description, String i_UserName, LocalDate i_Date)
        {
            m_Date=i_Date;
            m_Description=i_Description;
            m_Rank=i_Rank;
            m_UserName=i_UserName;
        }
        public StoreFeedback(int i_Rank, String i_UserName, LocalDate i_Date)
        {
            this(i_Rank,"",i_UserName,i_Date);
        }

        public int getRank() {
            return m_Rank;
        }

        public String getDescription() {
            return m_Description;
        }

        public String getUserName() {
            return m_UserName;
        }

        public LocalDate getDate() {
            return m_Date;
        }
    }

}