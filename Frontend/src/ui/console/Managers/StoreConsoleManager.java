package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StoreDto;
import ViewModel.ItemViewModel;
import ViewModel.StoreViewModel;
import ui.console.Menu.MenuItem;
import ui.console.Menu.UpdateStoreMenu;
import ui.console.Utils.*;

import java.util.Collection;
import java.util.Scanner;

public class StoreConsoleManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();
    private UpdateStoreMenu m_UpdateStoreMenu;
    private ItemViewModel m_ItemViewModel = new ItemViewModel();

    public int getStoreIdFromUser()
    {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Integer userChoice = null;

        Collection<StoreDto> stores = m_StoreViewModel.getAllStores();
        int counter=1;

        for (StoreDto storeDto:stores
        ) {
            System.out.println(counter+".\n"+
                    UniquelyUtil.getIdString(storeDto.getId()) +"\n"
                    +UniquelyUtil.getNameString(storeDto.getName()) + "\n"
                    + StoreDtoUtil.getPricePerKmString(storeDto.getPPK()));
            counter++;
        }
        while (true) {
            System.out.println("Please enter the Id of the desired store");
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: \"" + userChoice +"\" is not number");
                continue;
            }
            if(!m_StoreViewModel.isStoreIDExistInTheSystem(userChoice)){
                System.out.println("Error: store ID: \"" + userChoice +"\" is not available in the system");
                continue;
            }
            return userChoice;
        }
    }

    public void ShowAllStores()
    {
        int counter =1;
        System.out.println("Stores details:");
        StringBuilder storeDetails = new StringBuilder();
        for (StoreDto storeDto:m_StoreViewModel.getAllStores()
             ) {
            storeDetails.append(counter)
                    .append(".\n")
                    .append(UniquelyUtil.getIdString(storeDto.getId()))
                    .append("\n")
                    .append(UniquelyUtil.getNameString(storeDto.getName()))
                    .append("\n")
                    .append("- Items:\n")
                    .append(getAllItemsOfStore(storeDto))
                    .append("\n")
                    .append("- Orders form the store:\n")
                    .append(getOrdersOfStore(storeDto))
                    .append("\n")
                    .append(StoreDtoUtil.getPricePerKmString(storeDto.getPPK()))
                    .append("\n")
                    .append(StoreDtoUtil.getIncomesFromDeliveriesString(storeDto.getDeliveriesIncomes()))
                    .append("\n");
            counter++;
        }

        System.out.println(storeDetails);
    }

    private String getOrdersOfStore(StoreDto i_StoreDto) {
        int counter =1;
        StringBuilder ordersDetails= new StringBuilder();
        for (OrderDto orderDto: i_StoreDto.getAllOrders()
        ) {
            ordersDetails.append(counter)
                    .append(".\n")
                    .append(OrderDtoUtil.getDateString(orderDto.getDate()))
                    .append("\n")
                    .append(OrderDtoUtil.getItemsCountString(orderDto.getTotalItemsCount()))
                    .append("\n")
                    .append(OrderDtoUtil.getItemsPrice(orderDto.getTotalItemsPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getDeliveryPrice(orderDto.getDeliveryPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalOrderPrice(orderDto.getTotalOrderPrice()))
                    .append("\n");
            counter++;
        }

        if(counter==1)
        {
            ordersDetails.append("No orders to present from this store");
        }

        return ordersDetails.toString();
    }

    private String getAllItemsOfStore(StoreDto i_StoreDto)
    {
        int counter =1;
        StringBuilder itemsDetails = new StringBuilder();
        for (ItemDto itemDto:i_StoreDto.getItemsDto()
             ) {
            itemsDetails.append(counter).append(".\n")
                    .append(ItemDtoUtils.getBasicItemString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getPriceString(itemDto))
                    .append("\n")
                    .append(ItemDtoUtils.getAmountOfSellsString(itemDto.getAmountOfSell()))
                    .append("\n");
            counter++;
        }
        return itemsDetails.toString();
    }

    public void updateStoreItemsMenu(){
        int storeID = getStoreIdFromUser();
        m_UpdateStoreMenu = new UpdateStoreMenu(
                new MenuItem("Delete item", () -> deleteItemFromStore(storeID)),
                new MenuItem("Insert new item",() -> insertNewItemToStore(storeID)),
                new MenuItem("Change item price",() -> changeStoreItemPrice(storeID)),
                new MenuItem("Back to Main Menu", () -> System.out.println("All updates were saved!!!!")));
        m_UpdateStoreMenu.run();
    }

    private void deleteItemFromStore(int i_StoreID) {
        int itemID = getStoreItemIDFromUser(i_StoreID);
        try {
            m_ItemViewModel.deleteItemFromStore(i_StoreID, itemID);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    private void insertNewItemToStore(int i_StoreID) {
        int newItemID = getNewItemIDFromUser();
        double newItemPrice = getPriceForNewItemFromUser();
        try {
            m_ItemViewModel.addNewItemToStore(i_StoreID, newItemID, newItemPrice);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    private double getPriceForNewItemFromUser() {
        System.out.println("Enter price for the new item:");

        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Double userChoice = null;

        while (true) {
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Double.parseDouble(userChoiceStr);
                if(userChoice < 0){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error:\"" + userChoice + "\" is not a positive number");
                continue;
            }
            return userChoice;
        }
    }

    private int getNewItemIDFromUser() {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Integer userChoice = null;

        Collection<ItemDto> items = m_ItemViewModel.getAllItems();
        int counter=1;
        StringBuilder res = new StringBuilder();

        for(ItemDto itemDto: items){
            res.append(counter).append(". ")
                    .append("\n")
                    .append("- ID: ").append(itemDto.getId())
                    .append("\n")
                    .append("- Name: ").append(itemDto.getItemName())
                    .append("\n")
                    .append("- Purchase Form: ").append(itemDto.getPurchaseForm())
                    .append("\n");
            counter++;
        }

        System.out.println(res.toString());

        while (true) {
            try {
                System.out.println("Please enter the Id of the desired item:");
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: \"" + userChoice +"\" is not number");
                continue;
            }
            if(!m_ItemViewModel.isItemExistInTheSystem(userChoice)){
                System.out.println("Error: item ID: \"" + userChoice +"\" is not available in the system");
                continue;
            }
            return userChoice;
        }
    }

    private void changeStoreItemPrice(int i_StoreID){
        int storeItemID = getStoreItemIDFromUser(i_StoreID);
        double newPrice = getNewPriceFromUser();
        m_StoreViewModel.updateStoreItemPrice(i_StoreID, storeItemID, newPrice);
    }

    private double getNewPriceFromUser() {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Double userChoice = null;

        System.out.println("Enter the new price:");

        while (true) {
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Double.parseDouble(userChoiceStr);
                if(userChoice < 0){
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error:\"" + userChoice +"\" is not number");
                continue;
            }
            return userChoice;
        }

    }

    private int getStoreItemIDFromUser(int i_StoreID) {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr = null;
        Integer userChoice;

        Collection<ItemDto> items = m_ItemViewModel.getAllItemsOfStore(i_StoreID);
        int counter=1;
        StringBuilder res = new StringBuilder();

        for(ItemDto itemDto: items){
            res.append(counter).append(". ")
                    .append("\n")
                    .append("- ID: ").append(itemDto.getId())
                    .append("\n")
                    .append("- Name: ").append(itemDto.getItemName())
                    .append("\n")
                    .append("- Price: ").append(itemDto.getPrice())
                    .append("\n");
            counter++;
        }

        System.out.println(res.toString());

        while (true) {
            System.out.println("Please enter the Id of the desired item:");
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: \"" + userChoiceStr +"\" is not number");
                continue;
            }
            if(!m_ItemViewModel.isStoreItemIDBelongToTheStore(i_StoreID, userChoice)){
                System.out.println("Error: item ID: \"" + userChoice +"\" does not belong to the store ID: \"" + i_StoreID);
                continue;
            }
            return userChoice;
        }
    }
}

