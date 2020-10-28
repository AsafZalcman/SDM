package servlets;
import com.google.gson.Gson;
import constants.Constants;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.OrderViewModel;
import javax.servlet.ServletException;
import com.google.gson.GsonBuilder;
import dtoModel.OrderDto;
import dtoModel.StorageOrderDto;
import viewModel.OrderHistoryViewModel;
import viewModel.UserViewModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        } catch (Exception e) {
            response.setStatus(400);
            returnMessage = e.getMessage();

        } finally {
            try (PrintWriter out = response.getWriter()) {
                out.print(returnMessage);
                out.flush();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer userId = SessionUtils.getUserId(request);
        if (userId != null) {
            response.setContentType("application/json");
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                    .setPrettyPrinting().create();
            UserViewModel userViewModel = new UserViewModel();
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);

            OrderHistoryViewModel orderHistoryViewModel = new OrderHistoryViewModel();
            String jsonResponse;
            if ( userViewModel.getUser(userId).isCustomer()) {
                jsonResponse = gson.toJson(orderHistoryViewModel.getAllStorageOrderOfUser(zoneName, userId).stream()
                        .map(SimpleStorageOrder::new).collect(Collectors.toList()));
            } else {
                jsonResponse = gson.toJson(orderHistoryViewModel.getAllStorageOrderByOwner(zoneName, SessionUtils.getUsername(request)));
            }

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    private static class SimpleStorageOrder {
        private  OrderDto orderDto;
        private  Map<SimpleStoreDto, OrderDto> simpleStoreToOrderMap;

        public SimpleStorageOrder(OrderDto orderDto, Map<SimpleStoreDto, OrderDto> simpleStoreToOrderMap) {
            this.orderDto = orderDto;
            this.simpleStoreToOrderMap = simpleStoreToOrderMap;
        }

        public SimpleStorageOrder(StorageOrderDto storageOrderDto) {
        orderDto=storageOrderDto.getOrderDto();
        simpleStoreToOrderMap = storageOrderDto.getStoresToOrders().entrySet().stream().collect(Collectors.toMap(
                storeDtoOrderDtoEntry -> new SimpleStoreDto(storeDtoOrderDtoEntry.getKey().getName(),storeDtoOrderDtoEntry.getKey().getId()),
                Map.Entry::getValue));
        }

            public OrderDto getOrderDto() {
            return orderDto;
        }

        public void setOrderDto(OrderDto orderDto) {
            this.orderDto = orderDto;
        }

        public Map<SimpleStoreDto, OrderDto> getSimpleStoreToOrderMap() {
            return simpleStoreToOrderMap;
        }

        public void setSimpleStoreToOrderMap(Map<SimpleStoreDto, OrderDto> simpleStoreToOrderMap) {
            this.simpleStoreToOrderMap = simpleStoreToOrderMap;
        }
    }

    private static class SimpleStoreDto {
        private  String name;
        private  int id;

        public SimpleStoreDto(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleStoreDto that = (SimpleStoreDto) o;
            return getId() == that.getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash( getId());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String returnMessage = "";
        try {
            OrderViewModel orderViewModel = ServletUtils.getOrderViewModel(SessionUtils.getUserId(request));
            orderViewModel.cleanup();
            response.setStatus(200);
            returnMessage = "Order view model cleanUp successes";
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
