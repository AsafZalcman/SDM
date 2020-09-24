package ui.javafx.managers;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import javafx.scene.control.Tooltip;
import viewModel.ItemViewModel;
import viewModel.OrderViewModel;
import viewModel.StoreViewModel;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class StoreUIManager {

    private static StoreUIManager m_Instance = null;
    private StoreViewModel m_StoreViewModel;
    private ItemViewModel m_ItemViewModel;

    private StoreUIManager()
    {
        m_StoreViewModel = new StoreViewModel();
        m_ItemViewModel = new ItemViewModel();
    }
    public static StoreUIManager getInstance() {
        if (m_Instance == null) {
            synchronized (StoreUIManager.class) {
                if (m_Instance == null) {
                    m_Instance = new StoreUIManager();
                }
            }
        }
        return m_Instance;
    }

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

    public Tooltip getStoreMapToolTip(int i_StoreID) {
        return new Tooltip(m_StoreViewModel.getStoreMapToolTip(i_StoreID));
    }

    public void createNewStore(int i_Id, String i_Name, Point i_Location, double i_PPK, Collection<ItemDto> i_Items) throws Exception {
        m_StoreViewModel.createNewStore(new StoreDto(i_Id, i_Name, i_PPK, i_Location, i_Items));
    }
}
