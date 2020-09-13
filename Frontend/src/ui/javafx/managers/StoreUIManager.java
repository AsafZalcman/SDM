package ui.javafx.managers;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import viewModel.ItemViewModel;
import viewModel.StoreViewModel;

import java.util.Collection;
import java.util.stream.Collectors;

public class StoreUIManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();
    private ItemViewModel m_ItemViewModel = new ItemViewModel();

    public Collection<String> getStoresNames(){
        return m_StoreViewModel.getAllStores().stream().map(StoreDto::getName).collect(Collectors.toList());
    }

    public Collection<StoreDto> getAllStores(){
        return m_StoreViewModel.getAllStores();
    }

    public Collection<ItemDto> getAllItemsOfStore(int i_StoreID){
        return m_ItemViewModel.getAllItemsOfStore(i_StoreID);
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice) throws Exception{
        m_StoreViewModel.updateStoreItemPrice(i_StoreID,i_StoreItemID,i_NewPrice);
    }

    public void addNewItemToStore(int i_StoreID, int i_StoreItemID, double i_Price) throws Exception{
        m_ItemViewModel.addNewItemToStore(i_StoreID,i_StoreItemID,i_Price);
    }

    public void deleteItemFromStore(int i_StoreID, int i_StoreItemID) throws Exception {
        m_ItemViewModel.deleteItemFromStore(i_StoreID,i_StoreItemID);
    }
}
