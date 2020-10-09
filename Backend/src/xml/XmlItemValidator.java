package xml;

import xml.jaxb.schema.generated.* ;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class XmlItemValidator implements XmlValidator {
    private Collection<SDMStore> stores;
    private Collection<SDMItem> items;

    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {

        validateEachItemForSell(i_SuperDuperMarketDescriptor.getSDMStores().getSDMStore(), i_SuperDuperMarketDescriptor.getSDMItems().getSDMItem());
    }

    private void validateEachItemForSell(Collection<SDMStore> i_Stores, Collection<SDMItem> i_Items) throws XmlValidatorException {
        this.stores = i_Stores;
        this.items = i_Items;
        for (SDMItem item : i_Items
        ) {
            if (!i_Stores.stream()
                    .map(SDMStore::getSDMPrices)
                    .map(SDMPrices::getSDMSell)
                    .flatMap(List::stream)
                    .map(SDMSell::getItemId)
                    .collect(Collectors.toList())
                    .contains(item.getId()))
                throw new XmlValidatorException("The item with ID: " + item.getId() + " is not for sell in any store");
        }
    }
}

