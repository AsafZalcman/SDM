package utils;

public class StorageItem{
    private int m_StoresSellIt;//how many stores sell it
    private double m_AvgPrice;
    private double m_Sales;//how many times this item sold

    public StorageItem(){
        m_StoresSellIt = 0;
        m_AvgPrice = 0;
        m_Sales = 0;
    }

    public int getStoresSellIt() {
        return m_StoresSellIt;
    }

    public double getAvgPrice() {
        return m_AvgPrice;
    }

    public void setStoresSellIt(int i_StoresSellIt) {
        m_StoresSellIt += i_StoresSellIt;
    }

    public void setAvgPrice(double i_AvgPrice) {
        m_AvgPrice += i_AvgPrice;
    }

    public void addSales(double i_Sales) {
        m_Sales += i_Sales;
    }

    public double getSales() {
        return m_Sales;
    }

}
