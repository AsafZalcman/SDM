package ui.javafx.utils;

import java.net.URL;

public class SDMResourcesConstants {

    private static final String BASE_PACKAGE = "/ui/javafx/components";
    private static final  String STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/storeDiscount/storeDiscount.fxml";
    private static final  String ORDER_STORE_SUMMARY_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/order/storeSummary/OrderStoreSummary.fxml";

    public static final String MAIN_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/main/SDM.fxml";
    public static final URL STORE_DISCOUNT_FXML_RESOURCE = SDMResourcesConstants.class.getResource(STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER);
    public static final URL ORDER_STORE_SUMMARY_FXML_RESOURCE = SDMResourcesConstants.class.getResource(ORDER_STORE_SUMMARY_FXML_RESOURCE_IDENTIFIER);

}
