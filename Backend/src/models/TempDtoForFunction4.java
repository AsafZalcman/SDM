package models;

import utils.PurchaseForm;

public class TempDtoForFunction4 {

    private final Integer m_ItemId;
    private final String m_ItemName;
    private final PurchaseForm m_PurchaseForm;
    private final double m_Price;

    public TempDtoForFunction4(Integer i_ItemId, String i_ItemName, PurchaseForm i_PurchaseForm, double i_Price) {
        m_ItemId = i_ItemId;
        m_ItemName = i_ItemName;
        m_PurchaseForm = i_PurchaseForm;
        m_Price = i_Price;
    }

    public Integer getItemId() {
        return m_ItemId;
    }

    public String getItemName() {
        return m_ItemName;
    }

    public PurchaseForm getPurchaseForm() {
        return m_PurchaseForm;
    }

    public Double getPrice() {
        return m_Price;
    }
}
