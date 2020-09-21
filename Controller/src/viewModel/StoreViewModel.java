package viewModel;

import dtoModel.StoreDto;
import dtoModel.StoreOfferDto;
import enums.StoreDiscountOperator;
import javafx.util.Pair;
import managers.SuperDuperManager;
import models.Store;
import models.StoreDiscount;
import models.StoreDiscountCondition;
import models.StoreOffer;

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

    //  public void addDiscount(int i_StoreId,Pair<Integer,Integer> i_DiscountCondition, String discountOperator , Collection<StoreOfferDto> i_StoreOffers , String i_Name) {
  //      m_SuperDuperManager.getStore(i_StoreId).addDiscount(new StoreDiscount
  //              (new StoreDiscountCondition(i_DiscountCondition.getKey(), i_DiscountCondition.getValue()),
  //                      StoreDiscountOperator.valueOf(discountOperator.toUpperCase()),
  //                      i_StoreOffers.stream().map(storeOfferDto -> new StoreOffer(storeOfferDto.getItemId(), storeOfferDto.getQuantity(), storeOfferDto.getForAdditional()))
  //                              .collect(Collectors.toList())),i_Name);
  //  }
}


