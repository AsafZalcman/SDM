package ui.javafx.utils;

import java.net.URL;

public class SDMResourcesConstants {

    private static final String BASE_PACKAGE = "/ui/javafx/components";
    private static final String STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/storeDiscount/storeDiscount.fxml";
    private static final String ORDER_STORE_SUMMARY_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/storeSummary/OrderStoreSummary.fxml";
    private static final  String ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/createOrder/steps/insertDetails/InsertDetails.fxml";
    private static final  String ORDER_STEPS_BUY_ITEMS_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/createOrder/steps/buyItems/BuyItems.fxml";
    private static final  String ORDER_STEPS_ADD_DISCOUNTS_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/createOrder/steps/addDiscounts/AddDiscounts.fxml";
    private static final String CREATE_ORDER_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/createOrder/CreateOrder.fxml";
    public static final String MAIN_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/main/SDM.fxml";

    public static final URL  CREATE_ORDER_FXML_RESOURCE = SDMResourcesConstants.class.getResource( CREATE_ORDER_FXML_RESOURCE_IDENTIFIER);
    public static final URL STORE_DISCOUNT_FXML_RESOURCE = SDMResourcesConstants.class.getResource(STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER);
    public static final URL ORDER_STORE_SUMMARY_FXML_RESOURCE = SDMResourcesConstants.class.getResource(ORDER_STORE_SUMMARY_FXML_RESOURCE_IDENTIFIER);
    public static final URL ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE = SDMResourcesConstants.class.getResource(ORDER_STEPS_INSERT_DETAILS_FXML_RESOURCE_IDENTIFIER);
    public static final URL ORDER_STEPS_BUY_ITEMS_FXML_RESOURCE = SDMResourcesConstants.class.getResource(ORDER_STEPS_BUY_ITEMS_FXML_RESOURCE_IDENTIFIER);
    public static final URL ORDER_STEPS_ADD_DISCOUNTS_FXML_RESOURCE = SDMResourcesConstants.class.getResource(ORDER_STEPS_ADD_DISCOUNTS_FXML_RESOURCE_IDENTIFIER);


}
