package ViewModel;

import DtoModel.StoreDto;
import models.Store;
import utils.SuperDuperManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoreViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public StoreViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<StoreDto> getAllStores()
    {
       return SuperDuperManager.getInstance().getStoreManager().getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice){
        SuperDuperManager.getInstance().updateStoreItemPrice(i_StoreID, i_StoreItemID, i_NewPrice);
    }

    public boolean isStoreIDExistInTheSystem(int i_StoreID) {
        return m_SuperDuperManager.isStoreIDExist(i_StoreID);
    }
}


