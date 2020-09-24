package ui.javafx.managers;

import dtoModel.ItemDto;
import dtoModel.StorageItemDto;
import viewModel.ItemViewModel;

import java.util.Collection;

public class ItemsUIManger {
    private static ItemsUIManger m_Instance = null;
    private ItemViewModel m_ItemViewModel;

    private ItemsUIManger()
    {
        m_ItemViewModel = new ItemViewModel();
    }
    public static ItemsUIManger getInstance() {
        if (m_Instance == null) {
            synchronized (ItemsUIManger.class) {
                if (m_Instance == null) {
                    m_Instance = new ItemsUIManger();
                }
            }
        }
        return m_Instance;
    }

    public Collection<ItemDto> getAllItems() {
       return m_ItemViewModel.getAllItems();
    }

    public Collection<StorageItemDto> getAllStorageItems() {
        return m_ItemViewModel.getAllStorageItems();
    }

    public ItemDto getItemDtoById(int i_Id)
    {
        return m_ItemViewModel.getItemDtoById(i_Id);
    }

  //  public String getAllItemsWithStorePrice(int i_StoreId) {
  //      Map<Integer, ItemDto> idToStoreItemsMap = m_ItemViewModel.getAllItemsOfStore(i_StoreId).stream().collect(Collectors.toMap(ItemDto::getId, itemDto -> itemDto));
  //      Collection<ItemDto> allItems = m_ItemViewModel.getAllItems();
  //      StringBuilder res = new StringBuilder();
  //      ItemDto tempItemDto;
  //      int counter = 1;
  //      System.out.println("List of all items available in the system:");
  //      for (ItemDto itemDto : allItems
  //      ) {
  //          tempItemDto = idToStoreItemsMap.get(itemDto.getId());
  //          if (tempItemDto == null) {
  //              tempItemDto = itemDto;
  //          }
  //          res.append(counter).append(".\n")
  //                  .append(ItemDtoUtils.getBasicItemString(tempItemDto))
  //                  .append("\n")
  //                  .append(ItemDtoUtils.getPriceString(tempItemDto))
  //                  .append("\n");
  //          counter++;
  //      }
  //      return res.toString();
  //  }
}
