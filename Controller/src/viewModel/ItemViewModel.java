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

    public Collection<ItemDto> getAllItems(String i_ZoneName)
    {
        return m_SuperDuperManager.getAllItems(i_ZoneName).stream().map(ItemDto::new).collect(Collectors.toList());
    }

    public ItemDto getItemDtoById(String i_ZoneName,int i_Id)
    {
       return new ItemDto(m_SuperDuperManager.getItem(i_ZoneName,i_Id));
    }

    public Collection<ItemDto> getAllItemsOfStore(String i_ZoneName,int i_StoreId)
    {
       Store store= m_SuperDuperManager.getStore(i_ZoneName,i_StoreId);
        Collection<ItemDto> res = new ArrayList<>();
        for (StoreItem storeItem:store.getAllItems()
             ) {
            res.add(new ItemDto(storeItem));
        }
        return res;
    }

    public Collection<StorageItemDto> getAllStorageItems(String i_ZoneName){
        return m_SuperDuperManager.getAllStorageItems(i_ZoneName).stream().map(StorageItemDto::new).collect(Collectors.toList());
    }

    public void addNewItemToStore(String i_ZoneName,int i_StoreID, int i_NewItemID, double i_NewItemPrice) throws Exception{
        StoreItem newStoreItem = new StoreItem(m_SuperDuperManager.getItem(i_ZoneName,i_NewItemID), i_NewItemPrice);
        m_SuperDuperManager.insertNewItemToStore(i_ZoneName,i_StoreID, newStoreItem);
    }

 //   public boolean isItemExistInTheSystem(int i_ItemID){
 //       return m_SuperDuperManager.isItemExist(i_ItemID);
 //   }
//
 //   public boolean isStoreItemIDBelongToTheStore(int i_StoreID, int i_ItemID){
 //       return m_SuperDuperManager.isStoreItemBelongToTheStore(i_StoreID, i_ItemID);
 //   }
}