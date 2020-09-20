package ui.javafx.components.order.createOrder.steps.buyItems;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import ui.javafx.components.order.createOrder.steps.StepController;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.OrdersUIManager;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.FormatUtils;

import java.util.Collection;

public class BuyItemsController extends StepController {

    @FXML
    private TableView<ItemDto> itemsToBuyTableView;

    @FXML
    private TableColumn<ItemDto, Integer> itemIdColumnInItemsToBuyTable;

    @FXML
    private TableColumn<ItemDto, String> itemNameColumnInItemsToBuyTable;

    @FXML
    private TableColumn<ItemDto, String> itemPurchaseFormColumnInItemsToBuyTable;

    @FXML
    private TableColumn<ItemDto, Double> itemPriceColumnInItemsToBuyTable;

    @FXML
    private TableColumn<ItemDto, String> itemAmountColumnInItemsToBuyTable;

    @FXML
    private Label itemAddedLabel;

    @Override
    public String getStepName() {
        return "Buy Items";
    }

    @Override
    public void onStepFinish() {
    }

    private ItemsUIManger m_ItemsUIManger;
    private StoreUIManager m_StoreUIManager;
    private OrdersUIManager m_OrdersUIManager;
    private SimpleBooleanProperty isMandatoryFieldsAreCompleted;


    public BuyItemsController() {
        m_ItemsUIManger = new ItemsUIManger();
        m_StoreUIManager = new StoreUIManager();
        m_OrdersUIManager = OrdersUIManager.getInstance();
        isMandatoryFieldsAreCompleted = new SimpleBooleanProperty(false);
    }

    private void showItems() {
        Collection<ItemDto> allItems = m_ItemsUIManger.getAllItems();
        StoreDto currentStore = m_OrdersUIManager.getStore();
        if (currentStore == null) {
            /**
             if we want to hide price column in dynamic order, we need to uncomment the following lines:**/
           // itemPriceColumnInItemsToBuyTable.setVisible(false);
            itemsToBuyTableView.getItems().addAll(allItems);
        } else {
            itemsToBuyTableView.getItems().addAll(m_StoreUIManager.getAllItemsOfStore(currentStore.getId()));
            allItems.forEach(itemDto -> {
                if (!itemsToBuyTableView.getItems().contains(itemDto)) {
                    itemsToBuyTableView.getItems().add(itemDto);
                }
            });
        }
    }

    private void initItemsToBuyTableView() {
        itemIdColumnInItemsToBuyTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemNameColumnInItemsToBuyTable.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPurchaseFormColumnInItemsToBuyTable.setCellValueFactory(new PropertyValueFactory<>("purchaseForm"));
        itemPriceColumnInItemsToBuyTable.setCellValueFactory(cellData ->
        {
            if (cellData.getValue().getPrice() != null) {
                return new SimpleDoubleProperty(Double.parseDouble(FormatUtils.DecimalFormat.format(cellData.getValue().getPrice()))).asObject();
            }
            return null;
        });

        itemAmountColumnInItemsToBuyTable.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void initialize() {
        initItemsToBuyTableView();
        showItems();
        itemsToBuyTableView.setEditable(true);

  itemsToBuyTableView.setRowFactory(tv -> new TableRow<ItemDto>() {
      @Override
      protected void updateItem(ItemDto itemDto, boolean empty) {
          super.updateItem(itemDto, empty);
          if (!empty && itemDto != null) {
              disableProperty().set(m_OrdersUIManager.getStore()!=null && itemDto.getPrice() == null);
          }
      }
  });
    }

    public void itemAmountColumnInItemsToBuyTableOnEditCommit(TableColumn.CellEditEvent<ItemDto, String> itemDtoDoubleCellEditEvent) {
        //צריך לראות האם יש דרך קלה לא לאפשר למשתמש להכניס משהו אחר חוץ ממספרים
        ItemDto itemDto = itemsToBuyTableView.getSelectionModel().getSelectedItem();
        try {
            double amountToAdd = Double.parseDouble(itemDtoDoubleCellEditEvent.getNewValue());
            m_OrdersUIManager.addItemToOrder(itemDto.getId(), amountToAdd);
            itemAddedLabel.setText("The item with the id \"" + itemDto.getId() + "\" was added " + amountToAdd + "times to the order");
            itemAddedLabel.setTextFill(Color.GREEN);
            m_StepComplete.set(true);
        } catch (NumberFormatException e) {
            itemAddedLabel.setText("Error:\"" + itemDtoDoubleCellEditEvent.getNewValue() + "\" is not a number");
            itemAddedLabel.setTextFill(Color.RED);
        } catch (IllegalArgumentException e) {
            itemAddedLabel.setText("Error:" + e.getMessage());
            itemAddedLabel.setTextFill(Color.RED);
        }
    }
}

