package servlets;

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.ZoneDto;
import utils.SessionUtils;
import viewModel.ZoneViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@WebServlet("/zones")
@MultipartConfig
public class ZoneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);
        if (userName != null) {
        response.setContentType("application/json");
        ZoneViewModel zoneViewModel = new ZoneViewModel();
            Gson gson = new Gson();
            String jsonResponse;
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
            if (zoneName == null) {
                //get summary of all zones
                Collection<SimpleZoneDto> simpleZoneDtoCollection = zoneViewModel.getAllZones().stream().map(SimpleZoneDto::new).collect(Collectors.toList());
                jsonResponse = gson.toJson(simpleZoneDtoCollection);
            }
            else
            {
                //return specific zone
                System.out.println("requested zone: " + zoneName);
                jsonResponse = gson.toJson(zoneViewModel.getZone(zoneName));
            }
            System.out.println(jsonResponse);

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        if (userName != null) {
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            ZoneViewModel zoneViewModel = new ZoneViewModel();
            InputStream fileInputStream = new ArrayList<>(request.getParts()).get(0).getInputStream();

            try {
                zoneViewModel.loadZoneFromXml(fileInputStream, userName);
                out.println(gson.toJson(new LoadFileStatus(true, "")));
            } catch (Exception e) {
                out.println(gson.toJson(new LoadFileStatus(false, e.getMessage())));
            }

        }
    }
    private static class SimpleZoneDto {

         private final String ownerName;
         private final String name;
         private final int numberOfItems;
         private final int numberOfStores;
         private final int numberOfOrders;
         private final double averagePriceOfOrdersWithoutDelivery;

        public SimpleZoneDto(ZoneDto i_ZoneDto) {
            ownerName=i_ZoneDto.getOwnerName();
            name=i_ZoneDto.getName();
            numberOfItems=i_ZoneDto.getAllItems().size();
            numberOfStores=i_ZoneDto.getAllStores().size();
            numberOfOrders=i_ZoneDto.getAllOrders().size();
            averagePriceOfOrdersWithoutDelivery =0.0;//not impkemented yet
        }

        public String getOwnerName() {
            return ownerName;
        }

        public String getName() {
            return name;
        }

        public int getNumberOfItems() {
            return numberOfItems;
        }

        public int getNumberOfStores() {
            return numberOfStores;
        }

        public int getNumberOfOrders() {
            return numberOfOrders;
        }

        public double getAveragePriceOfOrdersWithoutDelivery() {
            return averagePriceOfOrdersWithoutDelivery;
        }
    }

    public static class LoadFileStatus {
        boolean isLoaded;
        String errorMessage;

        public LoadFileStatus(boolean isLoaded, String message) {
            this.isLoaded = isLoaded;
            this.errorMessage = message;
        }

        public boolean isLoaded() {
            return this.isLoaded;
        }

        public void setLoaded(boolean loaded) {
            this.isLoaded = loaded;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
