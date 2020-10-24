package models;

import enums.UserType;
import interfaces.IUniquely;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements IUniquely {

    private UserType m_UserType;
    private final int m_Id;
    private final String m_Name;

    public User(int i_Id, String i_Name, UserType i_UserType) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        this.m_UserType = i_UserType;
    }

    @Override
    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;
    }

    public boolean isCustomer()
    {
        return m_UserType == UserType.CUSTOMER;
    }

    public UserType getUserType() {
        return m_UserType;
    }
}