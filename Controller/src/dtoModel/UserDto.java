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

    public UserDto(int i_Id, String i_Name) {
        this.m_Id = i_Id;
        this.m_Name = i_Name;
    }

    public UserDto(User i_User) {
        this(i_User.getId(),i_User.getName());
    }

    public Integer getId() {
        return m_Id;
    }

    public String getName() {
        return m_Name;
    }
}