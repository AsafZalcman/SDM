package viewModel;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import dtoModel.StoreOfferDto;
import enums.PurchaseForm;
import enums.StoreDiscountOperator;
import javafx.util.Pair;
import managers.SuperDuperManager;
import models.*;
import myLocation.Location;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoreViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public StoreViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<StoreDto> getAllStores(String i_ZoneName)
    {
        return m_SuperDuperManager.getAllStores(i_ZoneName).stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public void addStoreFeedback(String i_ZoneName , int i_StoreId, LocalDate i_Date,int i_Rank, String i_Description, String i_UserName)
    {
        Store.StoreFeedback storeFeedback = new Store.StoreFeedback(i_Rank,i_Description,i_UserName,i_Date);
        m_SuperDuperManager.getStore(i_ZoneName, i_StoreId).addFeedback(storeFeedback);
    }

    public StoreDto getStore(String i_ZoneName , int i_StoreId) {
        return new StoreDto(m_SuperDuperManager.getStore(i_ZoneName, i_StoreId));
    }

    private boolean isStoreIDExistInTheSystem(String i_ZoneName,int i_StoreID) {
        return m_SuperDuperManager.isStoreIDExist(i_ZoneName,i_StoreID);
    }

    public void createNewStore(String i_ZoneName,StoreDto i_StoreDto) throws Exception {
        if(isStoreIDExistInTheSystem(i_ZoneName,i_StoreDto.getId()))
        {
            throw new IllegalArgumentException("Error: The id :\"" + i_StoreDto.getId() +"\" is already occupied by another store");
        }
        m_SuperDuperManager.addNewStore(i_ZoneName,new Store(i_StoreDto.getId(), i_StoreDto.getName(), new Location(i_StoreDto.getLocation().x, i_StoreDto.getLocation().y), i_StoreDto.getPPK(),i_StoreDto.getOwnerName()),   i_StoreDto.getItemsDto().stream()
                .map(itemDto -> new StoreItem(new Item(itemDto.getId(), itemDto.getItemName(), itemDto.getPurchaseForm()), itemDto.getPrice()))
                .collect(Collectors.toList()));
    }

    public ItemDto createNewStoreItem(String i_ZoneName,int i_ItemId, double i_Price)
    {
        return new ItemDto(new StoreItem(m_SuperDuperManager.getItem(i_ZoneName,i_ItemId),i_Price));
    }
}