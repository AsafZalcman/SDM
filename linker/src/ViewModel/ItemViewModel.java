package ViewModel;

import DtoModel.ItemDto;
import models.Store;
import models.StoreItem;
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
        Collection<ItemDto> res = new ArrayList<>();
        for (StoreItem storeItem:store.getAllItems()
             ) {
            res.add(new ItemDto(storeItem));
        }
        return res;
    }

}