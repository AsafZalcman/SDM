package ui.javafx.managers;

import dtoModel.CustomerDto;
import dtoModel.StorageOrderDto;
import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;
import managers.SuperDuperManager;
import viewModel.OrderViewModel;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersUIManager {

    private static OrdersUIManager m_Instance = null;

    private OrderViewModel m_OrderViewModel;
    private OrdersUIManager()
    {
        m_OrderViewModel = new OrderViewModel();
    }
    public static OrdersUIManager getInstance() {
        if (m_Instance == null) {
            synchronized (OrdersUIManager.class) {
                if (m_Instance == null) {
                    m_Instance = new OrdersUIManager();
                }
            }
        }
        return m_Instance;
    }
    public void executeOrder()
    {
        m_OrderViewModel.executeOrder();
    }

    public void cleanLastOrder()
    {
        m_OrderViewModel.abortOrder();
    }

    public void addItemToOrder(Integer i_ItemId, double i_AmountToAdd) {
        m_OrderViewModel.addItemToOrder(i_ItemId,i_AmountToAdd);
    }

    public void setStore(StoreDto i_StoreDto) {
        if(i_StoreDto!=null) {
           setStore(i_StoreDto.getId());
        }
    }

    public void setStore(int i_StoreId) {
        m_OrderViewModel.setStore(i_StoreId);
    }

    public void setDate(LocalDate i_Date) {
        m_OrderViewModel.setDate(i_Date);
    }

    public void setCustomer(CustomerDto i_Customer) {
        if(i_Customer!=null)
        setCustomer(i_Customer.getId());
    }
    public void setCustomer(int i_CustomerId) {
        m_OrderViewModel.setCustomer(i_CustomerId);
    }

    public void createOrder() {
        m_OrderViewModel.createOrder();
    }

    public StorageOrderDto getCurrentOrder()
    {
       return m_OrderViewModel.getCurrentOrder();
    }

    public Collection<StoreDiscountDto> getAllAvailableDiscountsOfStore(StoreDto i_Store) {
        return m_OrderViewModel.getAvailableDiscountsForCurrentOrder().entrySet().stream()
                .filter(storeDtoListEntry -> storeDtoListEntry.getKey().getId() == i_Store.getId())
                .map(Map.Entry::getValue)
                .findFirst().orElse(Collections.emptyList());
    }

    public Collection<StoreDto> getAllStoresWithAvailableDiscount()
    {
        return m_OrderViewModel.getAvailableDiscountsForCurrentOrder().entrySet().stream()
                .filter(storeDtoListEntry -> !storeDtoListEntry.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public StoreDto getStore() {
      return  m_OrderViewModel.getStore();
    }

    public void addDiscountToOrder(StoreDiscountDto storeDiscountDto)
    {
        m_OrderViewModel.buyDiscount(storeDiscountDto,null);
    }

    public void addOneOfDiscountToOrder(StoreDiscountDto storeDiscountDto, Integer i_SelectedItemIdInOffer)
    {
        m_OrderViewModel.buyDiscount(storeDiscountDto,i_SelectedItemIdInOffer);
    }

    public Collection<StorageOrderDto> getAllOrders() {
        return m_OrderViewModel.getAllOrders();
    }
}
