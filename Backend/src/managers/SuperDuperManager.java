package managers;

import enums.UserType;
import interfaces.ILocationable;
import models.*;
import myLocation.Location;
import myLocation.LocationManager;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class SuperDuperManager {
    private static SuperDuperManager m_Instance = null;
    private Map<String, Zone> m_ZoneNameToZoneMap;
    private UsersManager m_UsersManager;
    private AccountManager m_AccountManager;

    private SuperDuperManager() {
        m_ZoneNameToZoneMap = new HashMap<>();
        m_UsersManager = new UsersManager();
        m_AccountManager = new AccountManager();
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

    //need to avoid this duplicate
    public void loadSuperDuperDataFromXml(String i_PathToFile, String i_OwnerName) throws Exception {
        try {
            JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
            jaxbConverter.loadJaxbData(i_PathToFile, i_OwnerName);
            getDataFromJaxb(jaxbConverter, i_OwnerName);

        } catch (Exception e) {
            throw e;
        }
    }

    public void loadSuperDuperDataFromXml(InputStream i_InputStream, String i_OwnerName) throws Exception {
        try {
            JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
            jaxbConverter.loadJaxbData(i_InputStream, i_OwnerName);
            getDataFromJaxb(jaxbConverter, i_OwnerName);
        } catch (Exception e) {
            throw e;
        }
    }

    private void getDataFromJaxb(JaxbConverter i_JaxbConverter, String i_OwnerName) throws IllegalAccessException {
        synchronized (this) {
            if (m_ZoneNameToZoneMap.get(i_JaxbConverter.getZoneName()) != null) {
                throw new IllegalAccessException("Zone:\"" + i_JaxbConverter.getZoneName() + "\" is already exists");
            }
            m_ZoneNameToZoneMap.put(i_JaxbConverter.getZoneName(), new Zone(i_JaxbConverter.getZoneName(),
                    i_OwnerName,
                    new ItemManager(i_JaxbConverter.getItems()),
                    new StoreManager(i_JaxbConverter.getStores()),
                    new OrderManager()));
        }
    }

    public Store getStore(String i_ZoneName, Integer i_StoreID) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getStore(i_StoreID);
    }


    public Item getItem(String i_ZoneName, Integer i_ItemID) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getItem(i_ItemID);
    }

    public Collection<Zone> getAllZones() {
        return m_ZoneNameToZoneMap.values();
    }

    public Zone getZone(String i_ZoneName) {
        return m_ZoneNameToZoneMap.get(i_ZoneName);
    }

    public Collection<StorageItem> getAllStorageItems(String i_ZoneName) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getAllStorageItems();
    }

    private Store getCheapestStoreForItem(String i_ZoneName, int i_ItemId) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getCheapestStoreForItem(i_ItemId);
    }

    public void insertNewItemToStore(String i_ZoneName, int i_StoreID, StoreItem i_NewStoreItem) throws Exception {
        this.m_ZoneNameToZoneMap.get(i_ZoneName).insertNewItemToStore(i_StoreID, i_NewStoreItem);
    }

    public boolean isItemExist(String i_ZoneName, int i_ItemID) {
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isItemExist(i_ItemID);
    }

    public boolean isStoreItemBelongToTheStore(String i_ZoneName, int i_StoreID, int i_ItemID) {
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isStoreItemBelongToTheStore(i_StoreID, i_ItemID);
    }

    public boolean isStoreIDExist(String i_ZoneName, int i_StoreID) {
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isStoreIDExist(i_StoreID);
    }

    public void abortOrder(String i_ZoneName, int i_StorageOrderIdToAbort) {
        m_ZoneNameToZoneMap.get(i_ZoneName).abortOrder(i_StorageOrderIdToAbort);
    }

    public Collection<User> getAllCustomers() {
        return m_UsersManager.getAllUsers();
    }

    public User getCustomer(int i_CustomerId) {
        return m_UsersManager.getUser(i_CustomerId);
    }

    public int addUser(String i_Name, UserType i_UserType) throws Exception {
        int id = m_UsersManager.addUser(i_Name, i_UserType);
        m_AccountManager.addAccount(id);
        return id;
    }

    public void addNewStore(String i_ZoneName, Store i_Store, Collection<StoreItem> i_ItemsOfStore) throws Exception {
        this.m_ZoneNameToZoneMap.get(i_ZoneName).addNewStore(i_Store, i_ItemsOfStore);
    }

    public Collection<Store> getAllStores(String i_ZoneName) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getAllStores();
    }

    public Collection<Item> getAllItems(String i_ZoneName) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getAllItems();
    }

    public AccountManager getAccountManager() {
        return m_AccountManager;
    }

    public void createOrder(String m_zoneName, Map<Integer, Double> m_currentIdToAmount) {
        for (Map.Entry<Integer, Double> entry : m_currentIdToAmount.entrySet()
        ) {

        }
    }

    public Store getCheapestStore(String i_ZoneName, int i_ItemId) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getCheapestStoreForItem(i_ItemId);
    }

    public StorageOrder createOrder(String i_ZoneName, LocalDate i_CurrentOrderDate, Location i_Location, Map<Integer, Map<Integer, Double>> i_StoreIdToItemsMap, Map<Integer, List<OrderItem>> i_StoreIdToItemsInDiscounts, int i_UserId) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).createOrder(i_CurrentOrderDate, i_Location, i_StoreIdToItemsMap, i_StoreIdToItemsInDiscounts, i_UserId);
    }

    public void executeNewOrder(String i_ZoneName, int orderID) {
        m_ZoneNameToZoneMap.get(i_ZoneName).executeNewOrder(orderID);
    }
}