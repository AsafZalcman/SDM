package xml;

import xml.jaxb.schema.generated.SDMItem;
import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//think about generics with stores
public class XmlItemsValidator implements XmlValidator {
    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {
        validateUniqueIdForEachItem(i_SuperDuperMarketDescriptor.getSDMItems().getSDMItem());
    }

    private void validateUniqueIdForEachItem(Collection<SDMItem> i_Items) throws XmlValidatorException {
        Map<Integer,String> idsMap = new HashMap<>();
        String itemNameByIdInMap;
        for (SDMItem item: i_Items
        ) {
            itemNameByIdInMap =idsMap.getOrDefault(item.getId(),null);
            if(itemNameByIdInMap!=null)
            {
                throw new XmlValidatorException("Error: Both Items:" + itemNameByIdInMap + " and " + item.getName() + " have the same id: " + item.getId());
            }
            idsMap.put(item.getId(),item.getName());
        }
    }
}
