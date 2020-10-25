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
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String returnMessage = "";
        try {
            Gson gson = new Gson();
            OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));
            LocalDate orderDate = ServletUtils.getDateParameter(request);
            String selectStore = request.getParameter("selectStore");
            String zone = request.getParameter("zone");

            orderViewModel.setZone(zone);
            orderViewModel.setCustomer(SessionUtils.getUserId(request));
            orderViewModel.setDate(orderDate);
            orderViewModel.setLocation(gson.fromJson(request.getParameter("location"), (Type) Point.class));
            if (selectStore != null) {
                orderViewModel.setStore(Integer.parseInt(selectStore));
            }
            response.setStatus(200);
            returnMessage = "Pre Order data saved";
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
