package ViewModel;

import DtoModel.StoreDto;
import models.Store;
import utils.SuperDuperManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoreViewModel {

    public Collection<StoreDto> getAllStores()
    {
       return SuperDuperManager.getInstance().getStoreManager().getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }
}


