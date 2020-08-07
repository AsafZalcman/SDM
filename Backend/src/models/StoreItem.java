package models;

import xml.jaxb.schema.generated.SDMSell;

public class StoreItem {//extends Item {
    private double m_Price;
    private final Integer m_ItemId;
    private int m_AmountOfSells = 0;

    public StoreItem(double i_Price, int i_ItemId) {
        this.m_Price = i_Price;
        this.m_ItemId = i_ItemId;
    }

    public StoreItem(SDMSell i_JaxbStoreItem) {
        m_Price = i_JaxbStoreItem.getPrice();
        m_ItemId = i_JaxbStoreItem.getItemId();
    }

    public double getPrice() {
        return m_Price;
    }

    public void setPrice(double i_Price) {
        this.m_Price = i_Price;
    }

    public int getItemId() {
        return m_ItemId;
    }

    public int getAmountOfSells() {
        return m_AmountOfSells;
    }

    public void sold() {
        this.m_AmountOfSells++;
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "Id=" + m_ItemId +
                ",price=" + m_Price +
                ",AmountOfSells=" + m_AmountOfSells +
                '}';
    }
}