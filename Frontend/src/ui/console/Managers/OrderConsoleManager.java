package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StorageOrderDto;
import ViewModel.OrderViewModel;
import myLocation.LocationException;
import ui.console.Utils.FormatUtils;
import ui.console.Utils.ItemDtoUtils;
import ui.console.Utils.OrderDtoUtil;
import ui.console.Utils.UniquelyUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderConsoleManager {

    private Scanner scanner = new Scanner(System.in);
    private StoreConsoleManager m_StoreConsoleManager = new StoreConsoleManager();
    private ItemConsoleManager m_ItemConsoleManager = new ItemConsoleManager();
    private int m_StoreId;
    private OrderViewModel m_OrderViewModel = new OrderViewModel();

    public void MakeStaticOrder(){
        getStoreIdFromUser();
        getOrderDateFromUser();
        getUserLocation();
        addItemsToStaticOrder();
        approveOrder();
    }

    public void MakeDynamicOrder()
    {
        getOrderDateFromUser();
        getUserLocation();
        addItemsToDynamicOrder();
        approveOrder();
    }

    private void addItemsToDynamicOrder() {
        do {
            System.out.println(m_ItemConsoleManager.getAllItems());
        } while (addItemToOrder());
        try {
            m_OrderViewModel.createOrder();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addItemsToStaticOrder() {

        do {
            System.out.println(m_ItemConsoleManager.getAllItemsWithStorePrice(m_StoreId));
        } while (addItemToOrder());
        try {
            m_OrderViewModel.createOrder();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean addItemToOrder() {
        final String EXIT= "q";
        String userChoiceStr;
        int itemId;
        double amountOfSell;
        System.out.println("Please enter the id of the desired item, or press " + EXIT + " to finish");
        userChoiceStr = scanner.nextLine();
        if (userChoiceStr.toLowerCase().equals(EXIT)) {
            return false;
        }
        try {
            itemId = Integer.parseInt(userChoiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Error:\"" + userChoiceStr + "\" is not number");
            return true;
        }
        //need to add the validation if the item is sold by the current store.
        System.out.println("Please enter the amount you want to buy");
        try {
            userChoiceStr = scanner.nextLine();
            amountOfSell = Double.parseDouble(userChoiceStr);

        } catch (NumberFormatException e) {
            System.out.println("Error:\"" + userChoiceStr + "\" is not number");
            return true;
        }

        try {
            m_OrderViewModel.addItemToOrder(itemId, amountOfSell);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return true;

        }

        return true;
    }

    private void showOrderDetails()
    {
        OrderDto orderDto = m_OrderViewModel.getCurrentOrder();
        int counter =1;
        System.out.println("Order summary:");
        StringBuilder ordersDetails = new StringBuilder();
        for (ItemDto itemDto:orderDto.getItemsDto()
        ) {
            ordersDetails.append(counter)
                    .append(".\n")
                    .append(ItemDtoUtils.getBasicItemString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getPriceString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getAmountOfSellsString(itemDto.getAmountOfSell()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalOrderPrice(itemDto.getTotalPrice()));
            counter++;
        }
        String additionalValuesForStaticOrder = orderDto.isDynamicOrder()? "":"\nDistance from Store:" + FormatUtils.DecimalFormat.format(orderDto.getDistanceFromSource()) + "\nPrice for Km:" +orderDto.getPPK();
        System.out.println(ordersDetails.toString() + OrderDtoUtil.getDeliveryPrice(orderDto.getDeliveryPrice()) + additionalValuesForStaticOrder);
    }

    private void approveOrder()
    {
        showOrderDetails();
        System.out.println("Are you accept the order? press y for yes, or any other key to abort");
        String userChoice = scanner.nextLine();
        if(userChoice.toUpperCase().equals("Y"))
        {
            m_OrderViewModel.executeOrder();
            System.out.println("Your order was executed");
        }
        else
        {
            System.out.println("Your order was aborted");
        }
    }

    private void getStoreIdFromUser() {
        while (true) {
            try {
                m_StoreId = m_StoreConsoleManager.getStoreIdFromUser();
                m_OrderViewModel.setStoreForOrder(m_StoreId);
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
                continue;
            }
            break;
        }
    }
    private void getOrderDateFromUser() {
        System.out.println("Please enter a date for the order with " + FormatUtils.DATE_PATTERN + " format");
        String userChoice;
        DateFormat format = new SimpleDateFormat(FormatUtils.DATE_PATTERN, Locale.ENGLISH);
        Date date;
        while (true) {
            userChoice = scanner.nextLine();
            try {
                date = format.parse(userChoice);
            } catch (ParseException e) {
                System.out.println("the given date: " + userChoice + " is not in the correct pattern, which is " + FormatUtils.DATE_PATTERN);
                continue;
            }
            m_OrderViewModel.setDateForOrder(date);
            break;
        }
    }

    private void getUserLocation()
    {
        int x,y;
        while (true) {
            System.out.println("Please enter your x coordinate");
            String userChoice = scanner.nextLine();
            try {
                x = Integer.parseInt(userChoice);
                System.out.println("Please enter your y coordinate");
                userChoice = scanner.nextLine();
                y = Integer.parseInt(userChoice);
            } catch (InputMismatchException e) {
                System.out.println("Error:\"" + userChoice + "\" is not number");
                continue;
            }
            try {
                m_OrderViewModel.setLocationForOrder(x, y);
            } catch (LocationException e) {
                System.out.println("Error:" + e.getMessage() + ",please try again");
                continue;
            }
            break;
        }
    }

    public void ShowAllOrdersHistory()
    {
        int counter =1;
        String storeDetails;
        StringBuilder orderDetails = new StringBuilder();
        for (StorageOrderDto storageOrderDto: m_OrderViewModel.getAllOrders()
        ) {
            OrderDto tempOrder = storageOrderDto.getOrderDto();
            storeDetails = storageOrderDto.getOrderDto().isDynamicOrder() ?
                    "- Number of Stores that took part in this order: " + storageOrderDto.getNumberOfStores() :
                    "- Store id: " + storageOrderDto.getOrderDto().getStoreId() + "\n- Store name: " + storageOrderDto.getOrderDto().getStoreName();
            orderDetails.append(counter)
                    .append(".\n")
                    .append(UniquelyUtil.getIdString(storageOrderDto.getOrderID()))
                    .append("\n")
                    .append(OrderDtoUtil.getDateString(tempOrder.getDate()))
                    .append("\n")
                    .append(storeDetails)
                    .append("\n")
                    .append("- Number of different items:")
                    .append(tempOrder.getTotalItemsKind()).
                    append("\n")
                    .append(OrderDtoUtil.getItemsCountString(tempOrder.getTotalItemsCount()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalOrderPrice(tempOrder.getTotalItemsPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getDeliveryPrice(tempOrder.getDeliveryPrice())).append("\n")
                    .append(FormatUtils.DecimalFormat.format(tempOrder.getTotalOrderPrice()));
            counter++;
        }
        if(counter==1)
        {
            orderDetails.append("No orders to show\n");
        }

        System.out.println(orderDetails.toString());
    }
}
