package ui.javafx.components.storeDiscount;

import dtoModel.StoreOfferDto;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;

public class DiscountListCell extends ListCell<StoreOfferDto> {

    @Override
    public void updateItem(StoreOfferDto obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setText(null);
        }
        else {
            setText("Itemd id:" +obj.getItemId() + ",Quantity:"+obj.getQuantity());
        }
    }
}
