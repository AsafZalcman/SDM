package manager;

import com.google.gson.Gson;
import constants.Constants;
import dtoModel.AccountDto;
import model.Alert;
import model.NewStoreAlert;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlertManager {

    private final List<Alert> alerts;

    public AlertManager() {
        alerts = new ArrayList<>();
    }

    public synchronized void addAlert(Alert alert){
        alerts.add(alert);
    }

    public synchronized List<Alert> getAlerts(int fromIndex){
        if (fromIndex < 0 || fromIndex > alerts.size()) {
            fromIndex = 0;
        }
        return alerts.subList(fromIndex, alerts.size());
    }

    public synchronized int getVersion() {
        return alerts.size();
    }

    private static class AlertBuilder{

        public static Alert create(Alert.AlertType type,HttpServletRequest request) throws IOException {
            switch (type)
            {
                case NEW_STORE:{
                    return new Gson().fromJson(request.getReader(), NewStoreAlert.class);
                }
            }
            return null;

        }

    }
}
