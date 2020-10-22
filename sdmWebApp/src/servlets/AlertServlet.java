package servlets;

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.AccountDto;
import dtoModel.AccountMovementDto;
import dtoModel.ZoneDto;
import manager.AlertManager;
import model.Alert;
import utils.ResponseUtils;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.AccountViewModel;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/alerts")
@MultipartConfig
public class AlertServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);
        if (userName != null) {
            int alertVersion = ServletUtils.getIntParameter(request, Constants.ALERT_VERSION_PARAMETER);
            if (alertVersion == Constants.INT_PARAMETER_ERROR) {
                return;
            }
            AlertManager alertManager = ServletUtils.getAlertManager(getServletContext(),userName);
            response.setContentType("application/json");
            AlertAndVersion alertAndVersion = new AlertAndVersion(alertManager.getAlerts(alertVersion),alertManager.getVersion());

            try (PrintWriter out = response.getWriter()) {
                out.print(new Gson().toJson(alertAndVersion));
                out.flush();
            }
        }}

//  @Override
//  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      Integer userId = SessionUtils.getUserId(request);
//      if (userId != null) {
//          PrintWriter out = response.getWriter();
//          response.setContentType("application/json");
//          String returnMessage = "";

//          synchronized (this) {
//              AlertManager alertManager = m_UserToAlertManager.getOrDefault(getTargetUser(request), new AlertManager());
//              try {
//                  request
//                  alertManager.addAlert(request);
//                  response.setStatus(201);
//              } catch (Exception e) {
//                  response.setStatus(500);
//              } finally {
//                  out.println(ResponseUtils.getJsonResponseString(response.getStatus(), returnMessage));
//              }
//          }
//      }
//  }
//private String getTargetUser(HttpServletRequest request)
//{
//    switch (Alert.AlertType.valueOf(request.getParameter(Constants.ALERT_TYPE_PARAMETER).toUpperCase()))
//    {
//        case NEW_STORE:{
//            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
//            ZoneViewModel zoneViewModel = new ZoneViewModel();
//            return zoneViewModel.getZone(zoneName).getOwnerName();
//        }
//    }
//    return null;
//}

    private static class AlertAndVersion {

        final private Collection<Alert> alerts;
        final private int version;

        public AlertAndVersion(Collection<Alert> alerts, int version) {
            this.alerts = alerts;
            this.version = version;
        }
    }
}
