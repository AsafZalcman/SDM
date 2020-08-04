package ui.console;

public class MainMenu extends abstractMenu {

    public MainMenu(IMenuComponent... items) {
        super(items);
    }

    public void run() {
        while (!isUserPressExit()) {
            super.run();
        }
    }

    @Override
    String getMenuTitle() {
        return "Welcome to the main menu";
    }
}
