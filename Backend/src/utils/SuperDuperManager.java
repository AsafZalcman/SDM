package utils;

import models.Item;
import models.Store;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;
import java.util.*;

public class SuperDuperManager {
    private static SuperDuperManager m_Instance = null;
    private OrderManager m_OrderManager;
    private StoreManager m_StoreManager;
    private ItemManager m_ItemManager;


    private SuperDuperManager() {
        m_OrderManager=new OrderManager(); // after we will implement the bonus, we should replace this line in xml method
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

    public StoreManager getStoreManager()
    {
        return m_StoreManager;
    }

    public ItemManager getItemManager()
    {
        return m_ItemManager;
    }

    public OrderManager getOrderManager()
    {
        return m_OrderManager;
    }

    public void loadSuperDuperDataFromXml(String i_PathToFile) throws Exception {
        JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
        jaxbConverter.loadJaxbData(i_PathToFile);
        m_ItemManager = new ItemManager(jaxbConverter.getItems());
        m_StoreManager = new StoreManager(jaxbConverter.getStores());
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
}