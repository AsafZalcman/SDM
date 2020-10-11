package servlets;

import com.google.gson.Gson;
import dtoModel.UserDto;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.UserViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer userId = SessionUtils.getUserId(request);
        if (userId != null) {
            response.setContentType("application/json");
            UserViewModel userViewModel = ServletUtils.getUserViewModel(getServletContext());

            List<String> userNamesList;
            userNamesList = userViewModel.getAllUsers().stream().map(UserDto::getName).collect(Collectors.toList());
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(userNamesList);
            System.out.println("All users: " + userNamesList);
            System.out.println(jsonResponse);

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserViewModel userViewModel = ServletUtils.getUserViewModel(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String userRoleFromParameter = request.getParameter(ROLE);
        String returnMessage = "";
            synchronized (this) {
                try {
                    int newUserID = userViewModel.addUser(usernameFromParameter, userRoleFromParameter);
                    request.getSession(true).setAttribute(USERNAME, usernameFromParameter);
                    request.getSession(false).setAttribute(USER_ID,newUserID);
                    response.setStatus(201);
                    returnMessage = "The user:\""+usernameFromParameter + "\" created";
                }catch (Exception e){
                    response.setStatus(400);
                    returnMessage = e.getMessage();

                }
                finally {
                    try (PrintWriter out = response.getWriter()) {
                        out.print(returnMessage);
                        out.flush();
                    }
                }
            }

    }
}
