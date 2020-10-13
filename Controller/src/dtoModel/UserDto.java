package dtoModel;

import models.User;
import models.StorageOrder;
import myLocation.Location;
import viewModel.utils.StorageOrderUtil;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private final int m_Id;
    private final String m_Name;
    private final List<StorageOrderDto> m_Orders;

    public UserDto(int i_Id, String i_Name  ,List<StorageOrderDto> i_StorageOrderDtos) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        m_Orders=i_StorageOrderDtos;
    }

    public UserDto(User i_User) {
        this(i_User.getId(),i_User.getName(),i_User.getOrders().stream()
                .map(storageOrder -> new StorageOrderDto(storageOrder, StorageOrderUtil.convertStorageOrderStores(storageOrder.getStoresIdToOrder())))
                .collect(Collectors.toList()));
    }

    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;

    }

    public int getOrdersCount()
    {
        return m_Orders.size();
    }

    public double getAverageOrdersPriceExcludeDelivery()
    {
        return m_Orders.stream().mapToDouble(storageOrderDto -> storageOrderDto.getOrderDto().getTotalItemsPrice()).summaryStatistics().getAverage();
    }

    public double getAverageDeliveryPayment()
    {
        return m_Orders.stream().mapToDouble(storageOrderDto -> storageOrderDto.getOrderDto().getDeliveryPrice()).summaryStatistics().getAverage();
    }
}