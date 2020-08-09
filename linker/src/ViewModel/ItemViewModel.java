package ViewModel;

import DtoModel.ItemDto;
import models.Item;
import models.Store;
import models.StoreItem;
import models.TempDtoForFunction4;
import utils.ItemManager;
import utils.PurchaseForm;
import utils.SuperDuperManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ItemViewModel {

    public Collection<ItemDto> getAllItems()
    {
        return SuperDuperManager.getInstance().getItemManager().getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public Collection<ItemDto> getAllItemsOfStore(int i_StoreId)
    {
       Store store= SuperDuperManager.getInstance().getStoreManager().getStore(i_StoreId);
        ItemManager itemManager = SuperDuperManager.getInstance().getItemManager();
        Collection<ItemDto> res = new ArrayList<>();
        Item currentItem;
        for (StoreItem storeItem:store.getAllItems()
             ) {
            currentItem = itemManager.getItem(storeItem.getItemId());
            res.add(new ItemDto(currentItem.getId(),currentItem.getItemName(),currentItem.getPurchaseForm(),storeItem.getPrice(),storeItem.getAmountOfSells()));
        }
        return res;
    }

}