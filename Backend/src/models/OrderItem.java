package models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return m_StoreItem.getItem().equals(orderItem.m_StoreItem.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_StoreItem);
    }
}
