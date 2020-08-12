package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StorageOrderDto;
import ViewModel.OrderViewModel;
import myLocation.LocationException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderConsoleManager {

    private Scanner scanner = new Scanner(System.in);
    private StoreConsoleManager m_StoreConsoleManager = new StoreConsoleManager();
    private ItemConsoleManager m_ItemConsoleManager = new ItemConsoleManager();
    private int m_StoreId;
    private DecimalFormat m_DecimalFormat =new DecimalFormat("##.##");

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
        } while (!addItemToOrder());
        m_OrderViewModel.createOrder();
    }

    private void addItemsToStaticOrder() {

        do {
            System.out.println(m_ItemConsoleManager.getAllItemsWithPrice(m_StoreId));
        } while (!addItemToOrder());
        m_OrderViewModel.createOrder();
    }

    private boolean addItemToOrder() {
        final String EXIT= "q";
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        int itemId;
        double amountOfSell;
        System.out.println("Please enter the id of the desired item, or press " + EXIT + " to finish");
        userChoiceStr = scanner.nextLine();
        if (userChoiceStr.toLowerCase().equals(EXIT)) {
            return true;
        }
        try {
            itemId = Integer.parseInt(userChoiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Error:\"" + userChoiceStr + "\" is not number");
            return false;
        }
        System.out.println("Please enter the amount you want to buy");
        try {
            userChoiceStr = scanner.nextLine();
            amountOfSell = Double.parseDouble(userChoiceStr);

        } catch (NumberFormatException e) {
            System.out.println("Error:\"" + userChoiceStr + "\" is not number");
            return false;
        }

        try {
            m_OrderViewModel.addItemToOrder(itemId, amountOfSell);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return false;

        }

        return false;
    }


    private void showOrderDetails()
    {
        OrderDto orderDto = m_OrderViewModel.getCurrentOrder();
        int counter =1;
        System.out.println("Order bill:");
        for (ItemDto itemDto:orderDto.getItemsDto()
        ) {
            System.out.println(counter + ".\n" + m_ItemConsoleManager.getBasicDataFromItem(itemDto)+"\n" + m_ItemConsoleManager.getPriceField(itemDto) + "\nAmount:" + itemDto.getAmountOfSell() + "\nTotal price:" + itemDto.getTotalPrice());
            counter++;
        }

        System.out.println("Delivery price:" + m_DecimalFormat.format(orderDto.getDeliveryPrice()) +"\nDistance from Store:" + m_DecimalFormat.format(orderDto.getDistanceFromSource()) + "\nPrice for Km:" +orderDto.getPPK());
    }

    private void approveOrder()
    {
        Scanner scanner = new Scanner(System.in);
        showOrderDetails();
        System.out.println("Are you accept the order? press y for yes, or any other option to abort");
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a date for the order with dd/mm-hh:mm format");
        String userChoice;
        while (true) {
            userChoice = scanner.nextLine();
            try {
                m_OrderViewModel.setDateForOrder(userChoice);
            } catch (ParseException e) {
                System.out.println("Error:" + e.getMessage() + ",please try again");
                continue;
            }
            break;
        }
    }

    private void getUserLocation()
    {
        Scanner scanner = new Scanner(System.in);
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
        for (StorageOrderDto storageOrderDto: m_OrderViewModel.getAllOrders()
        ) {
            System.out.println(counter +".\nid:"+storageOrderDto.getOrderID()+"\ndate:"+storageOrderDto.getDate()+"\nStore id:" +storageOrderDto.getStoreID()
                    +"\nStore name:"+storageOrderDto.getStoreName() +"\nNumber of different items:" +storageOrderDto.getTotalItemsKind() +"" + "\nNumber of items:" +storageOrderDto.getTotalItemsCount()
                    +"\nPrice of all items:" + storageOrderDto.getTotalItemsPrice()+"\nDelivery price:" +m_DecimalFormat.format(storageOrderDto.getDeliveryPrice()) + "\nOrder price:" + m_DecimalFormat.format(storageOrderDto.getTotalOrderPrice()));
            counter++;
        }
        if(counter==1)
        {
            System.out.println("No orders to show");
        }
    }
}
