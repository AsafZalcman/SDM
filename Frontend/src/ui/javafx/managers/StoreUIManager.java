package ui.javafx.managers;

import dtoModel.StoreDto;
import viewModel.StoreViewModel;

import java.util.Collection;
import java.util.stream.Collectors;

public class StoreUIManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();

    public Collection<String> getStoresNames(){
        return m_StoreViewModel.getAllStores().stream().map(StoreDto::getName).collect(Collectors.toList());
    }

    public Collection<StoreDto> getAllStores(){
        return m_StoreViewModel.getAllStores();
    }
}
