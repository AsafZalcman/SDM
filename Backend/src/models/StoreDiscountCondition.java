package models;

public class StoreDiscountCondition
{
    private final int m_ItemId;
    private final double m_Amount;

    public StoreDiscountCondition(int i_itemId, double i_amount) {
        if(i_amount<=0)
        {
            throw new IllegalArgumentException("The amount of times must be a positive number");
        }
        m_Amount = i_amount;
        m_ItemId = i_itemId;
    }

    public int getItemId() {
        return m_ItemId;
    }

    public double getAmount() {
        return m_Amount;
    }
}