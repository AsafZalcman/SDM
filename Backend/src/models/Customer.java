package models;

import interfaces.ILocationable;
import interfaces.IUniquely;
import myLocation.Location;

public class Customer implements IUniquely, ILocationable {

    private final int m_Id;
    private final String m_Name;
    private final Location m_Location;

    public Customer(int i_Id, String i_Name, Location i_Location) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        this.m_Location = i_Location;
    }

    @Override
    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;

    }

    @Override
    public Location getLocation() {
        return m_Location;
    }
}