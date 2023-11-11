package pl.rentalApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.rentalApp.manager.ClientManager;
import pl.rentalApp.manager.ReservationManager;
import pl.rentalApp.manager.SkisManager;
import pl.rentalApp.models.Client;
import pl.rentalApp.models.Reservation;
import pl.rentalApp.models.Ski;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerAppController {

    @FXML
    private Button ManagerAddSkiButton;

    @FXML
    private TextField ManagerAddSkiLengthInput;
    @FXML
    private TextField ManagerAddSkiTypeInput;

    @FXML
    private TableColumn<Client, String> ManagerClientName;
    @FXML
    private TableColumn<Client, String> ManagerClientId;
    @FXML
    private TableView<Client> ManagerClientsTable;
    @FXML
    private TableColumn<Client,Boolean> ManagerClientRegistered;
    @FXML
    private TableColumn<Reservation, Integer> ManagerReservationClientID;
    @FXML
    private TableColumn<Reservation, Integer> ManagerReservationID;
    @FXML
    private TableColumn<Reservation,Boolean> ManagerReservationPayment;
    @FXML
    private TableColumn<Reservation,String> ManagerReservationStatus;
    @FXML
    private TableView<Reservation> ManagerReservationTable;
    @FXML
    private TableColumn<Ski,Integer> ManagerSkisIDTable;
    @FXML
    private TableColumn<Ski, Integer> ManagerSkisLengthTable;
    @FXML
    private TableColumn<Ski, String> ManagerSkisStatusTable;
    @FXML
    private TableView<Ski> ManagerSkisTable;
    @FXML
    private TableColumn<Ski,String> ManagerSkisTypeTable;

    public ManagerAppController() {

    }
@FXML
    public void initialize(){
        ManagerSkisTypeTable.setCellValueFactory(new PropertyValueFactory<>("type"));
        ManagerSkisStatusTable.setCellValueFactory(new PropertyValueFactory<>("status"));
        ManagerSkisLengthTable.setCellValueFactory(new PropertyValueFactory<>("length"));
        ManagerSkisIDTable.setCellValueFactory(new PropertyValueFactory<>("id"));

        ManagerClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ManagerClientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ManagerClientRegistered.setCellValueFactory(new PropertyValueFactory<>("registered"));

        ManagerReservationClientID.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
        ManagerReservationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ManagerReservationPayment.setCellValueFactory(new PropertyValueFactory<>("payMent"));
        ManagerReservationStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadData();
    }

    @FXML
    public void addSki(){
        SkisManager skisManager = new SkisManager();
        List<Ski> skis = skisManager.readSkisFromFile();
        if(ManagerAddSkiLengthInput.getText() != null && ManagerAddSkiTypeInput != null){
            skis.add(new Ski(skis.size() +1,ManagerAddSkiTypeInput.getText(),Integer.parseInt(ManagerAddSkiLengthInput.getText()),"dostepne"));
            skisManager.saveSkisToFile(skis);
        }
    }

    @FXML
    public void registerClient() throws IOException {
        Client selectedClient = ManagerClientsTable.getSelectionModel().getSelectedItem();
        ClientManager clientManager = new ClientManager();
        List<Client> clientList = clientManager.readClients();
        Client selectedClientInArray = clientList.get(selectedClient.getId() -1);
        if((selectedClient != null && !selectedClient.getRegistered())){
            selectedClientInArray.setRegistered(true);
        }
        ClientManager.updateClients(clientList);
        loadData();
    }
    public void loadData(){
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        SkisManager skisManager = new SkisManager();
        List<Ski> skis = skisManager.readSkisFromFile();
        ClientManager clientManager = new ClientManager();
        List<Client> clients = clientManager.readClients();
        ManagerClientsTable.getItems().setAll(clients);
        ManagerSkisTable.getItems().setAll(skis);
        ManagerReservationTable.getItems().setAll(reservations);


    }
}
