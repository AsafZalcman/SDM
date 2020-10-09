package xml;


import xml.jaxb.schema.generated.*;

import java.util.Collection;
import java.util.stream.Collectors;


public class XmlDiscountValidator implements XmlValidator{
    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {
        Collection<SDMStore> stores = i_SuperDuperMarketDescriptor.getSDMStores().getSDMStore();
        validateDiscountItemsForEachStore(stores);
    }

    private void validateDiscountItemsForEachStore(Collection<SDMStore> i_Stores) throws XmlValidatorException {
        for(SDMStore store: i_Stores){
            Collection<SDMSell> storeItems = store.getSDMPrices().getSDMSell();
            SDMDiscounts sdmDiscounts = store.getSDMDiscounts();
            if(sdmDiscounts == null){
                continue;
            }
            else {
                Collection<SDMDiscount> storeDiscounts = sdmDiscounts.getSDMDiscount();

                for (SDMDiscount discount : storeDiscounts) {
                    if (!storeItems.stream().map(SDMSell::getItemId).collect(Collectors.toList()).contains(discount.getIfYouBuy().getItemId())) {
                        throw new XmlValidatorException("The store with the ID: " + store.getId() + " has discount with item ID: " + discount.getIfYouBuy().getItemId() + " but this store does not sell this item");
                    }
                    for (SDMOffer offer : discount.getThenYouGet().getSDMOffer()) {
                        if (!storeItems.stream().map(SDMSell::getItemId).collect(Collectors.toList()).contains(offer.getItemId())) {
                            throw new XmlValidatorException("The store with the ID: " + store.getId() + " has discount with item ID: " + offer.getItemId() + " but this store does not sell this item");
                        }
                    }
                }
            }
        }
    }
}
