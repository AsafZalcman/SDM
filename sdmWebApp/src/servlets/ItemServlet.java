package servlets;
//new Gson().fromJson(request.getReader(), Person.class);

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.StoreDto;
import dtoModel.ZoneDto;
import utils.SessionUtils;
import viewModel.ItemViewModel;
import viewModel.LocationViewModel;
import viewModel.StoreViewModel;
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

@WebServlet("/items")
@MultipartConfig
public class ItemServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = SessionUtils.getUsername(request);

        if (userName != null) {
            String zoneName = request.getParameter(Constants.ZONE_NAME_PARAMETER);
            Gson gson = new Gson();
            String jsonResponse;

            ItemViewModel itemViewModel = new ItemViewModel();
            try {
                jsonResponse = gson.toJson(itemViewModel.getAllItems(zoneName));
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
