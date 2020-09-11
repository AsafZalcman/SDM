package ui.javafx.components.storeDiscount;

import dtoModel.StoreOfferDto;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class BuyDiscountController extends DiscountController {
    private ToggleGroup m_ToggleGroup = new ToggleGroup();

//   @Override
//   protected void setCellFactoryOfListView() {
//       discountOfferListView.setCellFactory(param -> new ListCell<StoreOfferDto>() {
//           @Override
//           protected void updateItem(StoreOfferDto storeOfferDto, boolean empty) {
//               super.updateItem(storeOfferDto, empty);

//               if (!empty && storeOfferDto != null) {
//                   RadioButton radioButton = new RadioButton(null);
//                   radioButton.setToggleGroup(m_ToggleGroup);
//                   // Add Listeners if any
//                   setGraphic(radioButton);
//               }
//           }
//       });
//   }
}