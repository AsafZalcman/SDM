package model;

public class StoreFeedbackAlert extends Alert {

    private int rank;
    private  String description;
    private String ownerName;
    private String  date;
    private String  storeName;


    public StoreFeedbackAlert(int rank, String description, String ownerName, String date,String storeName) {
        this.rank = rank;
        this.description = description;
        this.ownerName = ownerName;
        this.date = date;
        this.storeName=storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public AlertType getType() {
        return AlertType.FEEDBACK;
    }
}
