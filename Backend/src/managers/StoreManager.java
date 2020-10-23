package managers;

import models.*;
import java.util.*;

public class StoreManager {

    private Map<Integer, Store> m_StoreID2Store;


    public StoreManager(Collection<Store> i_Stores) {
        m_StoreID2Store = new HashMap<>();
        for (Store store : i_Stores
        ) {
           this.addStore(store);
        }
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

     public Collection<Integer> getAllStoresId()
     {
         return m_StoreID2Store.keySet();
     }

     public Collection<Store> getAllStores()
     {
         return m_StoreID2Store.values();
     }

  synchronized    public void addStore(Store i_Store)
     {
         if(isStoreIDExist(i_Store.getId()))
         {
             throw new IllegalArgumentException("Error: The id :\"" + i_Store.getId() +"\" is already occupied by another store");
         }
         m_StoreID2Store.put(i_Store.getId(),i_Store);
     }

    public Collection<StoreItem> getAllItemsInShops(int i_StoreId)
    {
        return m_StoreID2Store.get(i_StoreId).getAllItems();
    }

    public final Store getStore(Integer i_StoreId)
    {
        return m_StoreID2Store.get(i_StoreId);
    }

    public int howManyStoreSellTheInputItem(Integer i_ItemID){
        int result = 0;
        for(Store store: m_StoreID2Store.values()){
            if(store.isItemExists(i_ItemID)){
                result++;
            }
        }
        return result;
    }

    public double getItemAvgPrice(Integer i_itemID) {
        /*
        DoubleSummaryStatistics statistics = m_StoreID2Store.values().stream().filter(store -> {
            return store.isItemExists(i_itemID);
        }).map((Store i_Id) -> Store.getStoreItem(i_itemID)).mapToDouble(StoreItem::getPrice).summaryStatistics();
        return statistics.getAverage();
        */
        int storeCounter = 0;
        double itemTotalPrice = 0.0;
        for(Store store: m_StoreID2Store.values()){
            if(store.isItemExists(i_itemID)){
                storeCounter++;
                itemTotalPrice += store.getStoreItemPrice(i_itemID);
            }
        }
        return itemTotalPrice/storeCounter;
    }

    public final Store getCheapestStoreForItem(int i_ItemId)
    {
        return   m_StoreID2Store.values().stream()
                .filter(store -> store.isItemExists(i_ItemId))
                .reduce((prev, current) -> (prev.getStoreItemPrice(i_ItemId) < current.getStoreItemPrice(i_ItemId)) ? prev : current).orElse(null);
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice){
        m_StoreID2Store.get(i_StoreID).getStoreItem(i_StoreItemID).setPrice(i_NewPrice);
    }

    public void insertNewItemToStore(int i_StoreID, StoreItem i_NewStoreItem) throws Exception {
        Store store = m_StoreID2Store.get(i_StoreID);
        if(store.isItemExists(i_NewStoreItem.getItem().getId())){
            throw new Exception("Operation failed: The product is already exist in the store");
        }
        else {
            store.addStoreItem(i_NewStoreItem);
        }
    }

    public boolean isItemExist(int i_StoreID, int i_ItemID){
        return m_StoreID2Store.get(i_StoreID).isItemExists(i_ItemID);
    }

    synchronized public boolean isStoreIDExist(int i_storeID) {
        return m_StoreID2Store.containsKey(i_storeID);
    }

    public void deleteStoreItem(int i_StoreID, int i_ItemID) throws Exception {
        m_StoreID2Store.get(i_StoreID).removeStoreItem(i_ItemID);
    }

    public void addDiscountForStore(int i_StoreId, StoreDiscount i_Discount)
    {
        m_StoreID2Store.get(i_StoreId).addDiscount(i_Discount);
    }

    public Store getStoreFromDiscountName(String i_DiscountName) {
        return m_StoreID2Store.values().stream()
                .filter(store -> store.isDiscountExists(i_DiscountName))
                .findFirst().orElse(null);
    }

//   //only for debug
//   @Override
//   public String toString() {
//       StringBuilder items = new StringBuilder("items:");
//       for (Item item : m_ItemID2Item.values()
//       ) {
//           items.append(item);
//       }

//       StringBuilder stores = new StringBuilder("stores:");
//       for (Store store : m_StoreID2Store.values()
//       ) {
//           stores.append(store);
//       }
//       return items + "\n" + stores;
//   }

//  public void setStoreForOrder(int i_StoreId) throws Exception {
//      if(!m_StoreID2Store.containsKey(i_StoreId)) {
//          throw new Exception("Store with " + i_StoreId + " id is not exists");
//      }
//      m_OrderManager.setStore(m_StoreID2Store.get(i_StoreId));
//  }
//  public void setDateForOrder(String i_Date) throws ParseException {
//      m_OrderManager.setOrderDate(i_Date);
//  }
//  public void setLocationForOrder(int x, int y) throws LocationException {
//      m_OrderManager.setCustomerLocation(x, y);
//  }
//  public void addItemToOrder(Integer itemId,Double amountOfSells) throws Exception
//  {
//      if(m_ItemID2Item.get(itemId) == null)
//      {
//          throw new Exception("Item with:" + itemId +" id is not exists");
//      }
//      m_OrderManager.addItem(m_ItemID2Item.get(itemId),amountOfSells);
//  }
}
