package models;

public class OrderItem {
    private final StoreItem m_StoreItem;
    private final boolean m_FromDiscount;

    public OrderItem(StoreItem i_StoreItem, boolean i_FromDiscount) {
        m_StoreItem = i_StoreItem;
        m_FromDiscount = i_FromDiscount;
    }

    public StoreItem getStoreItem()
    {
        return m_StoreItem;
    }

    public boolean isFromDiscount()
    {
        return  m_FromDiscount;
    }
}
