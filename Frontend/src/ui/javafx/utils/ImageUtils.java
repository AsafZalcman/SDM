package ui.javafx.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
    public static final String RESOURCES_FOLDER = "/ui/javafx/resources/";
    public static final String IMAGE_FOLDER = RESOURCES_FOLDER + "images/";
    public static final String CUSTOMER_IMAGE = "icons8-user-50.png";
    public static final String STORE_IMAGE = "icons8-small-business-50.png";
    public static final String MAP_IMAGE = "mapImg.jpg";
    public static final String CART_IMAGE = "icons8-shopping-cart-50.png";

    public static Image getImage (String imageName){
        InputStream imageInputStream = ImageUtils.class.getResourceAsStream(IMAGE_FOLDER + imageName);
        return new Image(imageInputStream);
    }

    public static ImageView getImageView (String imageName){
        return new ImageView(getImage(imageName));
    }

    public static ImageView getCustomerIcon(){
        return getImageView(CUSTOMER_IMAGE);
    }

    public static ImageView getStoreIcon(){
        return getImageView(STORE_IMAGE);
    }

    public static Image getCartIcon(){ return getImage(CART_IMAGE); }
}
