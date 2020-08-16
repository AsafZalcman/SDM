package ui.console.Menu;

public class UpdateStoreMenu extends abstractMenu {
    public UpdateStoreMenu(IMenuComponent... items) {
        super(items);
    }

    public void run() {
        while (!isUserPressExit()) {
            super.run();
        }
    }

    @Override
    String getMenuTitle() {
        return "Welcome to the update store menu";
    }
}
