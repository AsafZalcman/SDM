package xml;

import myLocation.LocationException;
import myLocation.LocationManager;
import xml.jaxb.schema.generated.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class XmlStoreValidator implements XmlValidator {
    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {
        Collection<SDMStore> stores = i_SuperDuperMarketDescriptor.getSDMStores().getSDMStore();
        Collection<SDMItem> items = i_SuperDuperMarketDescriptor.getSDMItems().getSDMItem();
        for (SDMStore store : stores
        ) {
            validateStoreLocation(store);
            validateStoreSellsSameItemOnce(store);
            validateStoreSellsExistsItems(store,items);
        }
    }

    private void validateStoreSellsExistsItems(SDMStore i_Store, Collection<SDMItem> i_Items) throws XmlValidatorException {
        for (SDMSell sdmSell : i_Store.getSDMPrices().getSDMSell()
        ) {
            if (!i_Items.stream()
                    .map(SDMItem::getId)
                    .collect(Collectors.toList())
                    .contains(sdmSell.getItemId())) {
                throw new XmlValidatorException("Error: models.Store id " + i_Store.getId() + " sells an item with " + sdmSell.getItemId() + " id, which not exists in the SDM-Items");
            }
        }
    }

    private void validateStoreLocation(SDMStore i_Store) throws XmlValidatorException {
        try {
           LocationManager.isValidLocation(i_Store.getLocation().getX(),i_Store.getLocation().getY());

        } catch (LocationException e) {
            throw new XmlValidatorException("Error: The store named " + i_Store.getName() + " have illegal location (" + i_Store.getLocation().getX() + ","+i_Store.getLocation().getY()+"):" +e.getMessage());
        }
    }

    private void validateStoreSellsSameItemOnce(SDMStore i_Store) throws XmlValidatorException {
        Map<Integer, Integer> idsToAppearsMap = new HashMap<>();
        for (SDMSell sdmSell : i_Store.getSDMPrices().getSDMSell()
        ) {
            if (idsToAppearsMap.getOrDefault(sdmSell.getItemId(), 0) > 0) {
                throw new XmlValidatorException("Error: The store named " + i_Store.getName() + " sells the same item with " + sdmSell.getItemId() + " id more than once");
            }
            idsToAppearsMap.put(sdmSell.getItemId(), 1);
        }
    }
}
