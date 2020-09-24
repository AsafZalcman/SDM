package ui.javafx.components.createStore;

import dtoModel.ItemDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.managers.ItemsUIManger;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.FormatUtils;
import viewModel.LocationViewModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateStoreController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ComboBox<Point> locationComboBox;

    @FXML
    private TextField ppkTextField;

    @FXML
    private Button createStoreButton;

    @FXML
    private ListView<ItemDto> addItemsListView;

    @FXML
    private Label addItemMessageLabel;

    @FXML
    void createStoreButtonOnAction(ActionEvent event) {

        try {
            StoreUIManager.getInstance().createNewStore(Integer.parseInt(idTextField.getText()),
                    nameTextField.getText(),
                    locationComboBox.getValue(),
                    Double.parseDouble(ppkTextField.getText()),
                    m_ItemsToAdd);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The store:\"" + nameTextField.getText() + "\" was created");
            alert.show();
            fetchData();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
            alert.show();
        }
    }

    private List<ItemDto> m_ItemsToAdd;
    private SimpleBooleanProperty m_IsItemListEmpty;
    private SuperDuperController superDuperController;

    @FXML
    private void initialize() {

        idTextField.setTextFormatter(FormatUtils.getIntegerFormatter());
        ppkTextField.setTextFormatter(FormatUtils.getDoubleFormatter());
        nameTextField.setTextFormatter(FormatUtils.getLetterFormatter());
        locationComboBox.setConverter(new StringConverter<Point>() {
            @Override
            public String toString(Point point) {
                return "(" + point.x + "," + point.y + ")";
            }

            @Override
            public Point fromString(String pointString) {
                return null;
            }
        });
        addItemsListView.setCellFactory(param -> new ListCell<ItemDto>() {
            @Override
            protected void updateItem(ItemDto itemDto, boolean empty) {
                super.updateItem(itemDto, empty);

                if (empty || itemDto == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    HBox root = new HBox(10);
                    root.setAlignment(Pos.CENTER_LEFT);
                    root.setPadding(new Insets(5, 10, 5, 10));

                    TextField priceField = new TextField();
                    priceField.setPromptText("Set price");
                    priceField.setPrefWidth(109);
                    priceField.setTextFormatter(FormatUtils.getDoubleFormatter());


                    root.getChildren().add(new Label(itemDto.getItemName() + "(ID: " + itemDto.getId() + ") "));
                    root.getChildren().add(priceField);


                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS);
                    root.getChildren().add(region);

                    Button addBtn = new Button("Add Item");
                    addBtn.disableProperty().bind(priceField.textProperty().isEmpty());
                    addBtn.setOnAction(event -> {
                        m_ItemsToAdd.add(new ItemDto(itemDto.getId(),
                                itemDto.getItemName(),
                                itemDto.getPurchaseForm(),
                                Double.parseDouble(priceField.getText()),
                                0.0,
                                false));
                        param.getItems().removeAll(itemDto);
                        m_IsItemListEmpty.set(false);
                        addItemMessageLabel.setText("Item ID: \"" + itemDto.getId() + "\" with the price: \"" + priceField.getText() + "\" was Successfully added to the requested store");
                    });
                    root.getChildren().addAll(addBtn);

                    setText(null);
                    setGraphic(root);
                }
            }
        });

    }

    public void fetchData() {
        m_IsItemListEmpty = new SimpleBooleanProperty(true);
        m_ItemsToAdd = new ArrayList<>();
        LocationViewModel m_LocationViewModel = new LocationViewModel();
        addItemMessageLabel.setText(null);
        ppkTextField.clear();
        idTextField.clear();
        nameTextField.clear();
        locationComboBox.getItems().clear();
        locationComboBox.getItems().addAll(m_LocationViewModel.getAllAvailableLocations());
        locationComboBox.getSelectionModel().selectFirst();
        addItemsListView.getItems().clear();
        addItemsListView.getItems().addAll(ItemsUIManger.getInstance().getAllItems());
        createStoreButton.disableProperty().bind(
                Bindings.or(m_IsItemListEmpty,
                        Bindings.or(nameTextField.textProperty().isEmpty(),
                                Bindings.or(ppkTextField.textProperty().isEmpty(), idTextField.textProperty().isEmpty()))));
    }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }
}