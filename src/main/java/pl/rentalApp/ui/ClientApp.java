package pl.rentalApp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        VBox mainPane = FXMLLoader.load(getClass().getResource("/fxml/ClientApp.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setTitle("Panel użytkownika");
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
}
