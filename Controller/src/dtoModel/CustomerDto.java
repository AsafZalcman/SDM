package dtoModel;

import models.Customer;
import models.StorageOrder;
import myLocation.Location;
import viewModel.utils.StorageOrderUtil;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDto {

    private final int m_Id;
    private final String m_Name;
    private final Location m_Location;
    private final List<StorageOrderDto> m_Orders;

    public CustomerDto(int i_Id, String i_Name, Location i_Location,List<StorageOrderDto> i_StorageOrderDtos) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        this.m_Location = i_Location;
        m_Orders=i_StorageOrderDtos;
    }

    public CustomerDto(Customer i_Customer) {
        this(i_Customer.getId(),i_Customer.getName(),i_Customer.getLocation(),i_Customer.getOrders().stream()
                .map(storageOrder -> new StorageOrderDto(storageOrder, StorageOrderUtil.convertStorageOrderStores(storageOrder.getStoresIdToOrder())))
                .collect(Collectors.toList()));
    }

    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;

    }

    public Point getLocation() {
        return m_Location;
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