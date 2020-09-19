package viewModel;

import dtoModel.*;
import enums.PurchaseForm;
import enums.StoreDiscountOperator;
import javafx.util.Pair;
import models.*;
import models.StorageOrder;
import managers.SuperDuperManager;
import viewModel.utils.StorageOrderUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderViewModel {
    private SuperDuperManager m_SuperDuperManager;
    private Map<Integer, Double> m_CurrentIdToAmount;
    private Map<StoreDiscountDto, Integer> m_UsedDiscounts = new HashMap<>();
    private Store m_CurrentStore;
    private LocalDate m_CurrentOrderDate;
    private Customer m_CurrentCustomer;

    public OrderViewModel() {
        this.m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public final StorageOrderDto getCurrentOrder() {
        StorageOrder order = m_SuperDuperManager.getCurrentOrder();
        if (order == null) {
            return null;
        }
        return new StorageOrderDto(order, StorageOrderUtil.convertStorageOrderStores(order.getStoresIdToOrder()));
    }

    public void setStore(int i_StoreId) {
        Store store = m_SuperDuperManager.getStore(i_StoreId);
        if (store == null) {
            throw new IllegalArgumentException("Store with ID: " + i_StoreId + " is not exists");
        }
        m_CurrentStore = store;
    }

    public void setDate(LocalDate i_Date) {
        m_CurrentOrderDate = i_Date;
    }

    public void setCustomer(int i_CustomerId) {
        Customer customer = m_SuperDuperManager.getCustomer(i_CustomerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID: " + i_CustomerId + " is not exists");
        }

        m_CurrentCustomer = customer;
    }

    public void addItemToOrder(Integer i_ItemId, Double i_AmountOfSells) {
        Item item = m_SuperDuperManager.getItem(i_ItemId);

        if (item == null) {
            throw new IllegalArgumentException("Item with ID: " + i_ItemId + " is not exists");
        } else if (item.getPurchaseForm() == PurchaseForm.QUANTITY) {
            if ((i_AmountOfSells != Math.floor(i_AmountOfSells)) || Double.isInfinite(i_AmountOfSells)) {
                throw new IllegalArgumentException("The item with ID: " + item.getId() + " is sold only in whole numbers");
            }
        } else if (m_CurrentStore != null && m_CurrentStore.getStoreItem(i_ItemId) == null) {
            throw new IllegalArgumentException("The item with ID: " + i_ItemId + " is not for sell in the requested store");
        }

        if (m_CurrentIdToAmount == null) {
            m_CurrentIdToAmount = new HashMap<>();
        }
         m_CurrentIdToAmount.put(i_ItemId,i_AmountOfSells); // maybe need to think about better solution
        m_SuperDuperManager.addItemToOrder(m_CurrentStore, item, i_AmountOfSells);

    }

    public void createOrder() {
        //      for (Map.Entry<Integer,Double> entry: m_CurrentIdToAmount.entrySet()
        //           ) {
        //          m_SuperDuperManager.addItemToOrder(m_CurrentStore, m_SuperDuperManager.getItem(entry.getKey()), entry.getValue());
        //      }
        m_SuperDuperManager.createNewOrder(m_CurrentCustomer, m_CurrentOrderDate);
        cleanup();
    }

    public void executeOrder() {
        m_SuperDuperManager.executeNewOrder();
    }

    public Collection<StorageOrderDto> getAllOrders() {
        return SuperDuperManager.getInstance().getOrderManager().getStorageOrders().stream()
                .collect(Collectors.toMap(storageOrder -> storageOrder, storageOrder -> StorageOrderUtil.convertStorageOrderStores(storageOrder.getStoresIdToOrder()))).entrySet().stream().map(entry -> new StorageOrderDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    public void abortOrder() {
        cleanup();
        m_SuperDuperManager.abortOrder();
    }

    public void cleanup() {
        m_CurrentStore = null;
        m_CurrentCustomer = null;
        m_CurrentOrderDate = null;
        m_CurrentIdToAmount = null;
        m_UsedDiscounts.clear();
    }

    public void buyDiscount(StoreDiscountDto i_StoreDiscount , Integer i_SelectedItemIdInOffer) {
        int currentUsed = m_UsedDiscounts.getOrDefault(i_StoreDiscount, 0) + 1;
        m_UsedDiscounts.put(i_StoreDiscount, currentUsed);
        if(!i_StoreDiscount.isOneOfDiscount())
        {
            for (StoreOfferDto storeOfferDto:i_StoreDiscount.getStoreOfferDtos()
                 ) {
                m_SuperDuperManager.addItemInDiscountToOrder(i_StoreDiscount.getName(),storeOfferDto.getItemId(),storeOfferDto.getForAdditional(),storeOfferDto.getQuantity());
            }
        }
        else
        {
            StoreOfferDto selectedStoreOfferDto =  i_StoreDiscount.getStoreOfferDtos().stream()
                    .filter(storeOfferDto -> storeOfferDto.getItemId() == i_SelectedItemIdInOffer)
                    .collect(Collectors.toList()).get(0);
            m_SuperDuperManager.addItemInDiscountToOrder(i_StoreDiscount.getName(),selectedStoreOfferDto.getItemId(),selectedStoreOfferDto.getForAdditional(),selectedStoreOfferDto.getQuantity());
        }
    }

    public Map<StoreDto,List<StoreDiscountDto>> getAvailableDiscountsForCurrentOrder()
    {
        Map<Integer,Double> itemIdToLeftAmount = new HashMap<>(m_CurrentIdToAmount);
        for (Map.Entry<StoreDiscountDto,Integer> entry:m_UsedDiscounts.entrySet()
             ) {
            Pair<Integer, Double> currentDiscountCondition = entry.getKey().getDiscountCondition();
            int itemId = currentDiscountCondition.getKey();
            double currentUsedAmount = itemIdToLeftAmount.get(itemId);
            //We need to subtract the amount of items which used in discounts from the total sold amount
            itemIdToLeftAmount.put(itemId, currentUsedAmount -  (currentDiscountCondition.getValue() * entry.getValue()));
        }

        Map<StoreDto,List<StoreDiscountDto>> res = new HashMap<>();
        List<StoreDiscountDto> currentAvailableDiscounts;
        for (Map.Entry<Store,List<StoreDiscount>> entry:  m_SuperDuperManager.getAvailableDiscountsForCurrentOrder().entrySet()
             ) {
            currentAvailableDiscounts = entry.getValue().stream()
                    .filter(storeDiscount -> storeDiscount.isAvailable(itemIdToLeftAmount))
                    .map(StoreDiscountDto::new)
                    .collect(Collectors.toList());
            res.put(new StoreDto(entry.getKey()), currentAvailableDiscounts);
        }

        return res;
    }

    public StoreDto getStore() {
        return m_CurrentStore == null ? null : new StoreDto(m_CurrentStore);
    }
}
