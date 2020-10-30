package viewModel;

import dtoModel.*;
import enums.PurchaseForm;
import javafx.util.Pair;
import models.*;
import managers.SuperDuperManager;
import myLocation.Location;
import viewModel.utils.StorageOrderUtil;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OrderViewModel {
    private SuperDuperManager m_SuperDuperManager;
    private Map<Integer, Double> m_ItemIdToAmount = new HashMap<>();
    private Map<Integer,Map<StoreDiscountDto, Integer>> m_UsedDiscounts = new HashMap<>();
    private Store m_CurrentStore;
    private LocalDate m_CurrentOrderDate;
    private Integer m_UserID;
    private StorageOrderDto m_CurrentOrder;
    private Location m_Location;
    private String m_ZoneName;
    private Map<Integer,ItemDto> m_AvailableItemsToBuy = new HashMap<>();;
    private Map<Integer,Map<Integer,Double>> m_StoresToItemsMap = new HashMap<>();;
    private  Map<Integer,List<OrderItem>> m_StoresToItemsInDiscounts = new HashMap<>();

    public OrderViewModel() {
        this.m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public final StorageOrderDto getCurrentOrder() {

        return m_CurrentOrder;
    }

 public Collection<ItemDto> getAvailableItemsForOrder() {
     if (m_AvailableItemsToBuy.isEmpty()) {
         List<ItemDto> allItems = m_SuperDuperManager.getAllItems(m_ZoneName).stream().map(ItemDto::new).collect(Collectors.toList());

         if (m_CurrentStore != null) {
             List<ItemDto> availableItemsInStore = m_CurrentStore.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
             for (ItemDto itemDto : allItems
             ) {
                 if (!availableItemsInStore.contains(itemDto)) {
                     availableItemsInStore.add(itemDto);
                 }
             }
             allItems = availableItemsInStore;
         }

         for (ItemDto itemDto : allItems
         ) {
             m_AvailableItemsToBuy.put(itemDto.getId(), itemDto);
         }

     }
     return m_AvailableItemsToBuy.values();
 }

public void setStore(int i_StoreId) {
    m_CurrentStore = m_SuperDuperManager.getStore(m_ZoneName,i_StoreId);
}

public void setZone(String i_ZoneName)
{
    m_ZoneName = i_ZoneName;
}

public void setDate(LocalDate i_Date) {
    m_CurrentOrderDate = i_Date;
}

public void setLocation(Point i_Location)
{
    m_Location=new Location(i_Location.x, i_Location.y);
}

public void setCustomer(int i_CustomerId) {
    m_UserID = i_CustomerId;
}

public void addItemToOrder(Integer i_ItemId, Double i_AmountOfSells) {
    ItemDto item = m_AvailableItemsToBuy.get(i_ItemId);

    if (item == null) {
        throw new IllegalArgumentException("Item with ID: " + i_ItemId + " is not exists");
    } else{
        if (m_CurrentStore != null && m_CurrentStore.getStoreItem(i_ItemId) == null) {
            throw new IllegalArgumentException("The item with ID: " + i_ItemId + " is not for sell in the requested store");
        }
        if (item.getPurchaseForm() == PurchaseForm.QUANTITY) {
            if ((i_AmountOfSells != Math.floor(i_AmountOfSells)) || Double.isInfinite(i_AmountOfSells)) {
                throw new IllegalArgumentException("The item with ID: " + item.getId() + " is sold only in whole numbers");
            }
        }
    }

     m_ItemIdToAmount.put(i_ItemId,i_AmountOfSells);
}

public StorageOrderDto createOrder() {
    StorageOrder storageOrder = m_SuperDuperManager.createOrder(m_ZoneName, m_CurrentOrderDate, m_Location, m_StoresToItemsMap, m_StoresToItemsInDiscounts, m_UserID);
    m_CurrentOrder = new StorageOrderDto(storageOrder, StorageOrderUtil.convertStorageOrderStores(m_ZoneName,storageOrder.getStoresIdToOrder()));
    return m_CurrentOrder;
}

public void executeOrder() {
    m_SuperDuperManager.executeNewOrder(m_ZoneName,m_CurrentOrder.getOrderDto().getId());
    cleanup();
}

//public Collection<StorageOrderDto> getAllOrders() {
//    return SuperDuperManager.getInstance().getAllOrders(m_ZoneName).getStorageOrders().stream()
//            .collect(Collectors.toMap(storageOrder -> storageOrder, storageOrder -> StorageOrderUtil.convertStorageOrderStores(storageOrder.getStoresIdToOrder()))).entrySet().stream().map(entry -> new StorageOrderDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());
//}
//
//public getAllOrdersOfUser()

public void abortOrder() {
        if(m_CurrentOrder!=null) {
            m_SuperDuperManager.abortOrder(m_ZoneName, m_CurrentOrder.getOrderDto().getId());
        }
    cleanup();
}

public void cleanup() {
    m_CurrentStore = null;
    m_CurrentOrder = null;
    m_CurrentOrderDate = null;
    m_ZoneName = null;
    m_UsedDiscounts.clear();
    m_UserID = null;
    m_StoresToItemsInDiscounts.clear();
    m_AvailableItemsToBuy.clear();
    m_ItemIdToAmount.clear();
    m_Location=null;
    m_StoresToItemsMap.clear();

}

public void buyDiscount(int i_StoreId,StoreDiscountDto i_StoreDiscount , Integer i_SelectedItemIdInOffer) {
    Map<StoreDiscountDto, Integer> currentSelectedDiscountsOfStore = m_UsedDiscounts.getOrDefault(i_StoreId, new HashMap<>());
    int currentUsed = currentSelectedDiscountsOfStore.getOrDefault(i_StoreDiscount, 0) + 1;
    currentSelectedDiscountsOfStore.put(i_StoreDiscount, currentUsed);
    m_UsedDiscounts.put(i_StoreId, currentSelectedDiscountsOfStore);
    if (i_StoreDiscount.isOneOfDiscount()) {
        StoreOfferDto selectedStoreOfferDto = i_StoreDiscount.getStoreOfferDtos().stream()
                .filter(storeOfferDto -> storeOfferDto.getItemId() == i_SelectedItemIdInOffer)
                .collect(Collectors.toList()).get(0);
        addItemToDiscountsMap(i_StoreId, new OrderItem(new StoreItem(m_SuperDuperManager.getItem(m_ZoneName, selectedStoreOfferDto.getItemId()), selectedStoreOfferDto.getForAdditional(), selectedStoreOfferDto.getQuantity()), true));
    } else {
        for (StoreOfferDto storeOfferDto : i_StoreDiscount.getStoreOfferDtos()
        ) {
            addItemToDiscountsMap(i_StoreId, new OrderItem(new StoreItem(m_SuperDuperManager.getItem(m_ZoneName, storeOfferDto.getItemId()), storeOfferDto.getForAdditional(), storeOfferDto.getQuantity()), true));
        }
    }
}

private void addItemToDiscountsMap(int i_StoreId,OrderItem i_OrderItem)
{
    List<OrderItem> orderItemsInDiscountOfStore = m_StoresToItemsInDiscounts.getOrDefault(i_StoreId,new ArrayList<>());
    int existCurrentItemIndex = orderItemsInDiscountOfStore.indexOf(i_OrderItem);
    if(existCurrentItemIndex!=-1)
    {
        i_OrderItem.getStoreItem().setAmountOfSell(i_OrderItem.getStoreItem().getAmountOfSells() + orderItemsInDiscountOfStore.get(existCurrentItemIndex).getStoreItem().getAmountOfSells());
        orderItemsInDiscountOfStore.set(existCurrentItemIndex,i_OrderItem);
    }
    else {
        orderItemsInDiscountOfStore.add(i_OrderItem);
    }
    m_StoresToItemsInDiscounts.put(i_StoreId,orderItemsInDiscountOfStore);
}

    public Map<StoreDto, List<StoreDiscountDto>> getAvailableDiscountsForCurrentOrder ()
    {
        if (m_StoresToItemsMap.isEmpty()) {
            initStoresToItemsMap();
        }

        Map<Integer, Double> itemIdToLeftAmount = new HashMap<>(m_ItemIdToAmount);
        for (Map<StoreDiscountDto,Integer> usedDiscounts: m_UsedDiscounts.values()
             ) {
            for (Map.Entry<StoreDiscountDto, Integer> entry : usedDiscounts.entrySet()
            ) {
                Pair<Integer, Double> currentDiscountCondition = entry.getKey().getDiscountCondition();
                int itemId = currentDiscountCondition.getKey();
                double currentUsedAmount = itemIdToLeftAmount.get(itemId);
                //We need to subtract the amount of items which used in discounts from the total sold amount
                itemIdToLeftAmount.put(itemId, currentUsedAmount - (currentDiscountCondition.getValue() * entry.getValue()));
            }
        }


        Map<StoreDto, List<StoreDiscountDto>> res = new HashMap<>();
        List<StoreDiscountDto> currentAvailableDiscounts;
        for (Integer storeID :m_StoresToItemsMap.keySet()
        ) {
            Store store =m_SuperDuperManager.getStore(m_ZoneName,storeID);
            if(store.haveDiscounts())
            {
                currentAvailableDiscounts = store.getDiscounts().stream()
                        .filter(storeDiscount -> storeDiscount.isAvailable(itemIdToLeftAmount))
                        .map(StoreDiscountDto::new)
                        .collect(Collectors.toList());
                if(currentAvailableDiscounts.size()>0) {
                    res.put(new StoreDto(store), currentAvailableDiscounts);
                }
            }

        }

        return res;
    }

    private void initStoresToItemsMap () {
        if (m_CurrentStore != null) {
            m_StoresToItemsMap.put(m_CurrentStore.getId(), m_ItemIdToAmount);
        } else {
            for (Map.Entry<Integer, Double> entry : m_ItemIdToAmount.entrySet()
            ) {
                Store currentStore = m_SuperDuperManager.getCheapestStore(m_ZoneName, entry.getKey());
                m_StoresToItemsMap.computeIfAbsent(currentStore.getId(), k -> new HashMap<>());
                Map<Integer, Double> currentItemsOfStore = m_StoresToItemsMap.get(currentStore.getId());
                currentItemsOfStore.put(entry.getKey(), entry.getValue());
                m_StoresToItemsMap.put(currentStore.getId(), currentItemsOfStore);
            }
        }
    }
}
