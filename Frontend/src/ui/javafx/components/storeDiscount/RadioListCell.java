package ui.javafx.components.storeDiscount;

import dtoModel.StoreOfferDto;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class RadioListCell extends DiscountListCell {

    private ToggleGroup m_ToggleGroup;
    public RadioListCell(ToggleGroup i_ToggleGroup)
    {
        super();
        m_ToggleGroup=i_ToggleGroup;
    }
    @Override
    public void updateItem(StoreOfferDto obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setGraphic(null);
        }
        else {
            RadioButton radioButton = new RadioButton(getText() +",For additional:"+obj.getForAdditional());
            radioButton.setToggleGroup(m_ToggleGroup);
            // Add Listeners if any
            setGraphic(radioButton);
        }
    }
}
