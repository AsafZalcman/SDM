package managers;

import interfaces.ILocationable;
import models.*;
import myLocation.Location;
import myLocation.LocationManager;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;

import java.time.LocalDate;
import java.util.*;

public class SuperDuperManager {
    private static SuperDuperManager m_Instance = null;
    private Map<String,Zone> m_ZoneNameToZoneMap;
    private CustomersManager m_CustomersManager;
    private AccountManager m_AccountManager;

    private SuperDuperManager() {
m_ZoneNameToZoneMap = new HashMap<>();
        m_CustomersManager = new CustomersManager();
        m_AccountManager=new AccountManager();
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

 //  public StoreManager getStoreManager() {
 //      return m_StoreManager;
 //  }

 //  public ItemManager getItemManager() {
 //      return m_ItemManager;
 //  }

 //  public OrderManager getOrderManager() {
 //       return m_OrderManager;
  //   }

    public void loadSuperDuperDataFromXml(String i_PathToFile,int i_OwnerId) throws Exception {
        Map<Location, ILocationable> currentLocations = new HashMap<>(LocationManager.getLocations());
        LocationManager.initLocations();
        try {
            JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
            jaxbConverter.loadJaxbData(i_PathToFile,i_OwnerId);
            synchronized (this) {
                if (m_ZoneNameToZoneMap.get(jaxbConverter.getZoneName()) != null) {
                    throw new IllegalAccessException("Zone:\"" + jaxbConverter.getZoneName() + "\" is already exists");
                }
                m_ZoneNameToZoneMap.put(jaxbConverter.getZoneName(), new Zone(jaxbConverter.getZoneName(),
                        i_OwnerId,
                        new StoreManager(jaxbConverter.getStores()),
                        new ItemManager(jaxbConverter.getItems()),
                        new OrderManager()));
            }
        } catch (Exception e) {
            LocationManager.setLocations(currentLocations);
            throw e;
        }
    }

    public Store getStore(String i_ZoneName,Integer i_StoreID) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getStore(i_StoreID);
    }

    public Item getItem(String i_ZoneName,Integer i_ItemID) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getItem(i_ItemID);
    }

 //  public void addItemToOrder(String i_ZoneName,Store store,Item i_Item, Double i_AmountOfSells) {
 //      if (store == null) {
 //          store = getCheapestStoreForItem(i_ZoneName,i_Item.getId());
 //      }

 //      m_OrderManager.addItemFromCurrentStore(store, i_Item, i_AmountOfSells);
 //  }

 //  public void addItemInDiscountToOrder(String i_DiscountName,int i_ItemId,double i_Price , double i_Amount)
 //  {
 //      Store store = m_StoreManager.getStoreFromDiscountName(i_DiscountName);
 //      if(store ==null)
 //      {
 //          throw new IllegalArgumentException("The discount named :\"" + i_DiscountName +"\" is not exists");
 //      }
 //      m_OrderManager.addItemFromCurrentStore(store,new OrderItem(new StoreItem(getItem(i_ItemId),i_Price,i_Amount),true));
 //  }

 //   public void createNewOrder(Customer customer, LocalDate date) {
 //       m_OrderManager.create(customer,date);
 //   }
//
 //   public void executeNewOrder() {
 //       m_OrderManager.executeOrder();
 //       StorageOrder currentStorageOrder = m_OrderManager.getCurrentOrder();
 //       for (OrderItem orderItem : currentStorageOrder.getOrder().getAllItems()) {
 //           m_ItemManager.addStorageItemSales(orderItem.getStoreItem().getItem().getId(), orderItem.getStoreItem().getAmountOfSells());
 //       }
//
 //       m_CustomersManager.getCustomer(currentStorageOrder.getCustomerId()).addOrder(currentStorageOrder);
 //       m_OrderManager.cleanup();
 //   }
//
 //   public StorageOrder getCurrentOrder() {
 //       return m_OrderManager.getCurrentOrder();
 //   }

    public Collection<StorageItem> getAllStorageItems(String i_ZoneName){
        return m_ZoneNameToZoneMap.get(i_ZoneName).getAllStorageItems();
    }

    private Store getCheapestStoreForItem(String i_ZoneName,int i_ItemId) {
        return  m_ZoneNameToZoneMap.get(i_ZoneName).getCheapestStoreForItem(i_ItemId);
    }

    public void insertNewItemToStore(String i_ZoneName,int i_StoreID, StoreItem i_NewStoreItem) throws Exception {
        this.m_ZoneNameToZoneMap.get(i_ZoneName).insertNewItemToStore(i_StoreID, i_NewStoreItem);
     }

    public boolean isItemExist(String i_ZoneName,int i_ItemID){
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isItemExist(i_ItemID);
    }

    public boolean isStoreItemBelongToTheStore(String i_ZoneName,int i_StoreID, int i_ItemID) {
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isStoreItemBelongToTheStore(i_StoreID, i_ItemID);
    }

    public boolean isStoreIDExist(String i_ZoneName,int i_StoreID){
        return this.m_ZoneNameToZoneMap.get(i_ZoneName).isStoreIDExist(i_StoreID);
    }

 //   public void abortOrder() {
 //       m_OrderManager.cleanup();
 //   }
//
    public Collection<Customer> getAllCustomers() {
        return m_CustomersManager.getAllCustomers();
    }

    public Customer getCustomer (int i_CustomerId)
    {
       return  m_CustomersManager.getCustomer(i_CustomerId);
    }

  //  public Map<Store,List<StoreDiscount>> getAvailableDiscountsForCurrentOrder()
  //  {
  //      return m_OrderManager.getAvailableDiscounts();
  //  }

    public int addCustomer(String i_Name) throws Exception {
        int id = m_CustomersManager.addCustomer(i_Name);
        m_AccountManager.addAccount(id);
        return id;
    }

    public void addNewStore(String i_ZoneName,Store i_Store,Collection<StoreItem> i_ItemsOfStore) throws Exception {
        this.m_ZoneNameToZoneMap.get(i_ZoneName).addNewStore(i_Store,i_ItemsOfStore);
    }

    public Collection<Store> getAllStores(String i_ZoneName) {
        return m_ZoneNameToZoneMap.get(i_ZoneName).getAllStores();
    }

    public Collection<Item> getAllItems(String i_ZoneName) {
       return m_ZoneNameToZoneMap.get(i_ZoneName).getAllItems();
    }
}