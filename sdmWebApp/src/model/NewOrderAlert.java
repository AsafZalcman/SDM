package model;


public class NewOrderAlert extends Alert {

    private int orderId;
   private String ownerName;
    private String storeName;
    private int numberOfDifferentItems;
   private double totalPriceOfItems;
    private double deliveryPrice;

    public NewOrderAlert(int orderId, String ownerName, int numberOfDifferentItems, double totalPriceOfItems, double deliveryPrice , String storeName) {
        this.orderId = orderId;
        this.ownerName = ownerName;
        this.numberOfDifferentItems = numberOfDifferentItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.deliveryPrice = deliveryPrice;
        this.storeName = storeName;

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getNumberOfDifferentItems() {
        return numberOfDifferentItems;
    }

    public void setNumberOfDifferentItems(int numberOfDifferentItems) {
        this.numberOfDifferentItems = numberOfDifferentItems;
    }

    public double getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public void setTotalPriceOfItems(double totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Override
    public AlertType getType() {
       return AlertType.NEW_ORDER;
    }
}
