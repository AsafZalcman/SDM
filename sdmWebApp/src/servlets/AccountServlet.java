package servlets;
import com.google.gson.Gson;
import constants.Constants;
import dtoModel.AccountDto;
import dtoModel.AccountMovementDto;
import utils.ServletUtils;
import utils.SessionUtils;
import viewModel.AccountViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/account")

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountViewModel accountViewModel = new AccountViewModel();
        Integer userId = SessionUtils.getUserId(request);
        if (userId != null) {
            int accountVersion = ServletUtils.getIntParameter(request, Constants.ACCOUNT_VERSION_PARAMETER);
            if (accountVersion == Constants.INT_PARAMETER_ERROR) {
                return;
            }
            int accountViewModelVersion = accountViewModel.getVersion(userId);
            if (accountVersion == accountViewModelVersion) {
                return;
            }
            response.setContentType("application/json");
            List<AccountMovementDto> accountMovementDtoList;
            accountMovementDtoList = accountViewModel.getAccountOperations(userId, accountVersion);

            AccountDto accountDto = new AccountDto(accountViewModel.getAccount(userId).getBalance(), accountMovementDtoList);
            AccountAndVersion accountAndVersion = new AccountAndVersion(accountDto, accountViewModelVersion);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(accountAndVersion);
            System.out.println("Server Account version: " + accountViewModelVersion + ", User '" + userId + "' account version: " + accountVersion);
            System.out.println(jsonResponse);

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Integer userId = SessionUtils.getUserId(request);
      if (userId != null) {

          double amount = Double.parseDouble(request.getParameter(Constants.ACCOUNT_AMOUNT_PARAMETER));
          LocalDate date = ServletUtils.getDateParameter(request);
          AccountViewModel accountViewModel = new AccountViewModel();
      accountViewModel.loadBalance(userId, date, amount);
      }
  }

    private static class AccountAndVersion {

        final private AccountDto accountDto;
        final private int version;

        public AccountAndVersion(AccountDto accountDto, int version) {
            this.accountDto = accountDto;
            this.version = version;
        }
    }
}
