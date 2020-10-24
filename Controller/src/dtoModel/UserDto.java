package dtoModel;

import enums.UserType;
import models.User;
import viewModel.utils.StorageOrderUtil;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private final int m_Id;
    private final String m_Name;
    private final String m_Role;

    public UserDto(int i_Id, String i_Name, String i_Role) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
        m_Role = i_Role;
    }

    public UserDto(User i_User) {
        this(i_User.getId(),i_User.getName(),i_User.getUserType().getValue());
    }

    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;
    }

    public String getRole() {
        return m_Role;
    }

    public boolean isCustomer()
    {
        return m_Role.equalsIgnoreCase(UserType.CUSTOMER.getValue());
    }
}