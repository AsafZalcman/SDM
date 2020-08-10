package DtoModel;

import models.Item;
import utils.PurchaseForm;

import java.util.Objects;

public class ItemDto {
    private  Integer m_ItemId;
    private  String m_ItemName;
    private  PurchaseForm m_PurchaseForm;
    private  Double m_Price;
    private  Double m_AmountOfSell;

    public ItemDto(int i_ItemId, String i_ItemName, PurchaseForm i_PurchaseForm, Double i_Price, Double i_AmountOfSell) {
        this.m_ItemId = i_ItemId;
        this.m_ItemName = i_ItemName;
        this.m_PurchaseForm = i_PurchaseForm;
        m_Price = i_Price;
        m_AmountOfSell = i_AmountOfSell;
    }

    public ItemDto(Item i_Item) {
     this(i_Item.getId(),i_Item.getItemName(),i_Item.getPurchaseForm(),null,null);
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

    public class ItemDtoBuilder
    {
        private  Integer m_Id;
        private  String m_ItemName;
        private  PurchaseForm m_PurchaseForm;
        private  Double m_Price;
        private  Double m_AmountOfSell;

        public ItemDtoBuilder(int i_Id)
        {
            m_Id=i_Id;
        }

        public ItemDtoBuilder setName(String i_Name) {
            this.m_ItemName = i_Name;
            return this;
        }

        public ItemDtoBuilder setPurchaseForm(PurchaseForm i_PurchaseForm) {
            this.m_PurchaseForm = i_PurchaseForm;
            return this;
        }

        public ItemDtoBuilder setPrice(Double i_Price) {
            this.m_Price = i_Price;
            return this;
        }

        public ItemDtoBuilder setAmountOfSell(Double i_AmountOfSell) {
            this.m_AmountOfSell = i_AmountOfSell;
            return this;
        }

        public ItemDto build()
        {
            return new ItemDto(m_ItemId,m_ItemName,m_PurchaseForm,m_Price,m_AmountOfSell);
        }
    }
}