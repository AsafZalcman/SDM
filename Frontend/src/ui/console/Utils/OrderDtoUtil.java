package ui.console.Utils;

import java.util.Date;

public class OrderDtoUtil {

    public static String getDateString(Date i_Date)
    {
        return "- Date: " + FormatUtils.DateFormat.format(i_Date);
    }

    public static String getItemsCountString(int i_Count)
    {
        return "- Number of items: " +i_Count;
    }

    public static String getItemsPrice(double i_ItemsPrice)
    {
        return "- Total price of items: " +  FormatUtils.DecimalFormat.format(i_ItemsPrice);
    }

    public static String getDeliveryPrice(double i_DeliveryPrice)
    {
        return "-  Delivery price: " +  FormatUtils.DecimalFormat.format(i_DeliveryPrice);
    }

    public static String getTotalOrderPrice(double i_TotalOrderPrice)
    {
        return "-  Total order price: " + FormatUtils.DecimalFormat.format(i_TotalOrderPrice);
    }
}
