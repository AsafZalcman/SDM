package viewModel;

import dtoModel.OrderDto;
import dtoModel.StorageOrderDto;
import dtoModel.StoreDto;
import models.*;
import myLocation.LocationException;
import models.StorageOrder;
import managers.SuperDuperManager;
import viewModel.utils.StorageOrderUtil;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public OrderViewModel() {
        this.m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public final StorageOrderDto getCurrentOrder() {
        StorageOrder order = m_SuperDuperManager.getCurrentOrder();

        return new StorageOrderDto(order, StorageOrderUtil.convertStorageOrderStores(order.getStoresIdToOrder()));
    }

    public void setStoreForOrder(int i_StoreId) throws Exception {
        Store store = m_SuperDuperManager.getStore(i_StoreId);
        if (store == null) {
            throw new Exception("Store with ID: " + i_StoreId + " is not exists");
        }
        m_SuperDuperManager.setStoreToOrder(store);
    }

    public void setDateForOrder(Date i_Date) {
        m_SuperDuperManager.setDateToOrder(i_Date);
    }

    public void setLocationForOrder(int x, int y) throws LocationException {
        m_SuperDuperManager.setCustomerLocationToOrder(x, y);
    }

    public void addItemToOrder(Integer itemId, Double amountOfSells) throws Exception {
        Item item = m_SuperDuperManager.getItem(itemId);
        if (item == null) {
            throw new Exception("Item with ID: " + itemId + " is not exists");
        }
        m_SuperDuperManager.addItemToOrder(item, amountOfSells);
    }

    public void createOrder() {
        m_SuperDuperManager.createNewOrder();
    }

    public void executeOrder() {
        m_SuperDuperManager.executeNewOrder();
    }
    public Collection<StorageOrderDto> getAllOrders()
    {
        return SuperDuperManager.getInstance().getOrderManager().getStorageOrders().stream()
             .collect(Collectors.toMap(storageOrder -> storageOrder,storageOrder -> StorageOrderUtil.convertStorageOrderStores(storageOrder.getStoresIdToOrder()))).entrySet().stream().map(entry -> new StorageOrderDto(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    public void abortOrder() {
        m_SuperDuperManager.abortOrder();
    }
}
