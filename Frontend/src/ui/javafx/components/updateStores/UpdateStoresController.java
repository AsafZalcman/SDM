package ui.javafx.components.updateStores;

import dtoModel.ItemDto;
import dtoModel.StoreDto;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.FormatUtils;

import java.util.stream.Collectors;

public class UpdateStoresController {

    @FXML
    private AnchorPane storeItemsAnchorPane;

    @FXML
    private AnchorPane storageItemsAnchorPane;

    @FXML
    private ComboBox<StoreDto> chooseStoreComboBox;

    @FXML
    private ListView<ItemDto> storeItemsListView;

    @FXML
    private ListView<ItemDto> availableItemsToAddListView;

    private SuperDuperController superDuperController;
    private StoreUIManager m_StoreUIManager;
    private ItemsUIManger m_ItemUIManager;

    public UpdateStoresController() {
        this.m_StoreUIManager = StoreUIManager.getInstance();
        this.m_ItemUIManager = ItemsUIManger.getInstance();
    }

    @FXML
    void initialize() {

        availableItemsToAddListView.setCellFactory(param -> new ListCell<ItemDto>() {
            @Override
            protected void updateItem(ItemDto itemDto, boolean empty) {
                super.updateItem(itemDto, empty);

                if (empty || itemDto == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    StringBuilder storageItem = new StringBuilder(itemDto.getItemName() + "(ID: " + itemDto.getId() + ") ");

                    HBox root = new HBox(10);
                    root.setAlignment(Pos.CENTER_LEFT);
                    root.setPadding(new Insets(5, 10, 5, 10));

                    TextField priceField = new TextField();
                    priceField.setPromptText("Set new price");
                    priceField.setPrefWidth(109);
                    priceField.setTextFormatter(FormatUtils.getDoubleFormatter());


                    root.getChildren().add(new Label(storageItem.toString()));
                    root.getChildren().add(priceField);


                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS);
                    root.getChildren().add(region);

                    Button addBtn = new Button("Add Item");
                    addBtn.disableProperty().bind(priceField.textProperty().isEmpty());
                    addBtn.setOnAction(event -> {
                        try {
                            m_StoreUIManager.addNewItemToStore(chooseStoreComboBox.getSelectionModel().selectedItemProperty().getValue().getId(), itemDto.getId(), Double.parseDouble(priceField.getText()));
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item ID: " + itemDto.getId() + " with the price: " + priceField.getText() + " was Successfully added to the requested store");
                            alert.show();
                            itemWasAddedOrDeleted();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                            alert.show();
                        }
                    });

                    root.getChildren().addAll(addBtn);

                    setText(null);
                    setGraphic(root);
                }
            }
        });

        storeItemsListView.setCellFactory(param -> new ListCell<ItemDto>() {
            @Override
            protected void updateItem(ItemDto itemDto, boolean empty) {
                super.updateItem(itemDto, empty);

                if (empty || itemDto == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    StringBuilder storeItem = new StringBuilder(itemDto.getItemName() + "(ID: " + itemDto.getId() + "), Price: ");

                    HBox root = new HBox(10);
                    root.setAlignment(Pos.CENTER_LEFT);
                    root.setPadding(new Insets(5, 10, 5, 10));

                    TextField priceField = new TextField();
                    priceField.setPromptText(FormatUtils.DecimalFormat.format(itemDto.getPrice()));
                    priceField.setPrefWidth(50);
                    priceField.setTextFormatter(FormatUtils.getDoubleFormatter());

                    root.getChildren().add(new Label(storeItem.toString()));
                    root.getChildren().add(priceField);


                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS);
                    root.getChildren().add(region);

                    Button deleteBtn = new Button("Delete");
                    deleteBtn.setOnAction(event -> {
                        try {
                            deleteItemFromStore(chooseStoreComboBox.getSelectionModel().selectedItemProperty().getValue().getId(), itemDto.getId());
                            Alert alert = new Alert((Alert.AlertType.INFORMATION), "Item ID: " + itemDto.getId() + " deleted Successfully and also all discounts associated with it!");
                            alert.show();
                            itemWasAddedOrDeleted();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                            alert.show();
                        }
                    });


                    Button updatePriceBtn = new Button("Change Price");
                    updatePriceBtn.disableProperty().bind(priceField.textProperty().isEmpty());
                    updatePriceBtn.setOnAction(event -> {
                        try {
                            m_StoreUIManager.updateStoreItemPrice(chooseStoreComboBox.getSelectionModel().selectedItemProperty().getValue().getId(), itemDto.getId(), Double.parseDouble(priceField.getText()));
                            Alert alert = new Alert((Alert.AlertType.INFORMATION), "The price for item ID: " + itemDto.getId() + " has been successfully updates to " + priceField.getText());
                            alert.show();
                            cleanStoreItemInListView(chooseStoreComboBox.getValue());
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                            alert.show();
                        }
                    });

                    root.getChildren().addAll(deleteBtn, updatePriceBtn);

                    setText(null);
                    setGraphic(root);
                }
            }
        });


        chooseStoreComboBox.setConverter(new StringConverter<StoreDto>() {
            @Override
            public String toString(StoreDto storeDto) {
                return storeDto.getName() + "(ID: " + storeDto.getId() + ")";
            }

            @Override
            public StoreDto fromString(String storeDtoString) {
                return null;
            }
        });

        chooseStoreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, PrevStoreDto, currentStoreDto) -> {
            if (currentStoreDto != null) {
                storeItemsListView.getItems().setAll(currentStoreDto.getItemsDto());
                storeItemsAnchorPane.setVisible(true);
                storageItemsAnchorPane.setVisible(true);
            }
        });

        chooseStoreComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!chooseStoreComboBox.getItems().isEmpty()) {
                updateAvailableItemsToAddListView();
            }
        });
    }

    private void deleteItemFromStore(int i_StoreID, int i_StoreItemID) throws Exception {
        m_StoreUIManager.deleteItemFromStore(i_StoreID, i_StoreItemID);
    }

    private void cleanStoreItemInListView(StoreDto i_StoreDto) {
        storeItemsListView.getItems().setAll(i_StoreDto.getItemsDto());
    }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }

    public void fetchData() {
        storeItemsAnchorPane.setVisible(false);
        storageItemsAnchorPane.setVisible(false);
        chooseStoreComboBox.getItems().clear();
        chooseStoreComboBox.getItems().addAll(m_StoreUIManager.getAllStores());
    }

    private void updateAvailableItemsToAddListView() {
        StoreDto currentStoreDto = chooseStoreComboBox.getValue();
            availableItemsToAddListView.getItems().setAll(m_ItemUIManager.getAllItems().stream()
                .filter(itemDto -> !currentStoreDto.getItemsDto().contains(itemDto))
                .collect(Collectors.toList()));
    }

    private void itemWasAddedOrDeleted()
    {
        updateAvailableItemsToAddListView();
        cleanStoreItemInListView(chooseStoreComboBox.getValue());
    }
}
