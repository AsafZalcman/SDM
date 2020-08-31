package ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class SuperDuperMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

   //     Parent root = FXMLLoader.load(getClass().getResource("SDM.fxml"));
        URL mainFXML = getClass().getResource("SDM.fxml");
        loader.setLocation(mainFXML);
        Parent root = loader.load();
        SuperDuperController tempController = loader.getController();
        tempController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
