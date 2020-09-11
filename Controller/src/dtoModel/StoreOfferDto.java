package dtoModel;

import models.StoreOffer;

public class StoreOfferDto {

    private final int m_ItemId;
    private final double m_Quantity;
    private final double m_ForAdditional;

    public StoreOfferDto(int i_itemId, double i_quantity, double i_forAdditional) {
        m_ItemId = i_itemId;
        m_Quantity = i_quantity;
        m_ForAdditional = i_forAdditional;
    }

    public StoreOfferDto(StoreOffer i_StoreOffer) {
        this(i_StoreOffer.getItemId(),i_StoreOffer.getQuantity(),i_StoreOffer.getForAdditional());
    }

    public int getItemId() {
        return m_ItemId;
    }

    public double getQuantity() {
        return m_Quantity;
    }

    public double getForAdditional() {
        return m_ForAdditional;
    }
}
