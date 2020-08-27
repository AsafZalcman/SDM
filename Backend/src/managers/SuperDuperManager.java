package managers;

import models.Item;
import models.Store;
import models.StoreItem;
import myLocation.Location;
import myLocation.LocationException;
import models.StorageItem;
import models.StorageOrder;
import myLocation.LocationManager;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;

import java.util.*;

public class SuperDuperManager {
    private static SuperDuperManager m_Instance = null;
    private OrderManager m_OrderManager;
    private StoreManager m_StoreManager;
    private ItemManager m_ItemManager;


    private SuperDuperManager() {

    }

    public static SuperDuperManager getInstance() {
        if (m_Instance == null) {
            synchronized (SuperDuperManager.class) {
                if (m_Instance == null) {
                    m_Instance = new SuperDuperManager();
                }
            }
        }
        return m_Instance;
    }

    public StoreManager getStoreManager() {
        return m_StoreManager;
    }

    public ItemManager getItemManager() {
        return m_ItemManager;
    }

    public OrderManager getOrderManager() {
        return m_OrderManager;
    }

    public void loadSuperDuperDataFromXml(String i_PathToFile) throws Exception {
       Map<Location,Integer> currentLocations = LocationManager.getLocations();
        LocationManager.initLocations();
        try {
            JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
            jaxbConverter.loadJaxbData(i_PathToFile);
            m_ItemManager = new ItemManager(jaxbConverter.getItems());
            m_StoreManager = new StoreManager(jaxbConverter.getStores());
            m_OrderManager = new OrderManager();
            initializeStorageItems();
        }catch (Exception e)
        {
            LocationManager.setLocations(currentLocations);
            throw e;
        }
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

    public void setStoreToOrder(Store i_Store) {
        m_OrderManager.setStore(i_Store);
    }

    public void setDateToOrder(Date i_OrderDate) {
        m_OrderManager.setOrderDate(i_OrderDate);
    }

    public void setCustomerLocationToOrder(int i_X, int i_Y) throws LocationException {
        m_OrderManager.setCustomerLocation(i_X, i_Y);
    }

    public Item getItem(Integer i_ItemID) {
        return m_ItemManager.getItem(i_ItemID);
    }

    public void addItemToOrder(Item i_Item, Double i_AmountOfSells) throws Exception {
        if(m_OrderManager.getStore()==null) {
            m_OrderManager.setStore(getCheapestStoreForItem(i_Item.getId()));
            m_OrderManager.addItem(i_Item, i_AmountOfSells);
            m_OrderManager.setStore(null);
        }
        else {
            m_OrderManager.addItem(i_Item, i_AmountOfSells);
        }
    }

    public void createNewOrder() {
        m_OrderManager.create();
    }

    public void executeNewOrder() {
        m_OrderManager.executeOrder();

        for (StoreItem storeItem : m_OrderManager.getCurrentOrder().getOrder().getStoreItems()) {
            m_ItemManager.addStorageItemSales(storeItem.getItem().getId(), storeItem.getAmountOfSells());
        }

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

    public void deleteStoreItem(int i_StoreID, int i_ItemID) throws Exception {
        if(m_ItemManager.getStorageItemNumberOfStoreSellIt(i_ItemID) <= 1){
            throw new Exception("Operation failed: This is the only store that sell this item, so it can not be remove");
        }
        else{
            m_StoreManager.deleteStoreItem(i_StoreID, i_ItemID);
            this.m_ItemManager.addStorageItemSellItValue(i_ItemID, -1);
            this.m_ItemManager.setNewAvgPriceForStorageItem(i_ItemID, m_StoreManager.getItemAvgPrice(i_ItemID));
        }
    }

    public void abortOrder() {
        m_OrderManager.cleanup();
    }
}