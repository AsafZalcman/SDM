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
            store.add((entry.getValue().getM_StoreName()));
            store.add(entry.getValue().getM_PPK());
            storeDetails.add(store);
        }
        return storeDetails;
    }

    public void addNewStore(Store i_newStore){
        m_StoreID2Store.put(i_newStore.getM_StoreID(), i_newStore);
    }
}