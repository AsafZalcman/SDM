package models;

public class StoreItem {//extends Item {
    private double m_Price;
    private final Integer m_ItemId;
    private double m_AmountOfSells = 0;

    public StoreItem(int i_ItemId, double i_Price) {
        this.m_ItemId = i_ItemId;
        this.m_Price = i_Price;
    }

    public StoreItem(int i_ItemId, double i_Price, double i_AmountOfSells) {
        this(i_ItemId, i_Price);
        m_AmountOfSells = i_AmountOfSells;
    }

    public StoreItem(StoreItem i_StoreItem) {
        this(i_StoreItem.getItemId(), i_StoreItem.getPrice(), i_StoreItem.getAmountOfSells());
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

    public double getAmountOfSells() {
        return m_AmountOfSells;
    }

    public void addAmountOfSells(double i_AmountOfSells) {
        this.m_AmountOfSells += i_AmountOfSells;
    }

    public void setAmountOfSell(double i_AmountOfSells) {
        m_AmountOfSells = i_AmountOfSells;
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