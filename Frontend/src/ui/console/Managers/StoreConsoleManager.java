package ui.console.Managers;

import ViewModel.StoreViewModel;
import java.util.Collection;
import java.util.Scanner;

public class StoreConsoleManager {
    private StoreViewModel m_StoreViewModel = new StoreViewModel();

    public int getStoreIdFromUser()
    {
        Scanner scanner = new Scanner(System.in);
        String userChoiceStr;
        Integer userChoice = null;

        Collection<StoreViewModel.StoreDto> stores = m_StoreViewModel.getAllStores();
        int counter=1;
        System.out.println("Please enter the Id of the desired store");
        for (StoreViewModel.StoreDto store:stores
        ) {
            System.out.println(counter+". "+ "Id:" + store.getId() +"\nName:" + store.getStoreName() + "\nPrice Per Km:" +store.getPPK());
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
}
