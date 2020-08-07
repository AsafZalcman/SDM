package utils;

import models.Item;
import models.Store;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;
import java.util.*;

public class SuperDuperManager {
    private Map<Integer, Store> m_StoreID2Store;
    private Map<Integer, StorageOrder> m_OrderID2Order;
    private Map<Integer, Item> m_ItemID2Item;

    public SuperDuperManager() {
        m_StoreID2Store = new HashMap<>();
        m_OrderID2Order = new HashMap<>();
        m_ItemID2Item = new HashMap<>();
    }

    public List<ArrayList<Object>> getStoreIDNamePPK(){
        List<ArrayList<Object>> storeDetails = new ArrayList<>();
        for(Map.Entry<Integer, Store> entry: m_StoreID2Store.entrySet()){
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
        for (Item item: items
             ) {
            m_ItemID2Item.put(item.getId(),item);
        }

        for (Store store: stores
        ) {
            m_StoreID2Store.put(store.getId(),store);
        }
    }


    @Override
    public String toString() {
        StringBuilder items = new StringBuilder("items:");
        for (Item item:m_ItemID2Item.values()
        ) {
items.append(item);
        }

        StringBuilder stores = new StringBuilder("stores:");
        for (Store store:m_StoreID2Store.values()
        ) {
            stores.append(store);
        }
        return items + "\n" + stores;
    }

    public void addNewStore(Store i_newStore){
        m_StoreID2Store.put(i_newStore.getId(), i_newStore);
    }
}