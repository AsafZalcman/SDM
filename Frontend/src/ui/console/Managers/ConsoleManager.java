package ui.console.Managers;

import ui.console.Menu.MainMenu;
import ui.console.Menu.MenuItem;
import ui.console.Menu.SubMenu;
import utils.SuperDuperManager;

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
                new MenuItem("initialize system", this::loadSuperDuperMarketXmlFile),
                new MenuItem("show all shops", this::showAllShops),
                new MenuItem("show all items", this::showAllItems),
                new SubMenu("make an order", "choose the order way",
                        new MenuItem("from specific shop",this::makeStaticOrder),
                        new MenuItem("mix shops with the lower cost",this::makeDynamicOrder),
                        new MenuItem("Back",()->{})),
                new MenuItem("show orders history", this::showOrdersHistory),
                new MenuItem("Exit", () -> System.out.println("see you next time!!!!")));
    }

    private void loadSuperDuperMarketXmlFile() {
        System.out.println("Please enter full path to you xml file");
       // Scanner scanner = new Scanner(System.in);
       // String pathToFile = scanner.nextLine();
        try {
            m_SuperDuperManager.loadSuperDuperDataFromXml("C:\\Users\\asafz\\IdeaProjects\\SDM\\Backend\\src\\resources\\xml\\ex1-small.xml");
            b_IsDataLoaded = true;
            System.out.println("Xml file was loaded");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    private void makeStaticOrder(){
        if (isDataLoaded()) {
            m_OrderConsoleManager.MakeStaticOrder();
        }
    }

    private void makeDynamicOrder()
    {
        if (isDataLoaded()) {
            m_OrderConsoleManager.MakeDynamicOrder();
        }
    }

    private void showOrdersHistory() {
        if (isDataLoaded()) {
            m_OrderConsoleManager.ShowAllOrdersHistory();
        }
    }

    private boolean isDataLoaded() {
        if (!b_IsDataLoaded) {
            System.out.println("You cant choose this option before you loaded a data to the system");
        }

        return b_IsDataLoaded;
    }

}
