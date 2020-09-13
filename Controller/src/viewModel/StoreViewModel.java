package viewModel;

import dtoModel.StoreDto;
import dtoModel.StoreOfferDto;
import enums.StoreDiscountOperator;
import javafx.util.Pair;
import managers.SuperDuperManager;
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
       return SuperDuperManager.getInstance().getStoreManager().getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    public void updateStoreItemPrice(int i_StoreID, int i_StoreItemID, double i_NewPrice) throws Exception{
        SuperDuperManager.getInstance().updateStoreItemPrice(i_StoreID, i_StoreItemID, i_NewPrice);
    }

    public boolean isStoreIDExistInTheSystem(int i_StoreID) {
        return m_SuperDuperManager.isStoreIDExist(i_StoreID);
    }

  //  public void addDiscount(int i_StoreId,Pair<Integer,Integer> i_DiscountCondition, String discountOperator , Collection<StoreOfferDto> i_StoreOffers , String i_Name) {
  //      m_SuperDuperManager.getStore(i_StoreId).addDiscount(new StoreDiscount
  //              (new StoreDiscountCondition(i_DiscountCondition.getKey(), i_DiscountCondition.getValue()),
  //                      StoreDiscountOperator.valueOf(discountOperator.toUpperCase()),
  //                      i_StoreOffers.stream().map(storeOfferDto -> new StoreOffer(storeOfferDto.getItemId(), storeOfferDto.getQuantity(), storeOfferDto.getForAdditional()))
  //                              .collect(Collectors.toList())),i_Name);
  //  }
}


