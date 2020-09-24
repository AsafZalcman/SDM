package ui.javafx.components.viewItems;

import java.net.URL;
import java.util.ResourceBundle;

import dtoModel.StorageItemDto;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.utils.FormatUtils;

public class ViewItemsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<StorageItemDto> itemsTableViewInViewItemsTab;

    @FXML
    private TableColumn<StorageItemDto, Integer> itemIdTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemNameTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemPurchaseFormTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, Long> itemHowManyStoresSellTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemAveragePriceTableColumnInViewItems;

    @FXML
    private TableColumn<StorageItemDto, String> itemTotalSoldSoFarTableColumnInViewItems;

    @FXML
    void initialize() {
        initViewItemsTabComponents();
    }

    private SuperDuperController superDuperController;
    private ItemsUIManger m_ItemsUIManger;

    public ViewItemsController(){
        m_ItemsUIManger = ItemsUIManger.getInstance();
    }

    public void setMainController(SuperDuperController superDuperController){
        this.superDuperController = superDuperController;
    }

    private void initViewItemsTabComponents() {
        itemIdTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getItemDto().getId()).asObject());
        itemNameTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemDto().getItemName()));
        itemPurchaseFormTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getItemDto().getPurchaseForm().getValue()));
        itemHowManyStoresSellTableColumnInViewItems.setCellValueFactory(new PropertyValueFactory<>("storesSellIt"));
        itemAveragePriceTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getAvgPrice())));
        itemTotalSoldSoFarTableColumnInViewItems.setCellValueFactory(cellData-> new SimpleStringProperty(FormatUtils.DecimalFormat.format(cellData.getValue().getSales())));
    }

    public void fetchStorageItems(){
        itemsTableViewInViewItemsTab.getItems().setAll(m_ItemsUIManger.getAllStorageItems());
    }
}
