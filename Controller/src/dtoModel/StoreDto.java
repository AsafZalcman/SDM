package dtoModel;

import models.Store;
import myLocation.Location;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class  StoreDto {
    private  Collection<OrderDto> m_OrdersDto;
    private  double m_DeliveriesIncome;
    private final Collection<ItemDto> m_Items;
    private  Collection<StoreDiscountDto> m_Discounts;
    private final Location m_Location;
    private final double m_PPK;
    private final String m_Name;
    private final int m_Id;
    private final String m_OwnerName;
    private  Collection<StoreFeedbackDto> m_StoreFeedbackDtos;


    public StoreDto(Store i_Store) {
        this(i_Store.getId(), i_Store.getStoreName(), i_Store.getPPK(), i_Store.getLocation(), i_Store.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList()),i_Store.getOwnerName());
        m_OrdersDto = i_Store.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
        m_DeliveriesIncome = i_Store.getIncomesFromDeliveries();

        if (i_Store.getDiscounts() != null) {
            m_Discounts = i_Store.getDiscounts().stream()
                    .map(StoreDiscountDto::new)
                    .collect(Collectors.toList());
        }

        m_StoreFeedbackDtos = i_Store.getAllFeedbacks().stream().map(StoreFeedbackDto::new).collect(Collectors.toList());
    }

    public StoreDto(int i_Id,String i_Name,double i_PPK,Point i_Location,Collection<ItemDto> i_Items,String i_OwnerName)
    {
        this(i_Id,i_Name,i_PPK,i_Location,i_Items,Collections.emptyList(),i_OwnerName);
    }

    public StoreDto(int i_Id,String i_Name,double i_PPK,Point i_Location,Collection<ItemDto> i_Items,Collection<StoreDiscountDto> i_Discounts,String i_OwnerName)
    {
        m_Id=i_Id;
        m_Name=i_Name;
        m_PPK=i_PPK;
        m_Location=new Location(i_Location.x,i_Location.y);
        m_Items=i_Items;
        m_Discounts=i_Discounts;
        m_DeliveriesIncome = 0.0;
        m_OrdersDto=Collections.emptyList();
        m_OwnerName =i_OwnerName;
        m_StoreFeedbackDtos = Collections.emptyList();
    }


    public Point getLocation() {
        return m_Location;
    }

    public int getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;
    }

    public double getPPK() {
        return m_PPK;
    }

    public Collection<ItemDto> getItemsDto()
    {
        return m_Items;
    }

    public Double getDeliveriesIncomes() {
        return m_DeliveriesIncome;
    }

    public Collection<OrderDto> getAllOrders() {
        return m_OrdersDto;
    }

    public Collection<StoreDiscountDto> getAllDiscounts()
    {
        return m_Discounts;
    }
    public double getDeliveryPrice(Point i_DestPoint)
    {
        return m_Location.distance(i_DestPoint) * getPPK();
    }

    public String getOwnerName() {
        return m_OwnerName;
    }

    public Collection<StoreFeedbackDto> getFeedbacks()
    {
        return m_StoreFeedbackDtos;
    }

    private class StoreFeedbackDto
    {
        Store.StoreFeedback m_StoreFeedback;
        public StoreFeedbackDto (Store.StoreFeedback i_StoreFeedback)
        {
            m_StoreFeedback=i_StoreFeedback;
        }

        public int getRank() {
            return m_StoreFeedback.getRank();
        }

        public String getDescription() {
            return m_StoreFeedback.getDescription();

        }

        public String getUserName() {
            return m_StoreFeedback.getUserName();

        }

        public LocalDate getDate() {
            return m_StoreFeedback.getDate();
        }
    }

    @Override
    public String toString() {
        return m_Id + "," + m_Name;
    }
}