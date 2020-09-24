package ui.javafx.components.map;
import interfaces.ILocationable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import ui.javafx.components.main.SuperDuperController;

import ui.javafx.managers.CustomersUIManager;
import ui.javafx.managers.StoreUIManager;
import ui.javafx.utils.ImageUtils;
import viewModel.LocationViewModel;
import viewModel.StoreViewModel;

import java.util.Objects;


public class ViewMapController {

    @FXML
    private ScrollPane mapScrollPane;


    @FXML
    void initialize() {

    }

    private SuperDuperController superDuperController;
    private LocationViewModel m_LocationViewModel;
    private CustomersUIManager m_CustomersUIManager;
    private StoreUIManager m_StoreUIManager;


    public ViewMapController(){
        m_LocationViewModel = new LocationViewModel();
        m_CustomersUIManager = new CustomersUIManager();
        m_StoreUIManager = StoreUIManager.getInstance();
    }

    public void setMainController(SuperDuperController superDuperController) {
        this.superDuperController = superDuperController;
    }

    public void draw(){
        GridPane theMap = new GridPane();
        theMap.setAlignment(Pos.CENTER);

        for (int x = 0; x < m_LocationViewModel.getMaxX(); x++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.SOMETIMES);
            columnConstraints.setMinWidth(10);
            columnConstraints.setPrefWidth(100);
            theMap.getColumnConstraints().add(columnConstraints);
        }
        for (int y = 0; y < m_LocationViewModel.getMaxY(); y++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            rowConstraints.setMinHeight(10);
            rowConstraints.setPrefHeight(30);
            theMap.getRowConstraints().add(rowConstraints);
        }
        ILocationable entity;
        for (int x = 0; x < m_LocationViewModel.getMaxX(); x++) {
            for (int y = 0; y < m_LocationViewModel.getMaxY(); y++) {
                if((entity = m_LocationViewModel.getILocationable(x,y)) != null){
                    if(Objects.equals("Customer",entity.getClass().getSimpleName())){
                        int customerID = entity.getId();
                        ImageView customer = ImageUtils.getCustomerIcon();
                        customer.setPickOnBounds(true);

                        Tooltip.install(customer, m_CustomersUIManager.getCustomerMapToolTip(customerID));

                        theMap.add(customer,x,y);
                    }
                    else if(Objects.equals("Store", entity.getClass().getSimpleName())){
                        int storeID = entity.getId();
                        ImageView store = ImageUtils.getStoreIcon();
                        store.setPickOnBounds(true);
                        Tooltip.install(store,m_StoreUIManager.getStoreMapToolTip(storeID));
                        theMap.add(store,x,y);
                    }
                }

            }
        }

        theMap.getStyleClass().add("mapGridPane");
        theMap.getStylesheets().add("map.css");
        theMap.setGridLinesVisible(true);
        theMap.setPadding(new Insets(5,5,5,5));
        theMap.minHeightProperty().bind(mapScrollPane.heightProperty());
        theMap.minWidthProperty().bind(mapScrollPane.widthProperty());

        mapScrollPane.setContent(theMap);
    }
}
