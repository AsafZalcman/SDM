package xml.jaxb;

import enums.StoreDiscountOperator;
import models.*;
import myLocation.Location;
import myLocation.LocationManager;
import enums.PurchaseForm;
import xml.jaxb.schema.generated.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class JaxbConverter {
    IJaxbDataLoader m_IJaxbLoader;
    SuperDuperMarketDescriptor m_SuperDuperMarketDescriptor;
    Map<Integer, Item> m_Items;
    Collection<Store> m_Stores;
    private String m_OwnerName;

    public JaxbConverter(IJaxbDataLoader i_IJaxbLoader) {
        m_IJaxbLoader = i_IJaxbLoader;
    }

    public Collection<Store> getStores() {
        if (m_SuperDuperMarketDescriptor == null || m_Items == null) {
            return null;
        }

        if (m_Stores == null) {
            m_Stores = m_SuperDuperMarketDescriptor.getSDMStores().getSDMStore().stream()
                    .map(this::convertToStore)
                    .collect(Collectors.toList());
        }
        return m_Stores;
    }

    public Collection<Item> getItems() {
        if (m_SuperDuperMarketDescriptor == null) {
            return null;
        }

        if (m_Items == null) {
            m_Items = new HashMap<>();
            for (SDMItem sdmItem : m_SuperDuperMarketDescriptor.getSDMItems().getSDMItem()
            ) {
                m_Items.put(sdmItem.getId(), convertToItem(sdmItem));
            }
        }

        return m_Items.values();
    }

    public void loadJaxbData(String i_PathToFile, String i_OwnerName) throws Exception {
        m_SuperDuperMarketDescriptor = m_IJaxbLoader.load(i_PathToFile);
        init(i_OwnerName);
    }

    public void loadJaxbData(InputStream i_FileInputStream, String i_OwnerName) throws Exception {
        m_SuperDuperMarketDescriptor = m_IJaxbLoader.load(i_FileInputStream);
        init(i_OwnerName);
    }

    private void init(String i_OwnerName) {
        m_OwnerName = i_OwnerName;
        m_Items = null;
        m_Stores = null;

    }

    public String getZoneName() {
        return m_SuperDuperMarketDescriptor.getSDMZone().getName();
    }


    private Store convertToStore(SDMStore i_JaxbStore) {
        List<StoreItem> storeItems = new ArrayList<>(i_JaxbStore.getSDMPrices().getSDMSell().size());
        for (SDMSell sdmSell : i_JaxbStore.getSDMPrices().getSDMSell()
        ) {
            storeItems.add(convertToStoreItem(sdmSell));
        }
        Store newStore = new Store(i_JaxbStore.getId(), i_JaxbStore.getName(), new Location(i_JaxbStore.getLocation().getX(), i_JaxbStore.getLocation().getY()), i_JaxbStore.getDeliveryPpk(), storeItems, m_OwnerName);

        if (i_JaxbStore.getSDMDiscounts() != null) {
            for (SDMDiscount sdmDiscount : i_JaxbStore.getSDMDiscounts().getSDMDiscount()
            ) {
                newStore.addDiscount(convertToStoreDiscount(sdmDiscount));
            }
        }
        LocationManager.addLocation(getZoneName(),i_JaxbStore.getLocation().getX(), i_JaxbStore.getLocation().getY());
        return newStore;
    }

    private StoreItem convertToStoreItem(SDMSell i_JaxbStoreItem) {
        return new StoreItem(m_Items.get(i_JaxbStoreItem.getItemId()), i_JaxbStoreItem.getPrice());
    }

    private Item convertToItem(SDMItem i_JaxbItem) {
        return new Item(i_JaxbItem.getId(), i_JaxbItem.getName(), PurchaseForm.valueOf(i_JaxbItem.getPurchaseCategory().toUpperCase()));
    }

    private StoreDiscount convertToStoreDiscount(SDMDiscount i_Discount) {
        StoreDiscountCondition discountCondition = new StoreDiscountCondition(i_Discount.getIfYouBuy().getItemId(), i_Discount.getIfYouBuy().getQuantity());
        String discountOperatorStr = i_Discount.getThenYouGet().getOperator();
        StoreDiscountOperator discountOperator = discountOperatorStr == null ? StoreDiscountOperator.IRRELEVANT : StoreDiscountOperator.valueOf(discountOperatorStr.replaceAll("-", "_"));
        Collection<StoreOffer> storeOffers = i_Discount.getThenYouGet().getSDMOffer().stream()
                .map(sdmOffer -> new StoreOffer(sdmOffer.getItemId(), sdmOffer.getQuantity(), sdmOffer.getForAdditional())).collect(Collectors.toList());
        return new StoreDiscount(discountCondition, discountOperator, storeOffers, i_Discount.getName());
    }
}
