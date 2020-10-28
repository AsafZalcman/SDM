package servlets;

import com.google.gson.Gson;
import dtoModel.OrderDto;
import dtoModel.StorageOrderDto;
import dtoModel.StoreDto;
import manager.AlertManager;
import model.Alert;
import model.NewOrderAlert;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.AccountViewModel;
import viewModel.OrderViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   {
        Integer userId = SessionUtils.getUserId(request);
        if (userId != null) {
            OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(userId);
            payForOrder(orderViewModel.getCurrentOrder(),userId);
            createPaymentAlert(orderViewModel.getCurrentOrder(),SessionUtils.getUsername(request));
            orderViewModel.executeOrder();
        }
    }

    private void payForOrder(StorageOrderDto currentOrder, int customerID) {
        AccountViewModel accountViewModel = new AccountViewModel();
        accountViewModel.transferMoney(customerID,currentOrder.getOrderDto().getDate(),currentOrder.getOrderDto().getTotalOrderPrice());
        for (Map.Entry<String,Double> userToPayment: getPaymentMap(currentOrder).entrySet()
        ) {
            accountViewModel.acceptPayment(currentOrder.getOrderDto().getDate(), userToPayment.getValue(),userToPayment.getKey());
        }
    }

    private Map<String,Double> getPaymentMap(StorageOrderDto storageOrderDto)
    {
        Map <String,Double> res = new HashMap<>();
        for (Map.Entry<StoreDto, OrderDto> storeDtoToOrder: storageOrderDto.getStoresToOrders().entrySet()
        ) {
            double currentPayment = res.getOrDefault(storeDtoToOrder.getKey().getOwnerName(),0.0);
            currentPayment += storeDtoToOrder.getValue().getTotalOrderPrice();
            res.put(storeDtoToOrder.getKey().getOwnerName(),currentPayment);
        }

        return res;
    }


    private void createPaymentAlert(StorageOrderDto storageOrderDto , String userName)
    {
        for (Map.Entry<StoreDto, OrderDto> storeDtoToOrder: storageOrderDto.getStoresToOrders().entrySet()
        ) {
            StoreDto storeDto = storeDtoToOrder.getKey();
            OrderDto orderDto = storeDtoToOrder.getValue();
            AlertManager alertManager = ServletUtils.getAlertManager(getServletContext(),storeDto.getOwnerName());
            Alert alert = new NewOrderAlert(orderDto.getId(),userName,orderDto.getTotalItemsKind(),orderDto.getTotalItemsPrice(),orderDto.getDeliveryPrice(),storeDto.getName());
            alertManager.addAlert(alert);
        }
    }
}

