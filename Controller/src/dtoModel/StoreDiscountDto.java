package dtoModel;

import enums.StoreDiscountOperator;
import javafx.util.Pair;
import models.StoreDiscount;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class StoreDiscountDto {

    private final Pair<Integer, Double> m_DiscountCondition;
    private final String m_StoreDiscountOperator;
    private final Collection<StoreOfferDto> m_StoreOfferDtos;
    private final String m_Name;

    public StoreDiscountDto(Pair<Integer, Double> i_discountCondition, String i_storeDiscountOperator, Collection<StoreOfferDto> i_StoreOfferDtos, String i_Name) {
        m_DiscountCondition = i_discountCondition;
        m_StoreDiscountOperator = i_storeDiscountOperator;
        m_StoreOfferDtos = i_StoreOfferDtos;
        m_Name = i_Name;
    }

    public StoreDiscountDto(StoreDiscount i_StoreDiscount) {
        this(new Pair<>(i_StoreDiscount.getDiscountCondition().getItemId(), i_StoreDiscount.getDiscountCondition().getAmount()),
                i_StoreDiscount.getStoreDiscountOperator().getValue(),
                i_StoreDiscount.getItemIdToStoreOfferMap().values().stream()
                        .map(StoreOfferDto::new)
                        .collect(Collectors.toList()), i_StoreDiscount.getName());
    }

    public String getName() {
        return m_Name;
    }

    public Pair<Integer, Double> getDiscountCondition() {
        return m_DiscountCondition;
    }

    public String getStoreDiscountOperator() {
        return m_StoreDiscountOperator;
    }

    public Collection<StoreOfferDto> getStoreOfferDtos() {
        return m_StoreOfferDtos;
    }

    public boolean isOneOfDiscount() {
        return StoreDiscountOperator.ONE_OF.getValue().equals(m_StoreDiscountOperator);
    }

    public boolean isAllOrNothingDiscount() {
        return StoreDiscountOperator.ALL_OR_NOTHING.getValue().equals(m_StoreDiscountOperator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDiscountDto that = (StoreDiscountDto) o;
        return Objects.equals(m_Name, that.m_Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Name);
    }
}
