package models;

import managers.*;
import myLocation.LocationManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Zone {

    private OrderManager m_OrderManager;
    private StoreManager m_StoreManager;
    private ItemManager m_ItemManager;
    private final String m_Name;
    private final String m_OwnerName;

    public StoreManager getStoreManager() {
        return m_StoreManager;
    }

    public ItemManager getItemManager() {
        return m_ItemManager;
    }

    public OrderManager getOrderManager() {
        return m_OrderManager;
    }

    public String getName(){return m_Name;}

    public Zone (String i_Name,String i_OwnerName,ItemManager i_ItemManager,StoreManager i_StoreManager,OrderManager i_OrderManager)
    {
        m_ItemManager=i_ItemManager;
        m_OrderManager=i_OrderManager;
        m_StoreManager=i_StoreManager;
        m_Name=i_Name;
        m_OwnerName =i_OwnerName;
        initializeStorageItems();
    }

    private void initializeStorageItems() {
        for (Integer itemID : m_ItemManager.getAllItemsId()) {
            m_ItemManager.setNewStoreSellItForStorageItem(itemID, m_StoreManager.howManyStoreSellTheInputItem(itemID));
            m_ItemManager.setNewAvgPriceForStorageItem(itemID, m_StoreManager.getItemAvgPrice(itemID));
        }
    }

    public Store getStore(Integer i_StoreID) {
        return m_StoreManager.getStore(i_StoreID);
    }

    public String getOwnerName()
    {
        return m_OwnerName;
    }

    public Item getItem(Integer i_ItemID) {
        return m_ItemManager.getItem(i_ItemID);
    }

    public void addItemToOrder(Store store,Item i_Item, Double i_AmountOfSells) {
        if (store == null) {
            store = getCheapestStoreForItem(i_Item.getId());
        }

        m_OrderManager.addItemFromCurrentStore(store, i_Item, i_AmountOfSells);
    }

    public void addItemInDiscountToOrder(String i_DiscountName,int i_ItemId,double i_Price , double i_Amount)
    {
        Store store = m_StoreManager.getStoreFromDiscountName(i_DiscountName);
        if(store ==null)
        {
            throw new IllegalArgumentException("The discount named :\"" + i_DiscountName +"\" is not exists");
        }
        m_OrderManager.addItemFromCurrentStore(store,new OrderItem(new StoreItem(getItem(i_ItemId),i_Price,i_Amount),true));
    }

  //  public void createNewOrder(User user, LocalDate date) {
  //      m_OrderManager.create(user,date);
  //  }

    public void executeNewOrder() {
        m_OrderManager.executeOrder();
        StorageOrder currentStorageOrder = m_OrderManager.getCurrentOrder();
        for (OrderItem orderItem : currentStorageOrder.getOrder().getAllItems()) {
            m_ItemManager.addStorageItemSales(orderItem.getStoreItem().getItem().getId(), orderItem.getStoreItem().getAmountOfSells());
        }

        //    m_CustomersManager.getCustomer(currentStorageOrder.getCustomerId()).addOrder(currentStorageOrder);
        m_OrderManager.cleanup();
    }

    public StorageOrder getCurrentOrder() {
        return m_OrderManager.getCurrentOrder();
    }

    public Collection<StorageItem> getAllStorageItems(){
        return m_ItemManager.getAllStorageItems();
    }

    //only for debug
    @Override
    public String toString() {
        StringBuilder items = new StringBuilder("items:");
        for (Item item : m_ItemManager.getAllItems()
        ) {
            items.append(item);
        }

        StringBuilder stores = new StringBuilder("stores:");
        for (Store store : m_StoreManager.getAllStores()
        ) {
            stores.append(store);
        }
        return items + "\n" + stores;
    }

    public Store getCheapestStoreForItem(int i_ItemId) {
        return m_StoreManager.getCheapestStoreForItem(i_ItemId);
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice){
        m_StoreManager.updateStoreItemPrice(i_StoreID, i_StoreItemID, i_NewPrice);
        m_ItemManager.setNewAvgPriceForStorageItem(i_StoreItemID, m_StoreManager.getItemAvgPrice(i_StoreItemID));
    }

    public void insertNewItemToStore(int i_StoreID, StoreItem i_NewStoreItem) throws Exception {
        this.m_StoreManager.insertNewItemToStore(i_StoreID, i_NewStoreItem);
        this.m_ItemManager.addStorageItemSellItValue(i_NewStoreItem.getItem().getId(), 1);
        int storeItemID = i_NewStoreItem.getItem().getId();
        this.m_ItemManager.setNewAvgPriceForStorageItem(storeItemID, m_StoreManager.getItemAvgPrice(storeItemID));
    }

    public boolean isItemExist(int i_ItemID){
        return m_ItemManager.isItemExist(i_ItemID);
    }

    public boolean isStoreItemBelongToTheStore(int i_StoreID, int i_ItemID) {
        return m_StoreManager.isItemExist(i_StoreID, i_ItemID);
    }

    public boolean isStoreIDExist(int i_StoreID){
        return m_StoreManager.isStoreIDExist(i_StoreID);
    }

    public void abortOrder() {
        m_OrderManager.cleanup();
    }

    public Map<Store, List<StoreDiscount>> getAvailableDiscountsForCurrentOrder()
    {
        return m_OrderManager.getAvailableDiscounts();
    }

    public void addNewStore(Store i_Store,Collection<StoreItem> i_ItemsOfStore) throws Exception {
        m_StoreManager.addStore(i_Store);
        for (StoreItem storeItem: i_ItemsOfStore
        ) {
            insertNewItemToStore(i_Store.getId(), storeItem);
        }
        LocationManager.addLocation(i_Store.getLocation().x, i_Store.getLocation().y, i_Store);
    }

    public Collection<Store> getAllStores() {
        return   m_StoreManager.getAllStores();
    }

    public Collection<Item> getAllItems() {
        return m_ItemManager.getAllItems();
    }
}