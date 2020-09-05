package ui.javafx.utils;

import java.net.URL;

public class SDMResourcesConstants {

    private static final String BASE_PACKAGE = "/ui/javafx/components";
    private static final  String STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/storeDiscount/storeDiscount.fxml";

    public static final String MAIN_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/main/SDM.fxml";
    public static final URL STORE_DISCOUNT_FXML_RESOURCE = SDMResourcesConstants.class.getResource(STORE_DISCOUNT_FXML_RESOURCE_IDENTIFIER);

}
