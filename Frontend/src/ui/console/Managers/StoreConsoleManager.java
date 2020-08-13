package ui.console.Managers;

import DtoModel.ItemDto;
import DtoModel.OrderDto;
import DtoModel.StoreDto;
import ViewModel.StoreViewModel;
import ui.console.Utils.*;

import java.util.Collection;
import java.util.Scanner;

public class StoreConsoleManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();

    public int getStoreIdFromUser()
    {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Integer userChoice = null;

        Collection<StoreDto> stores = m_StoreViewModel.getAllStores();
        int counter=1;
        System.out.println("Please enter the Id of the desired store");
        for (StoreDto storeDto:stores
        ) {
            System.out.println(counter+".\n"+
                    UniquelyUtil.getIdString(storeDto.getId()) +"\n"
                    +UniquelyUtil.getNameString(storeDto.getName()) + "\n"
                    + StoreDtoUtil.getPricePerKmString(storeDto.getPPK()));
            counter++;
        }
        while (true) {
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error:\"" + userChoice +"\" is not number");
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
}
