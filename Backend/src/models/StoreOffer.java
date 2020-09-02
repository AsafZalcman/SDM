package models;

public class StoreOffer {
    private final int m_ItemId;
    private final double m_Quantity;
    private final int m_ForAdditional;

    public StoreOffer(int i_itemId, double i_quantity, int i_forAdditional) {
       if(i_quantity<0)
       {
           throw new IllegalArgumentException("Quantity must be at least 0");
       }
        m_ItemId = i_itemId;
        m_Quantity = i_quantity;
        m_ForAdditional = i_forAdditional;
    }

    public int getItemId() {
        return m_ItemId;
    }

    public double getQuantity() {
        return m_Quantity;
    }

    public int getForAdditional() {
        return m_ForAdditional;
    }
}
