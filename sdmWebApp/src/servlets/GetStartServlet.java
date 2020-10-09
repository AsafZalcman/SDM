package servlets;

import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetStartServlet extends HttpServlet {
    private final String SIGNUP_URL = "pages/signup/signup.html";
    private final String DASHBOARD_URL = "/pages/dashboard/dashboard.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String usernameFromSession = SessionUtils.getUsername(request);
        if(usernameFromSession == null){
            response.sendRedirect(SIGNUP_URL);
        }else{
            response.sendRedirect(DASHBOARD_URL);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
