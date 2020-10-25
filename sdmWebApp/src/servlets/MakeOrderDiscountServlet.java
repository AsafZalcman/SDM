package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@WebServlet("/makeOrder/discount")
public class MakeOrderDiscountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try {
            response.setStatus(200);
            String parseMap = gson.toJson(orderViewModel.getAvailableDiscountsForCurrentOrder());
            System.out.println(parseMap);
            out.println(parseMap);
        } catch (Exception e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }
}
