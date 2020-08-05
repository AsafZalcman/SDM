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

}
