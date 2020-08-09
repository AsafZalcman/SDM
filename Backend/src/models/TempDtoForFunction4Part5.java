package models;

import myLocation.Location;
import utils.PurchaseForm;

public class TempDtoForFunction4Part5 {

    private final TempDtoForFunction4 m_TempDtoForFunction4;
    private final double m_AmountOfSells;


    public TempDtoForFunction4Part5(TempDtoForFunction4 i_TempDtoForFunction4, double i_AmountOfSells) {
        m_TempDtoForFunction4 = i_TempDtoForFunction4;
        m_AmountOfSells = i_AmountOfSells;
    }

    public Integer getItemId() {
        return m_TempDtoForFunction4.getItemId();
    }

    public String getItemName() {
        return m_TempDtoForFunction4.getItemName();
    }

    public PurchaseForm getPurchaseForm() {
        return m_TempDtoForFunction4.getPurchaseForm();
    }

    public Double getPrice() {
        return m_TempDtoForFunction4.getPrice();
    }

    public double getAmountOfSells() {
        return m_AmountOfSells;
    }

    public double getTotalPrice()
    {
        return  m_AmountOfSells*m_TempDtoForFunction4.getPrice();
    }

    public TempDtoForFunction4 getTempDtoForFunction4() {
        return m_TempDtoForFunction4;
    }
}
