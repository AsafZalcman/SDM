import utils.Location;

import java.util.Date;
import java.util.Map;

public class Order {
    private Date m_OrderDate;
    private Location m_CustomerLocation;
    private double m_DeliveryPrice;
    private double m_TotalItemsPrice;
    private double m_TotalOrderPrice;
    private Map<StoreItem, Double> m_OrderItem2Amount;

}
