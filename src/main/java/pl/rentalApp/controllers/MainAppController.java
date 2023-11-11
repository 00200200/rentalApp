package pl.rentalApp.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.rentalApp.manager.ClientManager;
import pl.rentalApp.models.Client;
import pl.rentalApp.ui.EmployeeApp;

import java.io.IOException;
public class MainAppController {
    @FXML
    private TextField clientName;
    private Stage primaryStage;
    @FXML
    private Button clientButton;
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    @FXML
    void loginAsClient(ActionEvent event) {
        String name = clientName.getText();
        if (name != null) {
            openClientApp(name);
        }
    }
    @FXML
    void openEmployeeApp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Employee.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));;
            stage.show();
            stage.toFront();
            stage.setTitle("Panel Pracownika");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openManagerApp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManagerApp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));;
            stage.show();
            stage.setTitle("Panel Managera");
            stage.toFront();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openClientApp(String name){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientApp.fxml"));
            Parent root = loader.load();
            ClientAppController clientAppController = loader.getController();
            ClientManager clientManager = new ClientManager();
            Client newClient  = clientManager.createClient(name);
            clientAppController.setClientId(newClient.getId());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Aplikacja klienta " + name);
            stage.show();
            stage.toFront();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
