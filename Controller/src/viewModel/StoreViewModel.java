package viewModel;

import dtoModel.StoreDto;
import dtoModel.StoreOfferDto;
import enums.PurchaseForm;
import enums.StoreDiscountOperator;
import javafx.util.Pair;
import managers.SuperDuperManager;
import models.*;
import myLocation.Location;

import java.util.Collection;
import java.util.stream.Collectors;

public class StoreViewModel {
    private SuperDuperManager m_SuperDuperManager;

    public StoreViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<StoreDto> getAllStores()
    {
       return m_SuperDuperManager.getStoreManager().getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice){
        SuperDuperManager.getInstance().updateStoreItemPrice(i_StoreID, i_StoreItemID, i_NewPrice);
    }

    public void addNewStore(StoreDto i_StoreDto)
    {
return;
    }

    private boolean isStoreIDExistInTheSystem(int i_StoreID) {
        return m_SuperDuperManager.isStoreIDExist(i_StoreID);
    }

    public String getStoreMapToolTip(int i_StoreID) {
        Store store = m_SuperDuperManager.getStore(i_StoreID);
        StringBuilder sb = new StringBuilder();
        sb.append("Location: ").append(store.getLocation()).append("\n")
                .append("ID: ").append(store.getId()).append("\n")
                .append("Name: ").append(store.getStoreName()).append("\n")
                .append("PPK: ").append(store.getPPK()).append("\n")
                .append("Total Orders: ").append(store.getTotalAmountOfOrders()).append("\n");
        return sb.toString();
    }

    public void createNewStore(StoreDto i_StoreDto) throws Exception {
        if(isStoreIDExistInTheSystem(i_StoreDto.getId()))
        {
            throw new IllegalArgumentException("Error: The id :\"" + i_StoreDto.getId() +"\" is already occupied by another store");
        }
        m_SuperDuperManager.addNewStore(new Store(i_StoreDto.getId(), i_StoreDto.getName(), new Location(i_StoreDto.getLocation().x, i_StoreDto.getLocation().y), i_StoreDto.getPPK()),   i_StoreDto.getItemsDto().stream()
                .map(itemDto -> new StoreItem(new Item(itemDto.getId(), itemDto.getItemName(), itemDto.getPurchaseForm()), itemDto.getPrice()))
                .collect(Collectors.toList()));
    }
}


