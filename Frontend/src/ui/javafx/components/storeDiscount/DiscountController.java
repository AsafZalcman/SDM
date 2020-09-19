package ui.javafx.components.storeDiscount;

import dtoModel.StoreDiscountDto;
import dtoModel.StoreOfferDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

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
    private OrdersUIManager m_OrdersUIManager;
    private List<Runnable> m_ListenersAfterBuying;


    @FXML
    private Button buyDiscountButton;

    public void addListenerAfterBuying(Runnable i_Listener)
    {
        m_ListenersAfterBuying.add(i_Listener);
    }

    @FXML
    void buyDiscountButtonOnAction(ActionEvent event) {
        if (m_StoreDiscountDto.isOneOfDiscount()) {
            m_OrdersUIManager.addOneOfDiscountToOrder(m_StoreDiscountDto, (Integer) m_ToggleGroup.getSelectedToggle().getUserData());
        } else {
            m_OrdersUIManager.addDiscountToOrder(m_StoreDiscountDto);
        }
        for (Runnable listener:m_ListenersAfterBuying
             ) {
            listener.run();
        }
    }

    public void setStoreDiscountDto(StoreDiscountDto i_StoreDiscountDto) {
        m_StoreDiscountDto = i_StoreDiscountDto;
        isStoreDiscountAvailable.set(true);
    }

    public DiscountController() {
        isStoreDiscountAvailable = new SimpleBooleanProperty(false);
        m_ToggleGroup = new ToggleGroup();
        m_ItemsUIManger = new ItemsUIManger();
        m_OrdersUIManager = OrdersUIManager.getInstance();
    }

    public void setWithButtons(boolean withButtons) {
        m_WithButtons = withButtons;
        buyDiscountButton.setVisible(m_WithButtons);
        m_ListenersAfterBuying = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        isStoreDiscountAvailable.addListener((observable, oldValue, newValue) -> {
            discountOfferListView.getItems().addAll(m_StoreDiscountDto.getStoreOfferDtos());
            discountConditionLabel.textProperty().set(FormatUtils.DecimalFormat.format(m_StoreDiscountDto.getDiscountCondition().getValue()) + " " + m_ItemsUIManger.getItemDtoById(m_StoreDiscountDto.getDiscountCondition().getKey()).getItemName());
            discountNameLabel.textProperty().set(m_StoreDiscountDto.getName());

            if (m_StoreDiscountDto.isOneOfDiscount()) {
                thenYouGetLabel.setText(thenYouGetLabel.getText() + " one of the following:");
            } else {
                if (m_StoreDiscountDto.getStoreOfferDtos().size() > 1) {
                    thenYouGetLabel.setText(thenYouGetLabel.getText() + " all of the following:");
                }
                else
                {
                    thenYouGetLabel.setText(thenYouGetLabel.getText() + ":");
                }
                forAdditionalLabel.visibleProperty().setValue(true);
                totalPriceLabel.visibleProperty().setValue(true);

                double totalPrice = m_StoreDiscountDto.getStoreOfferDtos().stream().mapToDouble(StoreOfferDto::getForAdditional).sum();
                totalPriceLabel.textProperty().set(FormatUtils.DecimalFormat.format(totalPrice));
            }
            setCellFactoryOfListView();

        });
    }

    protected void setCellFactoryOfListView() {
        discountOfferListView.setCellFactory(param -> new ListCell<StoreOfferDto>() {
            @Override
            protected void updateItem(StoreOfferDto storeOfferDto, boolean empty) {
                super.updateItem(storeOfferDto, empty);

                if (empty || storeOfferDto == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (m_WithButtons && m_StoreDiscountDto.isOneOfDiscount()) {
                        RadioButton radioButton = new RadioButton(null);
                        radioButton.setToggleGroup(m_ToggleGroup);
                        radioButton.setUserData(storeOfferDto.getItemId());
                        radioButton.setSelected(true);
                        // Add Listeners if any
                        setGraphic(radioButton);
                    }

                    String storeOfferText = storeOfferDto.getQuantity() + " " + m_ItemsUIManger.getItemDtoById(storeOfferDto.getItemId()).getItemName() + " for additional of " +
                            storeOfferDto.getForAdditional() + " for unit";
                    setText(storeOfferText);
                }
            }
        });
    }

}
