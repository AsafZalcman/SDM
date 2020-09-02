package models;

import enums.StoreDiscountOperator;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class StoreDiscount {
    private final StoreDiscountCondition m_DiscountCondition;
    private final StoreDiscountOperator m_StoreDiscountOperator;
    private final Map<Integer,StoreOffer> m_ItemIdToStoreOfferMap;
    private final String m_Name;

    public StoreDiscount(StoreDiscountCondition i_discountCondition, StoreDiscountOperator i_storeDiscountOperator, Map<Integer, StoreOffer> i_itemIdToStoreOfferMap , String i_Name) {
        m_DiscountCondition = i_discountCondition;
        m_StoreDiscountOperator = i_storeDiscountOperator;
        m_ItemIdToStoreOfferMap = i_itemIdToStoreOfferMap;
        m_Name=i_Name;
    }

    public StoreDiscount(StoreDiscountCondition i_discountCondition, StoreDiscountOperator i_storeDiscountOperator, Collection<StoreOffer> i_StoreOffers, String i_Name) {
        this(i_discountCondition, i_storeDiscountOperator, i_StoreOffers.stream()
                .collect(Collectors.toMap(StoreOffer::getItemId, storeOffer -> storeOffer)),i_Name);
    }

    public StoreDiscountCondition getDiscountCondition() {
        return m_DiscountCondition;
    }

    public StoreDiscountOperator getStoreDiscountOperator() {
        return m_StoreDiscountOperator;
    }

    public Map<Integer, StoreOffer> getItemIdToStoreOfferMap() {
        return m_ItemIdToStoreOfferMap;
    }

    public String getName() {
        return m_Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDiscount that = (StoreDiscount) o;
        return Objects.equals(m_Name, that.m_Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Name);
    }
}
