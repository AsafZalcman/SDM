package dtoModel;

import models.Item;
import models.OrderItem;
import models.StoreItem;
import enums.PurchaseForm;

import java.util.Objects;

public class ItemDto {
    private  Integer m_ItemId;
    private  String m_ItemName;
    private  PurchaseForm m_PurchaseForm;
    private  Double m_Price;
    private  Double m_AmountOfSell;
    private final boolean m_FromDiscount;

    public ItemDto(int i_ItemId, String i_ItemName, PurchaseForm i_PurchaseForm, Double i_Price, Double i_AmountOfSell , boolean i_FromDiscount) {
        this.m_ItemId = i_ItemId;
        this.m_ItemName = i_ItemName;
        this.m_PurchaseForm = i_PurchaseForm;
        m_Price = i_Price;
        m_AmountOfSell = i_AmountOfSell;
        m_FromDiscount=i_FromDiscount;
    }

    public ItemDto(Item i_Item) {
     this(i_Item.getId(),i_Item.getItemName(),i_Item.getPurchaseForm(),null,null,false);
    }

    public ItemDto(StoreItem i_StoreItem)
    {
        this(i_StoreItem.getItem().getId(),i_StoreItem.getItem().getItemName(),i_StoreItem.getItem().getPurchaseForm(),i_StoreItem.getPrice(),i_StoreItem.getAmountOfSells(),false);
    }

    public ItemDto (OrderItem i_OrderItem)
    {
        this(i_OrderItem.getStoreItem().getItem().getId(),i_OrderItem.getStoreItem().getItem().getItemName(),i_OrderItem.getStoreItem().getItem().getPurchaseForm(),i_OrderItem.getStoreItem().getPrice(),i_OrderItem.getStoreItem().getAmountOfSells(),i_OrderItem.isFromDiscount());

    }

    public Integer getId() {
        return m_ItemId;
    }

    public String getItemName() {
        return m_ItemName;
    }

    public PurchaseForm getPurchaseForm() {
        return m_PurchaseForm;
    }

    public Double getPrice()
    {
        return m_Price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(m_ItemId, itemDto.m_ItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_ItemId, m_ItemName, m_PurchaseForm, m_Price, m_AmountOfSell);
    }

    public Double getAmountOfSell()
    {
        return m_AmountOfSell;
    }

    public Double getTotalPrice()
    {
        return m_AmountOfSell*m_Price;
    }

    public boolean isFromDiscount()
    {
        return m_FromDiscount;
    }
}