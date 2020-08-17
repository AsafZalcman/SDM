package utils;

import models.*;
import myLocation.LocationException;
import xml.jaxb.JaxbConverter;
import xml.jaxb.JaxbConverterFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private Map<Integer, Item> m_ItemID2Item;
    private Map<Integer, StorageItem> m_ItemID2StorageItem;

    public ItemManager(Collection<Item> i_Items) {
        m_ItemID2Item = new HashMap<>();
        m_ItemID2StorageItem = new HashMap<>();
        for (Item item : i_Items
        ) {
            this.addItem(item);

            m_ItemID2StorageItem.put(item.getId(), new StorageItem(item));

        }
    }

    public void addItem(Item i_Item)
    {
        m_ItemID2Item.put(i_Item.getId(), i_Item);
    }
    public Item getItem(Integer i_Id)
    {
        return m_ItemID2Item.get(i_Id);
    }

    public Collection<Integer> getAllItemsId()
    {
        return m_ItemID2Item.keySet();
    }

    public Collection<Item> getAllItems()
    {
        return m_ItemID2Item.values();
    }

    public Collection<StorageItem> getAllStorageItems(){ return m_ItemID2StorageItem.values();}

    public void setNewStoreSellItForStorageItem(Integer i_ItemID, int i_Value){
        m_ItemID2StorageItem.get(i_ItemID).setStoresSellIt(i_Value);
    }

    public void setNewAvgPriceForStorageItem(Integer i_ItemID, double i_Value){
        m_ItemID2StorageItem.get(i_ItemID).setAvgPrice(i_Value);
    }

    public void addStorageItemSales(Integer i_ItemID, double i_Value){
        m_ItemID2StorageItem.get(i_ItemID).addSales(i_Value);
    }

    public void addStorageItemSellItValue(Integer i_ItemID, int i_Value){
        m_ItemID2StorageItem.get(i_ItemID).addStoreSellIt(i_Value);
    }

    public boolean isItemExist(int i_ItemID){
        return m_ItemID2Item.containsKey(i_ItemID);
    }


}
