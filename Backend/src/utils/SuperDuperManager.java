package utils;

import models.*;
import myLocation.Location;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;
import java.util.*;

public class SuperDuperManager {
    private Map<Integer, Store> m_StoreID2Store;
    private Map<Integer, StorageOrder> m_OrderID2Order; // maybe we need list and not map
    private Map<Integer, Item> m_ItemID2Item;
    private static SuperDuperManager m_Instance = null;


    private SuperDuperManager() {
        m_StoreID2Store = new HashMap<>();
        m_OrderID2Order = new HashMap<>();
        m_ItemID2Item = new HashMap<>();
    }

    public static SuperDuperManager getInstance() {
        if (m_Instance == null) {
            //synchronized block to remove overhead
            synchronized (SuperDuperManager.class) {
                if (m_Instance == null) {
                    m_Instance = new SuperDuperManager();
                }
            }
        }
        return m_Instance;
    }

    public List<ArrayList<Object>> getStoreIDNamePPK() {
        List<ArrayList<Object>> storeDetails = new ArrayList<>();
        for (Map.Entry<Integer, Store> entry : m_StoreID2Store.entrySet()) {
            ArrayList<Object> store = new ArrayList<>();
            store.add(entry.getKey());
            store.add((entry.getValue().getStoreName()));
            store.add(entry.getValue().getPPK());
            storeDetails.add(store);
        }
        return storeDetails;
    }

    public void loadSuperDuperDataFromXml(String i_PathToFile) throws Exception {
        JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
        jaxbConverter.loadJaxbData(i_PathToFile);
        Collection<Item> items = jaxbConverter.getItems();
        Collection<Store> stores = jaxbConverter.getStores();
        for (Item item : items
        ) {
            m_ItemID2Item.put(item.getId(), item);
        }

        for (Store store : stores
        ) {
            m_StoreID2Store.put(store.getId(), store);
        }
    }

    /**
    //should be collection of our dto maybe
    //very bad implement!!!!!
    // we should think about poly between the dto
    //maybe implement factory for the dto.
    //maybe the dto creation should be inside the corresponding class (with store its not possible with the current impl)
     **/
//  public Collection<DtoStore> getAllStores()
//  {
//      DtoItem currentDtoItem;
//      Item currentItem;
//      StoreItem currentStoreItem;
//      DtoStore currentDtoStore;
//      Collection<DtoItem> currentDtoItems = new ArrayList<>();
//      Collection<DtoOrder> currentDtoOrders = new ArrayList<>();
//      Collection<DtoStore> res = new ArrayList<>();

//      for (Store store:m_StoreID2Store.values()
//           ) {
//          for (Integer id:store.getAllItems()
//               ) {
//              currentItem = m_ItemID2Item.get(id);
//              currentStoreItem = store.getStoreItem(id);
//              currentDtoItem = new DtoItem(id,currentItem.getItemName(),currentItem.getPurchaseForm(),currentStoreItem.getPrice(),currentStoreItem.getAmountOfSells());
//              currentDtoItems.add(currentDtoItem);
//          }
//          for (Integer id:store.getAllOrders()
//               ) {
//              currentDtoOrders.add(new DtoOrder(m_OrderID2Order.get(id),store.getId(),store.getStoreName()));
//          }

//          res.add(new DtoStore(store.getId(),store.getStoreName(),store.getPPK(),store.getDeliveryPrice(),))
//          currentDtoItems.clear();
//          currentDtoOrders.clear();
//      }
//  }

    public void createOrder(Integer i_StoreId,Date i_OrderDate, Location i_CustomerLocation,Map<Integer,Integer> i_ItemIdToAmountOfSellMap) {
        Store store = m_StoreID2Store.get(i_StoreId);
        Order createdOrder = store.createOrder(i_OrderDate, i_CustomerLocation, i_ItemIdToAmountOfSellMap);
        StorageOrder storageOrder = new StorageOrder(createdOrder, store.getId(), store.getStoreName());
        m_OrderID2Order.put(storageOrder.getM_OrderID(), storageOrder);

    }
    //should be dto
    public Collection<StoreItem> getAllItemsInShops(int i_StoreId)
    {
        return m_StoreID2Store.get(i_StoreId).getAllItems();
    }

//only for debug
    @Override
    public String toString() {
        StringBuilder items = new StringBuilder("items:");
        for (Item item : m_ItemID2Item.values()
        ) {
            items.append(item);
        }

        StringBuilder stores = new StringBuilder("stores:");
        for (Store store : m_StoreID2Store.values()
        ) {
            stores.append(store);
        }
        return items + "\n" + stores;
    }

    public void addNewStore(Store i_newStore) {
        m_StoreID2Store.put(i_newStore.getId(), i_newStore);
    }
}