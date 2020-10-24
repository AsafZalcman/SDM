package utils;

import constants.Constants;
import manager.AlertManager;
import viewModel.UserViewModel;
import javax.servlet.ServletContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ServletUtils {

	private static final String ACCOUNT_VIEW_MODEL_ATTRIBUTE_NAME = "accountViewModel";
	private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object chatManagerLock = new Object();

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

public static int getIntParameter(HttpServletRequest request, String name) {
	String value = request.getParameter(name);
	if (value != null) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException numberFormatException) {
		}
	}
	return Constants.INT_PARAMETER_ERROR;
}

public static LocalDate getDateParameter(HttpServletRequest request) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	String date = request.getParameter(Constants.ACCOUNT_DATE_PARAMETER);
	return LocalDate.parse(date, formatter);
}

	 public static AlertManager getAlertManager(ServletContext servletContext, String i_UserName) {
		 Map<String, AlertManager> userToAlertManager;
		synchronized (userManagerLock) {
			userToAlertManager = ( Map<String, AlertManager>)servletContext.getAttribute(Constants.ALERT_MANAGER_ATTRIBUTE_NAME);
			if (userToAlertManager == null) {
				 userToAlertManager = new HashMap<>();
			}
			if(userToAlertManager.get(i_UserName) == null)
			{
				userToAlertManager.put(i_UserName,new AlertManager());
				servletContext.setAttribute(Constants.ALERT_MANAGER_ATTRIBUTE_NAME, userToAlertManager);
			}
		}
		return userToAlertManager.get(i_UserName);
	}
}
