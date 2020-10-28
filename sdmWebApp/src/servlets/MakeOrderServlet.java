package servlets;

import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.OrderViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/makeOrder")
public class MakeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        try {
            response.setStatus(200);
            out.println(gson.toJson(orderViewModel.createOrder()));
        } catch (Exception e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }

    }
}
