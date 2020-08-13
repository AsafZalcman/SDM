package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.StorageItemDto;
import ViewModel.ItemViewModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemConsoleManager {

    ItemViewModel m_ItemViewModel = new ItemViewModel();

    public String getAllItems()
    {
        StringBuilder res = new StringBuilder();
        int counter =1;
        for (ItemDto itemDto: m_ItemViewModel.getAllItems()
        ) {
            res.append(counter + ". " + getBasicDataFromItem(itemDto) +"\n");
            counter++;
        }
        return res.toString();
    }

    public void showAllStorageItems(){
        StringBuilder res = new StringBuilder();
        int counter = 1;
        System.out.println("All items available in the system:");
        for(StorageItemDto storageItemDto: m_ItemViewModel.getAllStorageItems()){
            res.append(counter + ". " + "\n" +
                   "- ID: " + storageItemDto.getItemDto().getId() + "\n" +
                   "- Name: " + storageItemDto.getItemDto().getItemName() + "\n" +
                    "- Item Purchase Form: " + storageItemDto.getItemDto().getPurchaseForm() + "\n" +
                    "- How many stores sell it: " + storageItemDto.getStoresSellIt() + "\n" +
                    "- Average price: " + storageItemDto.getAvgPrice() + "\n" +
                    "- Sold so far: " + storageItemDto.getSales() + "\n" );
            counter++;
        }
        System.out.println(res.toString());
    }

    //bad name
    public String getAllItemsWithPrice(int i_StoreId) {
        Collection<ItemDto> storeItems = m_ItemViewModel.getAllItemsOfStore(i_StoreId);
        Collection<ItemDto> allItems = m_ItemViewModel.getAllItems();
        StringBuilder res = new StringBuilder();
        Map<Integer,ItemDto> idToItemDtoMap= new HashMap<>(storeItems.size());
        int counter = 1;
        for (ItemDto itemDto : storeItems
        ) {
            res.append(counter).append(". ").append(getBasicDataFromItem(itemDto)).append("\n").append(getPriceField(itemDto));
            counter++;
            idToItemDtoMap.put(itemDto.getId(),itemDto);
        }

        for (ItemDto itemDto : allItems
        ) {
            if (idToItemDtoMap.get(itemDto.getId())!=null) {
                res.append(counter).append(". ").append(getBasicDataFromItem(itemDto)).append("\n").append(getPriceField(itemDto));
                counter++;
            }
        }
        return res.toString();
    }

    public String getBasicDataFromItem(ItemDto i_ItemDto)
    {
        return "Id:" +i_ItemDto.getId() + "\nName:" + i_ItemDto.getItemName() + "\nPurchase from:" + i_ItemDto.getPurchaseForm();
    }

    public String getPriceField(ItemDto i_ItemDto)
    {
        return i_ItemDto.getPrice() !=null? "Price:"+ i_ItemDto.getPrice() :" Price: not for sell";
    }
}
