package viewModel;

import dtoModel.ItemDto;
import dtoModel.StorageItemDto;
import models.Store;
import models.StoreItem;
import managers.SuperDuperManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ItemViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public ItemViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<ItemDto> getAllItems()
    {
        return m_SuperDuperManager.getItemManager().getAllItems().stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public ItemDto getItemDtoById(int i_Id)
    {
       return new ItemDto(m_SuperDuperManager.getItem(i_Id));
    }

    public Collection<ItemDto> getAllItemsOfStore(int i_StoreId)
    {
       Store store= m_SuperDuperManager.getStoreManager().getStore(i_StoreId);
        Collection<ItemDto> res = new ArrayList<>();
        for (StoreItem storeItem:store.getAllItems()
             ) {
            res.add(new ItemDto(storeItem));
        }
        return res;
    }

    public Collection<StorageItemDto> getAllStorageItems(){
        return m_SuperDuperManager.getAllStorageItems().stream().map(StorageItemDto::new).collect(Collectors.toList());
    }

    public void addNewItemToStore(int i_StoreID, int i_NewItemID, double i_NewItemPrice) throws Exception{
        StoreItem newStoreItem = new StoreItem(m_SuperDuperManager.getItem(i_NewItemID), i_NewItemPrice);
        m_SuperDuperManager.insertNewItemToStore(i_StoreID, newStoreItem);
    }

    public boolean isItemExistInTheSystem(int i_ItemID){
        return m_SuperDuperManager.isItemExist(i_ItemID);
    }

    public boolean isStoreItemIDBelongToTheStore(int i_StoreID, int i_ItemID){
        return m_SuperDuperManager.isStoreItemBelongToTheStore(i_StoreID, i_ItemID);
    }

    public void deleteItemFromStore(int i_storeID, int i_ItemID) throws Exception {
        m_SuperDuperManager.deleteStoreItem(i_storeID, i_ItemID);
    }
}