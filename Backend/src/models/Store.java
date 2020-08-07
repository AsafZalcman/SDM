package models;

import interfaces.IDelivery;
import interfaces.IUniquely;
import myLocation.Location;
import xml.jaxb.schema.generated.SDMSell;
import xml.jaxb.schema.generated.SDMStore;

import java.util.*;

public class Store implements IDelivery, IUniquely {
    private final Integer m_StoreID;
    private final String m_StoreName;
    private double m_PPK;
    private final Location m_Location;
    private Map<Integer, StoreItem> m_IdToStoreItem;
    private List<Order> m_StoreOrders;

    public Store(int i_StoreID, String i_StoreName, Location i_Location, Collection<StoreItem> i_Items) {
        m_StoreID = i_StoreID;
        m_StoreName = i_StoreName;
        m_Location = i_Location;
        createIdToStoreMapFromCollection(i_Items);
        m_StoreOrders=new ArrayList<>();
    }

    public Store(int i_StoreID, String i_StoreName, Location i_Location,double i_PPK) {
        this.m_StoreID = i_StoreID;
        this.m_StoreName = i_StoreName;
        this.m_Location = i_Location;
        this.m_PPK=i_PPK;
        m_IdToStoreItem = new HashMap<>();
        m_StoreOrders=new ArrayList<>();
    }

    public Store(SDMStore i_JaxbStore) {
        this(i_JaxbStore.getId(), i_JaxbStore.getName(), new Location(i_JaxbStore.getLocation()),i_JaxbStore.getDeliveryPpk());
        List<StoreItem> storeItems = new ArrayList<>(i_JaxbStore.getSDMPrices().getSDMSell().size());
        for (SDMSell sdmSell : i_JaxbStore.getSDMPrices().getSDMSell()
        ) {
            storeItems.add(new StoreItem(sdmSell));
        }

        createIdToStoreMapFromCollection(storeItems);
    }

    private void createIdToStoreMapFromCollection(Collection<StoreItem> i_Items) {
        for (StoreItem storeItem : i_Items
        ) {
            m_IdToStoreItem.put(storeItem.getItemId(), storeItem);
        }
    }

    public String getStoreName() {
        return m_StoreName;
    }

    public double getPPK() {
        return m_PPK;
    }

    public void setPPK(double m_PPK) {
        this.m_PPK = m_PPK;
    }

    @Override
    public Collection<Object> getDetails() {
        List<Object> details = new ArrayList<>();
        details.add(this.m_StoreID);
        details.add(this.m_StoreName);
        details.add(this.m_PPK);
        return details;
    }

    @Override
    public double getDeliveryPrice(Location i_DestLocation) {
        return this.m_Location.distance(i_DestLocation) * m_PPK;
    }

    @Override
    public Integer getId() {
        return m_StoreID;
    }

    @Override
    public String toString() {

        StringBuilder itemsString = new StringBuilder();
        for (StoreItem item: m_IdToStoreItem.values()
             ) {
            itemsString.append(item.toString());
        }
        return "Store{" +
                "m_StoreID=" + m_StoreID +
                ", m_StoreName='" + m_StoreName + '\'' +
                ", m_PPK=" + m_PPK +
                ", m_Location=" + m_Location +
                ", m_IdToStoreItem=" + itemsString +
                '}';
    }
}
