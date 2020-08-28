package ui.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class tempController {

    @FXML
    private Button storesSideBarButton;

    @FXML
    private TabPane mainTabPain;
    @FXML
    private Tab storesTransparentTab;

    @FXML
    void storesSideBarButtonOnClick(ActionEvent event) {
        mainTabPain.getSelectionModel().select(storesTransparentTab);
    }

}
