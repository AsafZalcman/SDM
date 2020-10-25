package servlets;

import com.google.gson.Gson;
import constants.Constants;
import utils.SessionUtils;
import viewModel.LocationViewModel;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/locations")
@MultipartConfig
public class LocationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);

        if (userName != null) {
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
            Gson gson = new Gson();
            String jsonResponse;

            LocationViewModel locationViewModel = new LocationViewModel();
            try {
                jsonResponse = gson.toJson(locationViewModel.getAllAvailableLocations(zoneName));
                response.setContentType("application/json");

            } catch (Exception e) {
                response.setStatus(400);
                jsonResponse = e.getMessage();
            }

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }

        }
    }
}
