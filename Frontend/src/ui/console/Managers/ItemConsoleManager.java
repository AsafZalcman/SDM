package ui.console.Managers;

import DtoModel.ItemDto;
import ViewModel.ItemViewModel;

import java.util.Collection;
import java.util.Collections;

public class ItemConsoleManager {

    ItemViewModel m_ItemViewModel = new ItemViewModel();

 //  public String getAllItems()
 //  {
 //      StringBuilder res = new StringBuilder();
 //      int counter =1;
 //      for (ItemDto itemDto: m_ItemViewModel.getAllItems()
 //      ) {
 //          res.append(counter + ". " + getBasicDataFromItem(itemDto)+"\n");
 //          counter++;
 //      }
 //      return res.toString();
 //  }

    //bad name
    public String getAllItems(int i_StoreId) {
        Collection<ItemDto> storeItems = m_ItemViewModel.getAllItemsOfStore(i_StoreId);
        Collection<ItemDto> allItems = m_ItemViewModel.getAllItems();
        StringBuilder res = new StringBuilder();
        int counter = 1;
        for (ItemDto itemDto : storeItems
        ) {
            res.append(counter).append(". ").append(getBasicDataFromItem(itemDto)).append("\n");
            counter++;
        }

        for (ItemDto itemDto : allItems
        ) {
            if (!storeItems.contains(itemDto)) {
                res.append(counter).append(". ").append(getBasicDataFromItem(itemDto)).append("\n");
                counter++;
            }
        }
        return res.toString();
    }

    public String getBasicDataFromItem(ItemDto i_ItemDto)
    {
        String priceStr=i_ItemDto.getPrice() !=null? " Price:"+ i_ItemDto.getPrice() :" Price: not for sell";
        return "Id:" +i_ItemDto.getId() + " Name:" + i_ItemDto.getItemName() + " Purchase from:" + i_ItemDto.getPurchaseForm() + priceStr;
    }
}
