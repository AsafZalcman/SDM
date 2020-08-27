package ui.console.managers;
import ui.console.menu.MainMenu;
import ui.console.menu.MenuItem;
import managers.SuperDuperManager;
import static ui.console.utils.FormatUtils.messageFormat;
import java.util.*;

public class ConsoleManager {

    private MainMenu m_MainMenu;
    private SuperDuperManager m_SuperDuperManager;
    private boolean b_IsDataLoaded;
    private OrderConsoleManager m_OrderConsoleManager;
    private ItemConsoleManager m_itemConsoleManager;
    private StoreConsoleManager m_StoreConsoleManager;

    public void run() {
        m_MainMenu.run();
    }

    public ConsoleManager() {
        b_IsDataLoaded = false;
        m_SuperDuperManager = SuperDuperManager.getInstance();
        m_OrderConsoleManager= new OrderConsoleManager();
        m_itemConsoleManager = new ItemConsoleManager();
        m_StoreConsoleManager= new StoreConsoleManager();

        m_MainMenu = new MainMenu(
                new MenuItem(" Load system data from xml file", this::loadSuperDuperMarketXmlFile),
                new MenuItem(" View details of all stores in the system", this::showAllShops),
                new MenuItem(" View details of all items in the system", this::showAllItems),
                new MenuItem(" Make an order", this::makeAnOrder),
                new MenuItem(" View orders history", this::showOrdersHistory),
                new MenuItem(" Update store items/prices", this::updateStoreItems),
                new MenuItem(" Exit", () -> System.out.println("See you next time!!!!")));
    }

    private void loadSuperDuperMarketXmlFile() {
        System.out.println("Please enter full path to your xml file:");
        Scanner scanner = new Scanner(System.in);
        String pathToFile = scanner.nextLine();
        try {
            m_SuperDuperManager.loadSuperDuperDataFromXml(pathToFile);
            b_IsDataLoaded = true;
            System.out.println(messageFormat("Xml file was loaded successfully"));
        } catch (Exception e) {
            System.out.println(messageFormat("Error: " + e.getMessage()));
        }
    }

    private void showAllShops() {
        if (isDataLoaded()) {
            m_StoreConsoleManager.ShowAllStores();
        }
    }

    private void showAllItems() {
        if (isDataLoaded()) {
            m_itemConsoleManager.showAllStorageItems();
        }
    }

    private void makeAnOrder(){
        if (isDataLoaded()) {
            m_OrderConsoleManager.MakeAnOrder();
        }
    }

    private void showOrdersHistory() {
        if (isDataLoaded()) {
            m_OrderConsoleManager.ShowAllOrdersHistory();
        }
    }

    private boolean isDataLoaded() {
        if (!b_IsDataLoaded) {
            System.out.println(messageFormat("Error: You can not choose this option before you loaded a data to the system"));
        }

        return b_IsDataLoaded;
    }

    private void updateStoreItems(){
        if (isDataLoaded()) {
            m_StoreConsoleManager.updateStoreItemsMenu();
        }
    }
}
