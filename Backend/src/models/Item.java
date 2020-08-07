package models;

import interfaces.IUniquely;
import utils.PurchaseForm;
import xml.jaxb.schema.generated.SDMItem;

public class Item implements IUniquely {
    private final Integer m_ItemID;
    private final String m_ItemName;
    private final PurchaseForm m_PurchaseForm;

    public Item(int i_ItemID, String i_ItemName, PurchaseForm i_PurchaseForm) {
        this.m_ItemID = i_ItemID;
        this.m_ItemName = i_ItemName;
        this.m_PurchaseForm = i_PurchaseForm;
    }

    public Item(SDMItem i_Item) {
        this(i_Item.getId(),i_Item.getName(),PurchaseForm.valueOf(i_Item.getPurchaseCategory().toUpperCase()));
    }

    @Override
    public Integer getId() {
        return m_ItemID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "m_ItemID=" + m_ItemID +
                ", m_ItemName='" + m_ItemName + '\'' +
                ", m_PurchaseForm=" + m_PurchaseForm +
                '}';
    }
}
