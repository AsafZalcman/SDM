package servlets;
//new Gson().fromJson(request.getReader(), Person.class);

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.ItemDto;
import dtoModel.OrderDto;
import dtoModel.StorageOrderDto;
import dtoModel.StoreDto;
import manager.AlertManager;
import model.Alert;
import model.NewOrderAlert;
import model.NewStoreAlert;
import utils.ResponseUtils;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@WebServlet("/executeOrder")
@MultipartConfig
public class ExecuteOrderServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   {
        Integer userId = SessionUtils.getUserId(request);
        if (userId != null) {
            OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(userId);
            orderViewModel.executeOrder();
            payForOrder(orderViewModel.getCurrentOrder(),userId);
            createPaymentAlert(orderViewModel.getCurrentOrder(),SessionUtils.getUsername(request));
        }
    }

    private void payForOrder(StorageOrderDto currentOrder,int customerID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(currentOrder.getOrderDto().getDate(), formatter);
        AccountViewModel accountViewModel = new AccountViewModel();

        for (Map.Entry<String,Double> userToPayment: getPaymentMap(currentOrder).entrySet()
             ) {
            accountViewModel.transferMoney(customerID, date, userToPayment.getValue(),userToPayment.getKey());
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
            Alert alert = new NewOrderAlert(orderDto.getId(),userName,orderDto.getTotalItemsKind(),orderDto.getTotalItemsPrice(),orderDto.getDeliveryPrice());
            alertManager.addAlert(alert);
        }
    }

}
