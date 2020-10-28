package viewModel;

import dtoModel.UserDto;
import enums.UserType;
import managers.SuperDuperManager;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserViewModel {

    private SuperDuperManager m_SuperDuperManager;

    public UserViewModel(){
        m_SuperDuperManager = SuperDuperManager.getInstance();
    }

    public Collection<UserDto> getAllUsers()
    {
        return m_SuperDuperManager.getAllCustomers().stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto getUser (int i_UserId)
    {
        return new UserDto(m_SuperDuperManager.getCustomer(i_UserId));
    }

    //return the unique id of the created user
    // raise exception if the user is already exists
    public int addUser(String i_Name, String i_UserType) throws Exception {
        UserType userType = UserType.valueOf(i_UserType.toUpperCase());
        return m_SuperDuperManager.addUser(i_Name, userType);
    }

//    public boolean isUserExists(String i_UserName){
//        return m_SuperDuperManager.isUserExists(i_UserName);
//    }
}