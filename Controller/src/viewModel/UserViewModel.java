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

    //return the unique id of the created user
    // raise exception if the user is already exists
    public int addUser(String i_Name, String i_UserType) throws Exception {
        UserType userType = null;
        try {
            userType = UserType.valueOf(i_UserType);
        }catch (Exception e){
            System.out.println(e);
        }
        return m_SuperDuperManager.addUser(i_Name, userType);
    }

//    public boolean isUserExists(String i_UserName){
//        return m_SuperDuperManager.isUserExists(i_UserName);
//    }
}