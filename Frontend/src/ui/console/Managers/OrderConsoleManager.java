package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StorageOrderDto;
import DtoModel.StoreDto;
import ViewModel.OrderViewModel;
import myLocation.LocationException;
import ui.console.Menu.MenuItem;
import ui.console.Menu.SubMenu;
import ui.console.Utils.*;

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
    private SubMenu m_OrderOptionsMenu;

    public OrderConsoleManager() {
       scanner = new Scanner(System.in);
        m_StoreConsoleManager = new StoreConsoleManager();
        m_ItemConsoleManager = new ItemConsoleManager();
        m_OrderViewModel = new OrderViewModel();
        m_OrderOptionsMenu = new SubMenu("", "choose order type",
                new MenuItem("from specific shop",this::makeStaticOrder),
                new MenuItem("mix shops with the lower cost",this::makeDynamicOrder));
    }

    public void MakeAnOrder()
   {
       getOrderDateFromUser();
       getUserLocation();
       m_OrderOptionsMenu.selected();
       approveOrder();

   }
    private void makeStaticOrder(){
        getStoreIdFromUser();
        addItemsToStaticOrder();
    }

    private void makeDynamicOrder()
    {
        addItemsToDynamicOrder();
        showDynamicOrderDetails();
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
        StorageOrderDto storageOrderDto = m_OrderViewModel.getCurrentOrder();
        int counter =1;
        System.out.println("Order summary:");
        StringBuilder ordersDetails = new StringBuilder();
        for (ItemDto itemDto:storageOrderDto.getOrderDto().getItemsDto()
        ) {
            ordersDetails.append(counter)
                    .append(".\n")
                    .append(ItemDtoUtils.getBasicItemString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getPriceString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getAmountOfSellsString(itemDto.getAmountOfSell()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalItemCostString(itemDto.getTotalPrice()))
                    .append("\n");
            counter++;
        }
        String additionalValuesForStaticOrder = storageOrderDto.isDynamicOrder()? "":OrderDtoUtil.getDistanceFromSourceString(storageOrderDto.getDistanceFromSource()) + "\n" + StoreDtoUtil.getPricePerKmString(storageOrderDto.getPPK()) + "\n";
        System.out.println("====================\n" +ordersDetails.toString() + OrderDtoUtil.getDeliveryPriceString(storageOrderDto.getOrderDto().getDeliveryPrice()) + "\n" + additionalValuesForStaticOrder + OrderDtoUtil.getTotalOrderPriceString(storageOrderDto.getOrderDto().getTotalOrderPrice()));
    }

    private void showDynamicOrderDetails()
    {
        StorageOrderDto storageOrderDto = m_OrderViewModel.getCurrentOrder();
        int counter =1;
        StringBuilder storeDetails = new StringBuilder( "- " + storageOrderDto.getNumberOfStores() + " Stores took part in this order with the following details:");
        for (Map.Entry<StoreDto,OrderDto> entry: storageOrderDto.getStoresToOrders().entrySet()
             ) {
            storeDetails.append(counter)
                    .append(".\n")
                    .append(UniquelyUtil.getIdString(entry.getKey().getId()))
                    .append("\n")
                    .append(UniquelyUtil.getNameString(entry.getKey().getName()))
                    .append("\n")
                    .append(StoreDtoUtil.getLocationString(entry.getKey().getLocation()))
                    .append("\n")
                    .append(OrderDtoUtil.getDistanceFromSourceString(entry.getKey().getLocation().distance(entry.getValue().getDestLocation())))
                    .append("\n")
                    .append(StoreDtoUtil.getPricePerKmString(entry.getKey().getPPK()))
                    .append("\n")
                    .append(OrderDtoUtil.getDeliveryPriceString(entry.getValue().getDeliveryPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getNumberOfDifferentItemsString(entry.getValue().getTotalItemsKind()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalOrderPriceString(entry.getValue().getTotalOrderPrice()))
                    .append("\n");
            counter++;
        }
        System.out.println(storeDetails.toString());
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
            storeDetails = storageOrderDto.isDynamicOrder() ?
                    "- Number of Stores that took part in this order: " + storageOrderDto.getNumberOfStores() :
                    "- Store id: " + storageOrderDto.getStoreId() + "\n- Store name: " + storageOrderDto.getStoreName();
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
                    .append(OrderDtoUtil.getTotalOrderPriceString(tempOrder.getTotalItemsPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getDeliveryPriceString(tempOrder.getDeliveryPrice())).append("\n")
                    .append(OrderDtoUtil.getTotalOrderPriceString(tempOrder.getTotalOrderPrice()));
            counter++;
        }
        if(counter==1)
        {
            orderDetails.append("No orders to show\n");
        }

        System.out.println(orderDetails.toString());
    }
}
