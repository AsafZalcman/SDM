package model;

public abstract class Alert {

    public abstract AlertType  getType();
    private AlertType alertType = getType();

    public static enum AlertType
    {
        NEW_STORE,FEEDBACK, NEW_ORDER
    }
}
