package ui.console.utils;

import java.util.Date;

public class OrderDtoUtil {

    public static String getDateString(Date i_Date) {
        return "- Date: " + FormatUtils.DateFormat.format(i_Date);
    }

    public static String getItemsCountString(int i_Count) {
        return "- Total number of items: " + i_Count;
    }

    public static String getItemsPriceString(double i_ItemsPrice) {
        return "- Total price of items: " + FormatUtils.DecimalFormat.format(i_ItemsPrice);
    }

    public static String getDeliveryPriceString(double i_DeliveryPrice) {
        return "- Delivery price: " + FormatUtils.DecimalFormat.format(i_DeliveryPrice);
    }

    public static String getTotalOrderPriceString(double i_TotalOrderPrice) {
        return "- Total order price: " + FormatUtils.DecimalFormat.format(i_TotalOrderPrice);
    }

    public static String getTotalOrderItemsPriceString(double i_TotalOrderPrice) {
        return "- Total order items price: " + FormatUtils.DecimalFormat.format(i_TotalOrderPrice);
    }

    public static String getDistanceFromSourceString(double i_DistanceFromSource) {
        return "- Distance from Store: " + FormatUtils.DecimalFormat.format(i_DistanceFromSource);
    }

    public static String getNumberOfDifferentItemsString(int i_NumberOfDifferentItems) {
        return "- Number of different items:" + i_NumberOfDifferentItems;
    }

    public static String getTotalItemCostString(double i_TotalItemCost)
    {
      return  "- Total item cost: " + FormatUtils.DecimalFormat.format(i_TotalItemCost);
    }
}