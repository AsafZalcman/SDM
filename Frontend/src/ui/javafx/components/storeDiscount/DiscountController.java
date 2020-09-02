package ui.javafx.components.storeDiscount;

import dtoModel.StoreDiscountDto;
import dtoModel.StoreOfferDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
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

    public void setStoreDiscountDto(StoreDiscountDto i_StoreDiscountDto)
    {
        m_StoreDiscountDto=i_StoreDiscountDto;
        isStoreDiscountAvailable.set(true);
    }

    public DiscountController() {
        isStoreDiscountAvailable = new SimpleBooleanProperty(false);
        m_ToggleGroup = new ToggleGroup();
    }

    @FXML
    private void initialize() {
        isStoreDiscountAvailable.addListener((observable, oldValue, newValue) -> {
            discountOfferListView.getItems().addAll(m_StoreDiscountDto.getStoreOfferDtos());
            discountConditionLabel.textProperty().set(FormatUtils.DecimalFormat.format(m_StoreDiscountDto.getDiscountCondition().getValue()) + " item with id:" + m_StoreDiscountDto.getDiscountCondition().getKey());
            discountNameLabel.textProperty().set(m_StoreDiscountDto.getName());
            if (m_StoreDiscountDto.isOneOfDiscount())
            {
                discountOfferListView.setCellFactory(param ->new RadioListCell(m_ToggleGroup));
                thenYouGetLabel.textProperty().set("Then you get one of the following:");
            }
            else
            {
                discountOfferListView.setCellFactory(param ->new DiscountListCell());
                forAdditionalLabel.visibleProperty().setValue(true);
                totalPriceLabel.visibleProperty().setValue(true);
                if(m_StoreDiscountDto.getStoreOfferDtos().size() >1)
                {
                    thenYouGetLabel.textProperty().set("Then you get all of the following:");
                }
                double totalPrice =m_StoreDiscountDto.getStoreOfferDtos().stream().mapToDouble(StoreOfferDto::getForAdditional).sum();
                totalPriceLabel.textProperty().set(FormatUtils.DecimalFormat.format(totalPrice));

            }
        });

    }

}
