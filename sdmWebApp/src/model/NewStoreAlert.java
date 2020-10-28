package model;

import java.awt.*;

public class NewStoreAlert extends Alert {

   private String ownerName;
   private String storeName;
   private Point location;
   private String availableItemsFromAllItems;

    public NewStoreAlert(String ownerName, String storeName, Point location, String availableItemsFromAllItems) {
        this.ownerName = ownerName;
        this.storeName = storeName;
        this.location = location;
        this.availableItemsFromAllItems = availableItemsFromAllItems;
    }

    @Override
    public AlertType getType() {
        return AlertType.NEW_STORE;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getAvailableItemsFromAllItems() {
        return availableItemsFromAllItems;
    }

    public void setAvailableItemsFromAllItems(int numberOfItemsForSell, int totalItems) {
        this.availableItemsFromAllItems = numberOfItemsForSell + "\\" + totalItems;
    }
}
