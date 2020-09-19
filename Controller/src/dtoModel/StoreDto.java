package dtoModel;

import models.Store;
import myLocation.Location;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
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


    public StoreDto(Store i_Store) {
        this(i_Store.getId(), i_Store.getStoreName(), i_Store.getPPK(), i_Store.getLocation(), i_Store.getAllItems().stream().map(ItemDto::new).collect(Collectors.toList()));
        m_OrdersDto = i_Store.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());

        if (!m_OrdersDto.isEmpty()) {
            m_DeliveriesIncome = m_OrdersDto.stream().mapToDouble(OrderDto::getDeliveryPrice).sum();
        }

        if (i_Store.getDiscounts() != null) {
            m_Discounts = i_Store.getDiscounts().stream()
                    .map(StoreDiscountDto::new)
                    .collect(Collectors.toList());
        }
    }

    public StoreDto(int i_Id,String i_Name,double i_PPK,Point i_Location,Collection<ItemDto> i_Items)
    {
        this(i_Id,i_Name,i_PPK,i_Location,i_Items,Collections.emptyList());
    }

    public StoreDto(int i_Id,String i_Name,double i_PPK,Point i_Location,Collection<ItemDto> i_Items,Collection<StoreDiscountDto> i_Discounts)
    {
        m_Id=i_Id;
        m_Name=i_Name;
        m_PPK=i_PPK;
        m_Location=new Location(i_Location.x,i_Location.y);
        m_Items=i_Items;
        m_Discounts=i_Discounts;
        m_DeliveriesIncome = 0.0;
        m_OrdersDto=Collections.emptyList();
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
}
