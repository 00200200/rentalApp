package pl.rentalApp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// JAK STARCZY CZASU TO  ZAMIAST W CONTROLLERZE TUTAJ TO ROBIC
public class EmployeeApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        VBox mainPane = FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setTitle("Panel pracownika");
        stage.setScene(scene);
        stage.show();
    }
}
