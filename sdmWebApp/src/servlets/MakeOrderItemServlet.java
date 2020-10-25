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

@WebServlet("/makeOrder/item")
public class MakeOrderItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try {
            response.setStatus(200);
            out.println(gson.toJson(orderViewModel.getAvailableItemsForOrder()));
        } catch (Exception e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String returnMessage = "";
        try {
            OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));
            String itemID = request.getParameter("id");
            String itemAmount = request.getParameter("amount");
            orderViewModel.addItemToOrder(Integer.parseInt(itemID), Double.parseDouble(itemAmount));
            response.setStatus(200);
            returnMessage = "Item saved";
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
