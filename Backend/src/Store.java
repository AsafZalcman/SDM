import utils.Location;

import java.util.List;
import java.util.Map;

public class Store {
    private int m_StoreID;
    private String m_StoreName;
    private double m_PPK;
    private Location m_Location;
    private Map<StoreItem, Double> m_StoreItem2TotalSoldSoFar;
    private List<Order> m_StoreOrders;
    private double m_TotalDeliveriesPrice;

    public int getM_StoreID() {
        return m_StoreID;
    }

    public String getM_StoreName() {
        return m_StoreName;
    }

    public double getM_PPK() {
        return m_PPK;
    }

    public void setM_StoreID(int m_StoreID) {
        this.m_StoreID = m_StoreID;
    }

    public void setM_StoreName(String m_StoreName) {
        this.m_StoreName = m_StoreName;
    }

    public void setM_PPK(double m_PPK) {
        this.m_PPK = m_PPK;
    }
}
