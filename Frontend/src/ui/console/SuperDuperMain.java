package ui.console;

import utils.SuperDuperManager;
import xml.XmlManager;

import java.io.FileNotFoundException;

public class SuperDuperMain {
    public static void main(String[] args) {
        MenuItem XML_item = new MenuItem("initialize system", () -> loadSuperDuperMarketXmlFile());
        MainMenu menu = new MainMenu(
                XML_item,
                new MenuItem("show all shops", () -> System.out.println("not implemented")),
                new MenuItem("show all items", () -> System.out.println("not implemented")),
                new SubMenu("make an order", "choose the order way",
                        XML_item,
                        new MenuItem("from specific shop", () -> System.out.println("not implemented")),
                        new MenuItem("mix shops with the lower cost", () -> System.out.println("not implemented")),
                        new MenuItem("Back", () -> {
                        })),//empty method is weird
                new MenuItem("show orders history", () -> System.out.println("not implemented")),
                new MenuItem("Exit", () -> System.out.println("see you next time!!!!")));
        //maybe "back" button should be a default in subMenu
        menu.run();
    }

    private static  void loadSuperDuperMarketXmlFile()
    {
        SuperDuperManager superDuperManager = new SuperDuperManager();
        try {
            superDuperManager.loadSuperDuperDataFromXml("C:\\Users\\asafz\\Downloads\\ex1-small.xml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(superDuperManager);

    }

}
