package dtoModel;

import models.Customer;
import myLocation.Location;

public class CustomerDto {

    private final int m_Id;
    private final String m_Name;
    private final Location m_Location;

    public CustomerDto(int i_Id, String i_Name, Location i_Location) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        this.m_Location = i_Location;
    }

    public CustomerDto(Customer i_Customer) {
        this(i_Customer.getId(),i_Customer.getName(),i_Customer.getLocation());
    }

    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;

    }

    public Location getLocation() {
        return m_Location;
    }
}