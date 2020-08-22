package xml;

import xml.jaxb.schema.generated.SDMStore;
import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class XmlStoresValidator implements XmlValidator {
    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {
         validateUniqueIdForEachStore(i_SuperDuperMarketDescriptor.getSDMStores().getSDMStore());
    }

    private void validateUniqueIdForEachStore(Collection<SDMStore> i_Stores) throws XmlValidatorException {
        Map<Integer,String> shopIdToShopNameMap = new HashMap<>();
        String shopNameByIdInMap;
        for (SDMStore store: i_Stores
             ) {
            shopNameByIdInMap =shopIdToShopNameMap.getOrDefault(store.getId(),null);
            if(shopNameByIdInMap!=null)
            {
                throw new XmlValidatorException("Error: Both i_Stores:" + shopNameByIdInMap + " and " + store.getName() + " have the same id: " + store.getId());
            }
            shopIdToShopNameMap.put(store.getId(),store.getName());
        }
    }
}
