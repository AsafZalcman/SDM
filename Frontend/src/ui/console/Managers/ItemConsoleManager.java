package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.StorageItemDto;
import ViewModel.ItemViewModel;
import ui.console.Utils.ItemDtoUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemConsoleManager {

    ItemViewModel m_ItemViewModel = new ItemViewModel();

    public String getAllItems() {
        StringBuilder res = new StringBuilder();
        int counter = 1;
        for (ItemDto itemDto : m_ItemViewModel.getAllItems()
        ) {
            res.append(counter)
                    .append(".\n")
                    .append(ItemDtoUtils.getBasicItemString(itemDto))
                    .append("\n");
            counter++;
        }
        return res.toString();
    }

    public void showAllStorageItems(){
        StringBuilder res = new StringBuilder();
        int counter = 1;
        System.out.println("All items available in the system:");
        for(StorageItemDto storageItemDto: m_ItemViewModel.getAllStorageItems()){
            res.append(counter).append(". ")
                    .append("\n")
                    .append(ItemDtoUtils.getBasicItemString(storageItemDto.getItemDto()))
                    .append("\n")
                    .append("- How many stores sell it: ")
                    .append(storageItemDto.getStoresSellIt())
                    .append("\n")
                    .append("- Average price: ")
                    .append(storageItemDto.getAvgPrice())
                    .append("\n")
                    .append(ItemDtoUtils.getAmountOfSellsString(storageItemDto.getSales()))
                    .append("\n");
            counter++;
        }
        System.out.println(res.toString());
    }

    public String getAllItemsWithStorePrice(int i_StoreId) {
        Map<Integer, ItemDto> idToStoreItemsMap = m_ItemViewModel.getAllItemsOfStore(i_StoreId).stream().collect(Collectors.toMap(ItemDto::getId, itemDto -> itemDto));
        Collection<ItemDto> allItems = m_ItemViewModel.getAllItems();
        StringBuilder res = new StringBuilder();
        ItemDto tempItemDto;
        int counter = 1;
        for (ItemDto itemDto : allItems
        ) {
            tempItemDto = idToStoreItemsMap.get(itemDto.getId());
            if (tempItemDto == null) {
                tempItemDto = itemDto;
            }
            res.append(counter).append(".\n")
                    .append(ItemDtoUtils.getBasicItemString(tempItemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getPriceString(tempItemDto));
            counter++;
        }
        return res.toString();
    }
}
