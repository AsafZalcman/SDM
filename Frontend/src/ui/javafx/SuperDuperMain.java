package ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.javafx.components.main.SuperDuperController;
import ui.javafx.utils.SDMResourcesConstants;

import java.net.URL;

public class SuperDuperMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

   //     Parent root = FXMLLoader.load(getClass().getResource("SDM.fxml"));
        URL mainFXML = getClass().getResource(SDMResourcesConstants.MAIN_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(mainFXML);
        Parent root = loader.load();
        SuperDuperController tempController = loader.getController();

        primaryStage.setTitle("Super Duper Market");

        Scene scene = new Scene(root, 900, 430 );
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(100);
        tempController.setPrimaryStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
