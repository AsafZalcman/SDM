package ui.console.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class abstractMenu extends ArrayList<IMenuComponent> {
    private boolean isUserPressExit;

    public abstractMenu(IMenuComponent... items)
    {
        Collections.addAll(this, items);
    }
    protected void run() {
        System.out.println(getMenuTitle());
        isUserPressExit = false;
        showMenu();
        int userChoice = getUserChoice();
        isUserPressExit = userChoice == this.size();
        this.get(userChoice - 1).selected();

    }
    protected int getUserChoice()
    {
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        String userChoiceStr;
        while (true) {
            try {
                userChoiceStr = scanner.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);
            } catch (NumberFormatException e) {
                System.out.println("You must select a number between 1 to " + this.size() + ",please try again");

                continue;
            }
            if (userChoice <= 0 || userChoice > this.size()) {
                System.out.println("You must select a number between 1 to " + this.size() + ", please try again");
                continue;
            }
            return userChoice;
        }
    }
    private void showMenu()
    {
        System.out.println("Please choose one of the following options:");
        int counter=1;
        for (IMenuComponent item:this
        ) {
            System.out.println(counter + ". " + item.getTitle());
            counter++;
        }
    }
    abstract String getMenuTitle();

    public boolean isUserPressExit() {
        return isUserPressExit;
    }
}
