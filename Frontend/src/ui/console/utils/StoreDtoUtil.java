package ui.console.utils;

import myLocation.Location;

public class StoreDtoUtil {

    public static String getPricePerKmString(double i_PricePerKm) {
        return "- Price Per Km: " +  FormatUtils.DecimalFormat.format(i_PricePerKm);
    }

    public static String getIncomesFromDeliveriesString(Double i_IncomesFromDeliveries) {
        StringBuilder res = new StringBuilder("- Incomes from deliveries: ");
        res.append(i_IncomesFromDeliveries != null ?
                FormatUtils.DecimalFormat.format(i_IncomesFromDeliveries)
                : "No deliveries income to show");
        return res.toString();
    }

    public static String getLocationString(Location i_Location) {
        return "- Location: " +  i_Location;
    }



}
