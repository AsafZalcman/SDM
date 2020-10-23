package servlets;
//new Gson().fromJson(request.getReader(), Person.class);

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.ItemDto;
import dtoModel.StoreDto;
import manager.AlertManager;
import model.Alert;
import model.NewStoreAlert;
import utils.ResponseUtils;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.ItemViewModel;
import viewModel.StoreViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/stores")
@MultipartConfig
public class StoresServlet extends HttpServlet {

    //  @Override
    //  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //      String userName = SessionUtils.getUsername(request);
    //      if (userName != null) {
    //          response.setContentType("application/json");
    //          ZoneViewModel zoneViewModel = new ZoneViewModel();
    //          Gson gson = new Gson();
    //          String jsonResponse;
    //          String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
    //          if (zoneName == null) {
    //              //get summary of all zones
    //              Collection<SimpleZoneDto> simpleZoneDtoCollection = zoneViewModel.getAllZones().stream().map(SimpleZoneDto::new).collect(Collectors.toList());
    //              jsonResponse = gson.toJson(simpleZoneDtoCollection);
    //          }
    //          else
    //          {
    //              //return specific zone
    //              System.out.println("requested zone: " + zoneName);
    //              jsonResponse = gson.toJson(zoneViewModel.getZone(zoneName));
    //          }
    //          System.out.println(jsonResponse);
//
    //          try (PrintWriter out = response.getWriter()) {
    //              out.print(jsonResponse);
    //              out.flush();
    //          }
    //      }
    //  }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        String returnMessage = "";
        PrintWriter out = response.getWriter();
        if (userName != null) {
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
            Gson gson = new Gson();
            response.setContentType("application/json");
            StoreViewModel storeViewModel = new StoreViewModel();
            try {
                Point location = gson.fromJson(request.getParameter("location"), Point.class);
               List<ClientItem> clientItems= Arrays.asList(gson.fromJson(request.getParameter("items"),ClientItem[].class));
               List<ItemDto> itemsOfStore = new ArrayList<>();
                for (ClientItem clientItem: clientItems
                     ) {
                    itemsOfStore.add(storeViewModel.createNewStoreItem(zoneName,clientItem.id,clientItem.price));
                }
                StoreDto storeDto = new StoreDto(Integer.parseInt(request.getParameter("id")),
                        request.getParameter("name"),
                        Double.parseDouble(request.getParameter("ppk")),
                        location,
                        itemsOfStore,
                        userName);
                storeViewModel.createNewStore(zoneName,storeDto);
                returnMessage = "Store created!";
                response.setStatus(201);
                createNewStoreAlert(storeDto,zoneName);
            } catch (Exception e) {
                response.setStatus(400);
                returnMessage = e.getMessage();
            }
            finally {
                out.println(ResponseUtils.getJsonResponseString(response.getStatus(),returnMessage));
            }

        } else {
            response.setStatus(401);
            out.println(ResponseUtils.getJsonResponseString(response.getStatus(),returnMessage));
        }

        
    }

    private void createNewStoreAlert(StoreDto storeDto,String zoneName)
    {
        ItemViewModel itemViewModel = new ItemViewModel();
        AlertManager alertManager = ServletUtils.getAlertManager(getServletContext(),storeDto.getOwnerName());
        Alert alert = new NewStoreAlert(storeDto.getOwnerName(),storeDto.getName(),storeDto.getLocation(),storeDto.getItemsDto().size() + "\\" + itemViewModel.getAllItems(zoneName).size() );
        alertManager.addAlert(alert);
    }

    private static class ClientItem {

        private  int id;
        private  double price;

        private ClientItem(int id, double price) {
            this.id = id;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public void setId(int id)
        {
            this.id=id;
        }

        public void setPrice(double price)
        {
            this.price=price;
        }

    }

}
