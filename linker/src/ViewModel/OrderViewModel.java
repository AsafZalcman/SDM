package ViewModel;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StorageOrderDto;
import models.*;
import myLocation.LocationException;
import utils.SuperDuperManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OrderViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public OrderViewModel() {
        this.m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public final OrderDto getCurrentOrder() {
        Order order = m_SuperDuperManager.getCurrentOrder();
        Collection<ItemDto> itemsDto = new ArrayList<>();
        ItemDto itemDto;
        for (StoreItem item : order.getStoreItems()
        ) {
            itemDto = new ItemDto(item);
            itemsDto.add(itemDto);
        }

        return new OrderDto(order);
    }

    public void setStoreForOrder(int i_StoreId) throws Exception {
        Store store = m_SuperDuperManager.getStore(i_StoreId);
        if (store == null) {
            throw new Exception("Store with " + i_StoreId + " id is not exists");
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
            throw new Exception("Item with:" + itemId + " id is not exists");
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
        return SuperDuperManager.getInstance().getOrderManager().getStorageOrders().stream().map(StorageOrderDto::new).collect(Collectors.toList());
    }
}
