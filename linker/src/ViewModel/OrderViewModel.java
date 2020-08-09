package ViewModel;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import models.*;
import myLocation.Location;
import myLocation.LocationException;
import utils.OrderManager;
import utils.StorageOrder;
import utils.SuperDuperManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class OrderViewModel {
    public final OrderDto getCurrentOrder()
    {
        OrderManager orderManager = SuperDuperManager.getInstance().getOrderManager();
        if(!orderManager.isOrderCreated())
        {
            throw new NullPointerException("First create the order");
        }
        Order order = orderManager.getCurrentOrder();
        Store store = orderManager.getStore();
        Collection<ItemDto> itemsDto = new ArrayList<>();

        ItemDto itemDto;
        Item currentItem;
        for (StoreItem item: order.getStoreItems()
        ) {
            currentItem = SuperDuperManager.getInstance().getItemManager().getItem(item.getItemId());
             itemDto= new ItemDto(currentItem.getId(),currentItem.getItemName(),currentItem.getPurchaseForm(),item.getPrice(),item.getAmountOfSells());
            itemsDto.add(itemDto);
        }

        return new OrderDto(itemsDto,order.getCustomerLocation(),store.getLocation(),store.getPPK());
    }

    public void setStoreForOrder(int i_StoreId) throws Exception {
        Store store = SuperDuperManager.getInstance().getStoreManager().getStore(i_StoreId);
        if (store == null) {
            throw new Exception("Store with " + i_StoreId + " id is not exists");
        }
        SuperDuperManager.getInstance().getOrderManager().setStore(store);
    }
    public void setDateForOrder(String i_Date) throws ParseException {
        SuperDuperManager.getInstance().getOrderManager().setOrderDate(i_Date);
    }
    public void setLocationForOrder(int x, int y) throws LocationException {
        SuperDuperManager.getInstance().getOrderManager().setCustomerLocation(x, y);
    }
    public void addItemToOrder(Integer itemId,Double amountOfSells) throws Exception {
        Item item = SuperDuperManager.getInstance().getItemManager().getItem(itemId);
        if (item == null) {
            throw new Exception("Item with:" + itemId + " id is not exists");
        }
        SuperDuperManager.getInstance().getOrderManager().addItem(item, amountOfSells);
    }

    public void createOrder() {
        SuperDuperManager.getInstance().getOrderManager().create();
    }

    public void executeOrder()
    {
        SuperDuperManager.getInstance().getOrderManager().executeOrder();
    }

}
