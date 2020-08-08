package models;

import interfaces.IUniquely;
import utils.PurchaseForm;

public class Item implements IUniquely {
    private final Integer m_ItemId;
    private final String m_ItemName;
    private final PurchaseForm m_PurchaseForm;

    public Item(int i_ItemId, String i_ItemName, PurchaseForm i_PurchaseForm) {
        this.m_ItemId = i_ItemId;
        this.m_ItemName = i_ItemName;
        this.m_PurchaseForm = i_PurchaseForm;
    }

    @Override
    public Integer getId() {
        return m_ItemId;
    }

    public String getItemName() {
        return m_ItemName;
    }

    public PurchaseForm getPurchaseForm() {
        return m_PurchaseForm;
    }

    @Override
    public String toString() {
        return "Item{" +
                "m_ItemID=" + m_ItemId +
                ", m_ItemName='" + m_ItemName + '\'' +
                ", m_PurchaseForm=" + m_PurchaseForm +
                '}';
    }
}
