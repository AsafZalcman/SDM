package dtoModel;

import models.StorageItem;

public class StorageItemDto{
    private ItemDto m_ItemDto;
    private long m_StoresSellIt;
    private double m_AvgPrice;
    private double m_Sales;

    public StorageItemDto(ItemDto i_ItemDto, long i_StoresSellIt, double i_AvgPrice, double i_Sales) {
        this.m_ItemDto = i_ItemDto;
        this.m_StoresSellIt = i_StoresSellIt;
        this.m_AvgPrice = i_AvgPrice;
        this.m_Sales = i_Sales;
    }

    public StorageItemDto(StorageItem i_StorageItem){
        this.m_ItemDto = new ItemDto(i_StorageItem.getItem());
        this.m_StoresSellIt = i_StorageItem.getStoresSellIt();
        this.m_AvgPrice = i_StorageItem.getAvgPrice();
        this.m_Sales = i_StorageItem.getSales();
    }

    public ItemDto getItemDto() {
        return m_ItemDto;
    }

    public long getStoresSellIt() {
        return m_StoresSellIt;
    }

    public double getAvgPrice() {
        return m_AvgPrice;
    }

    public double getSales() {
        return m_Sales;
    }
}
