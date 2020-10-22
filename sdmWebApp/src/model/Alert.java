package model;

public interface Alert {

    AlertType getType();
    public static enum AlertType
    {
        NEW_STORE,FEEDBACK,ORDER_IN_STORE
    }
}
