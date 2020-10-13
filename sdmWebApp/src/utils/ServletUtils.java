package utils;

import viewModel.UserViewModel;

import javax.servlet.ServletContext;

public class ServletUtils {
    private static final String USER_VIEW_MODEL_ATTRIBUTE_NAME = "userViewModel";

    private static final Object userViewModelLock = new Object();

    public static UserViewModel getUserViewModel(ServletContext servletContext){
        synchronized (userViewModelLock){
            if(servletContext.getAttribute(USER_VIEW_MODEL_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(USER_VIEW_MODEL_ATTRIBUTE_NAME, new UserViewModel());
            }
        }
        return (UserViewModel) servletContext.getAttribute(USER_VIEW_MODEL_ATTRIBUTE_NAME);
    }
}
