package ui.javafx.components.storeDiscount;

import dtoModel.StoreDiscountDto;
import dtoModel.StoreDto;
import dtoModel.StoreOfferDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.utils.FormatUtils;

public class DiscountController {

    @FXML
    private Label discountConditionLabel;

    @FXML
    private ListView<StoreOfferDto> discountOfferListView;

    @FXML
    private Label forAdditionalLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label thenYouGetLabel;

    @FXML
    private Label discountNameLabel;

    private StoreDiscountDto m_StoreDiscountDto;

    private SimpleBooleanProperty isStoreDiscountAvailable;

    private ToggleGroup m_ToggleGroup;

    private boolean m_WithButtons = false;

    private ItemsUIManger m_ItemsUIManger;
    public void setStoreDiscountDto(StoreDiscountDto i_StoreDiscountDto) {
        m_StoreDiscountDto = i_StoreDiscountDto;
        isStoreDiscountAvailable.set(true);
    }

    public DiscountController() {
        isStoreDiscountAvailable = new SimpleBooleanProperty(false);
        m_ToggleGroup = new ToggleGroup();
        m_ItemsUIManger= new ItemsUIManger();
    }

    public void setWithButtons(boolean withButtons) {
        m_WithButtons = withButtons;
    }

    @FXML
    private void initialize() {
        isStoreDiscountAvailable.addListener((observable, oldValue, newValue) -> {
            discountOfferListView.getItems().addAll(m_StoreDiscountDto.getStoreOfferDtos());
            discountConditionLabel.textProperty().set(FormatUtils.DecimalFormat.format(m_StoreDiscountDto.getDiscountCondition().getValue()) + " " +m_ItemsUIManger.getItemDtoById(m_StoreDiscountDto.getDiscountCondition().getKey()).getItemName());
            discountNameLabel.textProperty().set(m_StoreDiscountDto.getName());
            if (m_StoreDiscountDto.isOneOfDiscount()) {
                thenYouGetLabel.setText(thenYouGetLabel.getText() + " one of the following:");
            } else {
                if (m_StoreDiscountDto.getStoreOfferDtos().size() > 1) {
                    thenYouGetLabel.setText(thenYouGetLabel.getText() + " all of the following:");
                }
                forAdditionalLabel.visibleProperty().setValue(true);
                totalPriceLabel.visibleProperty().setValue(true);

                double totalPrice = m_StoreDiscountDto.getStoreOfferDtos().stream().mapToDouble(StoreOfferDto::getForAdditional).sum();
                totalPriceLabel.textProperty().set(FormatUtils.DecimalFormat.format(totalPrice));
            }

            discountOfferListView.setCellFactory(param -> new ListCell<StoreOfferDto>() {
                @Override
                protected void updateItem(StoreOfferDto storeOfferDto, boolean empty) {
                    super.updateItem(storeOfferDto, empty);

                    if (empty || storeOfferDto == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        StringBuilder storeOfferText = new StringBuilder(storeOfferDto.getQuantity() + " " + m_ItemsUIManger.getItemDtoById(storeOfferDto.getItemId()).getItemName());
                        if (m_StoreDiscountDto.isOneOfDiscount()) {
                            storeOfferText.append(" For additional of ")
                                    .append(storeOfferDto.getForAdditional());
                            if (m_WithButtons) {
                                RadioButton radioButton = new RadioButton(null);
                                radioButton.setToggleGroup(m_ToggleGroup);
                                // Add Listeners if any
                                setGraphic(radioButton);
                            }
                        }
                        setText(storeOfferText.toString());
                    }
                }
            });
        });
    }

}
