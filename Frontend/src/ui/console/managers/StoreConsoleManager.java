package ui.console.managers;

import dtoModel.ItemDto;
import dtoModel.OrderDto;
import dtoModel.StoreDto;
import viewModel.ItemViewModel;
import viewModel.StoreViewModel;
import ui.console.menu.MenuItem;
import ui.console.menu.UpdateStoreMenu;
import ui.console.utils.*;
import java.util.Collection;
import java.util.Scanner;

import static ui.console.Utils.FormatUtils.messageFormat;

public class StoreConsoleManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();
    private UpdateStoreMenu m_UpdateStoreMenu;
    private ItemViewModel m_ItemViewModel = new ItemViewModel();

    public int getStoreIdFromUser()
    {
        Collection<StoreDto> stores = m_StoreViewModel.getAllStores();
        int counter=1;

        StringBuilder storesDetails = new StringBuilder();
        storesDetails.append("Stores:").append("\n");
        for (StoreDto storeDto:stores) {
            storesDetails.append(counter).append(".\n")
                    .append(UniquelyUtil.getIdString(storeDto.getId())).append("\n")
                    .append(UniquelyUtil.getNameString(storeDto.getName())).append("\n")
                    .append(StoreDtoUtil.getPricePerKmString(storeDto.getPPK())).append("\n");
            counter++;
        }
        return getStoreIDFromUser(storesDetails.toString());

    }

    private int getStoreIDFromUser(String i_StoreDetails){
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr = null;
        int userChoice;

        System.out.println(i_StoreDetails);

        while (true) {
            System.out.println("Please enter the Id of the desired store:");
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println(messageFormat("Error: \"" + userChoiceStr +"\" is not number"));
                continue;
            }
            if(!m_StoreViewModel.isStoreIDExistInTheSystem(userChoice)){
                System.out.println(messageFormat("Error: store ID: \"" + userChoice +"\" is not available in the system"));
                continue;
            }
            return userChoice;
        }
    }

    public int getStoreIdBasicDetailsFromUser(){
        Collection<StoreDto> stores = m_StoreViewModel.getAllStores();
        int counter=1;

        StringBuilder storesDetails = new StringBuilder();
        storesDetails.append("Stores:").append("\n");
        for (StoreDto storeDto:stores) {
            storesDetails.append(counter).append(".\n")
                    .append(UniquelyUtil.getIdString(storeDto.getId())).append("\n")
                    .append(UniquelyUtil.getNameString(storeDto.getName())).append("\n");
            counter++;
        }
        return getStoreIDFromUser(storesDetails.toString());
    }
    public void ShowAllStores()
    {
        int counter =1;
        System.out.println("\n" + "Stores details:");
        StringBuilder storeDetails = new StringBuilder();
        for (StoreDto storeDto:m_StoreViewModel.getAllStores()
             ) {
            storeDetails.append(counter)
                    .append(")\n")
                    .append(UniquelyUtil.getIdString(storeDto.getId()))
                    .append("\n")
                    .append(UniquelyUtil.getNameString(storeDto.getName()))
                    .append("\n")
                    .append("- Items:\n")
                    .append(getAllItemsOfStore(storeDto))
                    .append("\n")
                    .append("- Orders details:\n")
                    .append(getOrdersOfStore(storeDto))
                    .append("\n")
                    .append(StoreDtoUtil.getPricePerKmString(storeDto.getPPK()))
                    .append("\n")
                    .append(StoreDtoUtil.getIncomesFromDeliveriesString(storeDto.getDeliveriesIncomes()))
                    .append("\n\n");
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
                    .append(OrderDtoUtil.getItemsPriceString(orderDto.getTotalItemsPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getDeliveryPriceString(orderDto.getDeliveryPrice()))
                    .append("\n")
                    .append(OrderDtoUtil.getTotalOrderPriceString(orderDto.getTotalOrderPrice()))
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
        int storeID = getStoreIdBasicDetailsFromUser();
        m_UpdateStoreMenu = new UpdateStoreMenu(
                new MenuItem("Delete item", () -> deleteItemFromStore(storeID)),
                new MenuItem("Insert new item",() -> insertNewItemToStore(storeID)),
                new MenuItem("Change item price",() -> changeStoreItemPrice(storeID)),
                new MenuItem("Back to Main Menu", () -> System.out.println("All updates were saved!\n")));
        m_UpdateStoreMenu.run();
    }

    private void deleteItemFromStore(int i_StoreID) {
        int itemID = getStoreItemIDFromUser(i_StoreID);
        try {
            m_ItemViewModel.deleteItemFromStore(i_StoreID, itemID);
            System.out.println(messageFormat("Item ID: " + itemID + " deleted Successfully!"));
        }catch (Exception e){
            System.out.println(messageFormat(e.getMessage()));
        }
    }

    private void insertNewItemToStore(int i_StoreID) {
        int newItemID = getNewItemIDFromUser();
        double newItemPrice = getPriceForNewItemFromUser();
        try {
            m_ItemViewModel.addNewItemToStore(i_StoreID, newItemID, newItemPrice);
            System.out.println(messageFormat("Item ID: " + newItemID + " with the price: " + newItemPrice + " was Successfully added to the requested store"));
        }catch (Exception e){
            System.out.println(messageFormat(e.getMessage()));
        }
    }

    private double getPriceForNewItemFromUser() {
        String messageStr = "Enter price for the new item:";
        return getPriceFromUser(messageStr);
    }


    private double getNewPriceFromUser() {
        String messageStr = "Enter the new price:";
        return getPriceFromUser(messageStr);
    }

    private double getPriceFromUser(String i_MsgStr)
    {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr = null;
        double userChoice;

        while (true) {
            System.out.println(i_MsgStr);
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Double.parseDouble(userChoiceStr);
                if(userChoice < 0){
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println(messageFormat("Error:\"" + userChoiceStr +"\" is not a positive number"));
                continue;
            }
            return userChoice;
        }
    }

    private int getNewItemIDFromUser() {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr = null;
        int userChoice;

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
        System.out.println("Items:");
        System.out.println(res.toString());

        while (true) {
            try {
                System.out.println("Please enter the Id of the desired item:");
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println(messageFormat("Error: \"" + userChoiceStr +"\" is not number"));
                continue;
            }
            if(!m_ItemViewModel.isItemExistInTheSystem(userChoice)){
                System.out.println(messageFormat("Error: item ID: \"" + userChoice +"\" is not available in the system"));
                continue;
            }
            return userChoice;
        }
    }

    private void changeStoreItemPrice(int i_StoreID){
        int storeItemID = getStoreItemIDFromUser(i_StoreID);
        double newPrice = getNewPriceFromUser();
        m_StoreViewModel.updateStoreItemPrice(i_StoreID, storeItemID, newPrice);
        System.out.println(messageFormat("The price for item ID: " + storeItemID + " has been successfully updates to " + newPrice));
    }

    private int getStoreItemIDFromUser(int i_StoreID) {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr = null;
        int userChoice;

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
        System.out.println("Items:");
        System.out.println(res.toString());

        while (true) {
            System.out.println("Please enter the Id of the desired item:");
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println(messageFormat("Error: \"" + userChoiceStr +"\" is not number"));
                continue;
            }
            if(!m_ItemViewModel.isStoreItemIDBelongToTheStore(i_StoreID, userChoice)){
                System.out.println(messageFormat("Error: item ID: \"" + userChoice +"\" does not belong to the store ID: \"" + i_StoreID + "\""));
                continue;
            }
            return userChoice;
        }
    }
}

