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

    public ItemManager(Collection<Item> i_Items) {
        m_ItemID2Item = new HashMap<>();
        for (Item item : i_Items
        ) {
            this.addItem(item);
        }
    }

    public void addItem(Item i_Item)
    {
        m_ItemID2Item.put(i_Item.getId(), i_Item);
    }
    public Item getItem(int i_Id)
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

}
