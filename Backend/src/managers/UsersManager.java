package managers;

import enums.UserType;
import models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UsersManager {

    private Map<Integer, User> m_UserIdToUser;

    public UsersManager()
    {
        this(Collections.emptyList());
    }
    public UsersManager(Collection<User> i_Users) {
        m_UserIdToUser = new HashMap<>();
        for (User user : i_Users
        ) {
            this.addUser(user);
        }
    }

    public Collection<Integer> getAllUsersId() {
        return m_UserIdToUser.keySet();
    }

    public Collection<User> getAllUsers() {
        return m_UserIdToUser.values();
    }

    private void addUser(User i_User) {
        m_UserIdToUser.put(i_User.getId(), i_User);
    }

    public int addUser(String i_UserName, UserType i_UserType) throws Exception {
        String newUserRepresentString = i_UserName.toLowerCase() + i_UserType.getValue();
        int id = newUserRepresentString.hashCode();
        if (m_UserIdToUser.get(id) != null) {
            throw new Exception("User: " + i_UserName.toLowerCase() + " with the role: " + i_UserType.getValue() + " is already exists");
        }
        addUser(new User(id, i_UserName, i_UserType));
        return id;
    }

    public User getUser(int i_CustomerId) {
        return m_UserIdToUser.get(i_CustomerId);
    }

    public boolean isUserExist(int i_CustomerID) {
        return m_UserIdToUser.containsKey(i_CustomerID);
    }
}