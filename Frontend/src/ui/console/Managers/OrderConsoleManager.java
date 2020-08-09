package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
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

    private OrderViewModel m_OrderViewModel = new OrderViewModel();

    public void MakeAnOrder(){
            getStoreIdFromUser();
            getOrderDateFromUser();
            getUserLocation();
            addItemsToOrder();
            approveOrder();
    }

    private void addItemsToOrder() {
        final String EXIT= "q";
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        int itemId;
        double amountOfSell;
        while (true) {
            System.out.println(m_ItemConsoleManager.getAllItems(m_StoreId));
            System.out.println("Please enter the id of the desired item, or press " + EXIT + " to finish");
            userChoiceStr = scanner.nextLine();
            if(userChoiceStr.equals(EXIT))
            {
                break;
            }
            try {
                itemId = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error:\"" + userChoiceStr +"\" is not number");
                continue;
            }
            System.out.println("Please enter the amount you want to buy, or press " + EXIT + " to finish");
            try {
                userChoiceStr = scanner.nextLine();
                amountOfSell = Double.parseDouble(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error:\"" + userChoiceStr +"\" is not number");
                continue;
            }

            try {
                m_OrderViewModel.addItemToOrder(itemId,amountOfSell);
            } catch (Exception e) {
                System.out.println("Error:"+e.getMessage());
            }
        }
        m_OrderViewModel.createOrder();
    }

    private void showOrderDetails()
    {
        OrderDto orderDto = m_OrderViewModel.getCurrentOrder();
        int counter =1;
        System.out.println("Order bill:");
        for (ItemDto itemDto:orderDto.getItemsDto()
        ) {
            System.out.println(counter + ". " + m_ItemConsoleManager.getBasicDataFromItem(itemDto) + " Amount:" + itemDto.getAmountOfSell() + " Total price:" + itemDto.getTotalPrice());
            counter++;
        }

        DecimalFormat decimalFormat =new DecimalFormat("##.##");
        System.out.println("Delivery price:" + decimalFormat.format(orderDto.getDeliveryPrice()) +" Distance from Store:" + decimalFormat.format(orderDto.getDistanceFromSource()) + " Price for Km:" +orderDto.getPPK());

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
            System.out.println("Please enter your x cordinate");
            String userChoice = scanner.nextLine();
            try {
                x = Integer.parseInt(userChoice);
                System.out.println("Please enter your y cordinate");
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
}
