package servlets;

import constants.Constants;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.UserViewModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.ROLE;
import static constants.Constants.USERNAME;

public class UserServlet extends HttpServlet {
    private final String DASHBOARD_URL = "pages/dashboard/dashboard.html";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserViewModel userViewModel = ServletUtils.getUserViewModel(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String userRoleFromParameter = request.getParameter(ROLE);

            synchronized (this) {
                try {
                    int newUserID = userViewModel.addUser(usernameFromParameter, userRoleFromParameter);
                    request.getSession(true).setAttribute(USERNAME, usernameFromParameter);
                    response.sendRedirect(DASHBOARD_URL);
                }catch (Exception e){
                    response.sendError(400, e.getMessage());
                }
            }

    }
}
