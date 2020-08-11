package utils;

import models.Item;
import models.Order;
import models.Store;
import models.StoreItem;
import myLocation.LocationException;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;

import java.text.ParseException;
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

//   public StoreManager getStoreManager()
//   {
//       return m_StoreManager;
//   }

//   public ItemManager getItemManager()
//   {
//       return m_ItemManager;
//   }

//   public OrderManager getOrderManager()
//   {
//       return m_OrderManager;
//   }

    public void loadSuperDuperDataFromXml(String i_PathToFile) throws Exception {
        JaxbConverter jaxbConverter = JaxbConverterFactory.create(JaxbConverterFactory.JaxbConverterType.XML);
        jaxbConverter.loadJaxbData(i_PathToFile);
        m_ItemManager = new ItemManager(jaxbConverter.getItems());
        m_StoreManager = new StoreManager(jaxbConverter.getStores());
        initializeStorageItems();
    }

    private void initializeStorageItems() {
        for(Integer itemID: m_ItemManager.getAllItemsId()){
            m_ItemManager.setNewStoreSellItForStorageItem(itemID, m_StoreManager.howManyStoreSellTheInputItem(itemID));
            m_ItemManager.setNewAvgPriceForStorageItem(itemID, m_StoreManager.getItemAvgPrice(itemID));
        }
    }

    public Store getStore(Integer i_StoreID){
        return m_StoreManager.getStore(i_StoreID);
    }

    public void setStoreToOrder(Store i_Store){
        m_OrderManager.setStore(i_Store);
    }

    public void setDateToOrder(String i_OrderDate) throws ParseException{
        m_OrderManager.setOrderDate(i_OrderDate);
    }

    public void setCustomerLocationToOrder(int i_X, int i_Y) throws LocationException{
        m_OrderManager.setCustomerLocation(i_X, i_Y);
    }

    public Item getItem(Integer i_ItemID){
        return m_ItemManager.getItem(i_ItemID);
    }

    public void addItemToOrder(Item i_Item, Double i_AmountOfSells) throws Exception {
        m_OrderManager.addItem(i_Item, i_AmountOfSells);
    }

    public void creatNewOrder(){
        m_OrderManager.create();
    }

    public void executeNewOrder(){
        m_OrderManager.executeOrder();

        //Stream??
        for(StoreItem storeItem: m_OrderManager.getCurrentOrder().getStoreItems()){
            m_ItemManager.addStorageItemSales(storeItem.getItemId(), storeItem.getAmountOfSells());
        }

        m_OrderManager.cleanup();
    }

    public Order getCurrentOrder()
    {
       return m_OrderManager.getCurrentOrder();
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