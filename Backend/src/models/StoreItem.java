package models;

public class StoreItem {//extends Item {
    private double m_Price;
    private final Item m_Item;
    private double m_AmountOfSells = 0;

    public StoreItem(Item i_Item, double i_Price) {
        this.m_Item = i_Item;
        this.m_Price = i_Price;
    }

    public StoreItem(Item i_Item, double i_Price, double i_AmountOfSells) {
        this(i_Item, i_Price);
        m_AmountOfSells = i_AmountOfSells;
    }

    public StoreItem(StoreItem i_StoreItem) {
        this(i_StoreItem.getItem(), i_StoreItem.getPrice(), i_StoreItem.getAmountOfSells());
    }

    public double getPrice() {
        return m_Price;
    }

    public void setPrice(double i_Price) {
        this.m_Price = i_Price;
    }

    public Item getItem() {
        return m_Item;
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
                m_Item.toString()+
                ",price=" + m_Price +
                ",AmountOfSells=" + m_AmountOfSells +
                '}';
    }
}