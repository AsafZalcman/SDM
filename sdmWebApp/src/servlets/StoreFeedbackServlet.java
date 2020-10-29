package servlets;
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
import model.StoreFeedbackAlert;
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

@WebServlet("/storeFeedback")
@MultipartConfig
public class StoreFeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);
        String returnMessage = "";
        if (userName != null) {
            int storeId = Integer.parseInt(request.getParameter(Constants.STORE_ID_PARAMETER));
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);

            StoreViewModel storeViewModel = new StoreViewModel();
            StoreDto storeDto = storeViewModel.getStore(zoneName,storeId);
            returnMessage = new Gson().toJson(storeDto.getFeedbacks());
                try (PrintWriter out = response.getWriter()) {
                    out.print(returnMessage);
                    out.flush();
                }
            }
        }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);
        String returnMessage = "";

        if (userName != null) {
            try {
                StoreFeedbackFromClient storeFeedbackFromClient = new Gson().fromJson(request.getReader(), StoreFeedbackFromClient.class);
                StoreViewModel storeViewModel = new StoreViewModel();
                String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
                storeViewModel.addStoreFeedback(zoneName, storeFeedbackFromClient.getStoreId(), storeFeedbackFromClient.getDate(), storeFeedbackFromClient.getRank(), storeFeedbackFromClient.getDescription(), userName);
                response.setStatus(201);
                returnMessage = "Feedback created!!";
                createFeedbackAlert(userName,storeViewModel.getStore(zoneName,storeFeedbackFromClient.getStoreId()),storeFeedbackFromClient);
            } catch (IOException e) {
                response.setStatus(500);
            } finally {
                try (PrintWriter out = response.getWriter()) {
                    out.print(returnMessage);
                    out.flush();
                }
            }
        }
    }

    private void createFeedbackAlert(String currentUser,StoreDto storeDto,StoreFeedbackFromClient storeFeedbackFromClient)
    {
            AlertManager alertManager = ServletUtils.getAlertManager(getServletContext(),storeDto.getOwnerName());
            Alert alert = new StoreFeedbackAlert(storeFeedbackFromClient.getRank(),storeFeedbackFromClient.getDescription(),currentUser,storeFeedbackFromClient.date,storeDto.getName() );
            alertManager.addAlert(alert);
    }

   private static class StoreFeedbackFromClient
   {
       private int rank;
       private  String description;
       private  int storeId;
       private  String date;

       public StoreFeedbackFromClient(int rank, String description, int storeId, String date) {
           this.rank = rank;
           this.description = description;
           this.storeId = storeId;
           this.date = date;
       }

       public int getRank() {
           return rank;
       }

       public void setRank(int rank) {
           this.rank = rank;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }

       public int getStoreId() {
           return storeId;
       }

       public void setStoreId(int storeId) {
           this.storeId = storeId;
       }

       public LocalDate getDate() {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
           return LocalDate.parse(date, formatter);
       }

       public void setDate(String date) {
           this.date = date;
       }
   }

}
