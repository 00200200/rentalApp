package pl.rentalApp.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.rentalApp.manager.ClientManager;
import pl.rentalApp.manager.ReservationManager;
import pl.rentalApp.manager.SkisManager;
import pl.rentalApp.models.Client;
import pl.rentalApp.models.Reservation;
import pl.rentalApp.models.Ski;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ClientAppController {
    private int clientId;
    public void setClientId(int clientId){
        this.clientId = clientId;
    }
    @FXML
    public TableView<Ski> availableSkisTable;
    @FXML
    private TableColumn<Ski,String> availableTableNameClient;
    @FXML
    private TableColumn<Ski,String> availableTableStatusClient;
    private List<Ski> skis;
    @FXML
    private TableColumn<Ski,Integer> availableTableLengthClient;
    @FXML
    private Button clientReservation;

    @FXML
    public void initialize(){
        availableTableNameClient.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableTableLengthClient.setCellValueFactory(new PropertyValueFactory<>("length"));
        availableTableStatusClient.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadSkisData();
    }
    @FXML
    private void reloadData(){
        loadSkisData();
    }

    private void loadSkisData(){
        SkisManager skisManager = new SkisManager();
        ReservationManager reservationManager = new ReservationManager();
        skis = skisManager.readSkisFromFile();
        List<Reservation> reservations = reservationManager.readReservations();
        List<Ski> filteredSkis = new ArrayList<>();
        for(Ski ski : skis){
            for(Reservation reservation : reservations){
                if(ski.getId() == reservation.getId_narty() && reservation.getId_klienta() == clientId && ski.getStatus().equals("dostepne")){
                    ski.setStatus(reservation.getStatus());
                }
                if(ski.getId() == reservation.getId_narty() && reservation.getId_klienta() == clientId && ski.getStatus().equals("Zarezerwowane")){
                    ski.setStatus(reservation.getStatus());
                }
                if(ski.getId() == reservation.getId_narty() && reservation.getId_klienta() == clientId && ski.getStatus().equals("Zapłacone")){
                    ski.setStatus(reservation.getStatus());
                }
                if(ski.getId() == reservation.getId_narty() && reservation.getId_klienta() == clientId && ski.getStatus().equals("Wydane")){
                    ski.setStatus(reservation.getStatus());
                }
                if(ski.getStatus().equals("dostepne") && !filteredSkis.contains(ski)){
                    filteredSkis.add(ski);
                }
                if(ski.getId() == reservation.getId_narty() && clientId == reservation.getId_klienta() && !filteredSkis.contains(ski) && ski.getStatus().equals("Wydane") ){
                    filteredSkis.add(ski);
                }
                if(ski.getId() == reservation.getId_narty() && clientId == reservation.getId_klienta() && !filteredSkis.contains(ski) && ski.getStatus().equals("Zapłacone") ){
                    filteredSkis.add(ski);
                }
                if(ski.getId() == reservation.getId_narty() && clientId == reservation.getId_klienta() && !filteredSkis.contains(ski) && ski.getStatus().equals("Zarezerwowane") ){
                    filteredSkis.add(ski);
                }
            }
        }
        skisManager.saveSkisToFile(skis);
        availableSkisTable.getItems().setAll(filteredSkis);
    }
    @FXML
    private void handleReservation() throws IOException {
        ClientManager clientManager = new ClientManager();
        List<Client> clients = clientManager.readClients();
        if(!clients.get(clientId - 1).getRegistered()){
            System.out.println("NIE JESTES ZAREJESTROWANY");
            return;
        }
        Ski selectedSki = availableSkisTable.getSelectionModel().getSelectedItem();
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        if(selectedSki != null){
            Reservation newReservation = new Reservation(reservations.size()+1,selectedSki.getId(),clientId, LocalDate.now(),LocalDate.now().plusDays(7),"Zarezerwowane",false);
            Reservation checkIfContains = new Reservation(reservations.size(),selectedSki.getId(),clientId, LocalDate.now(),LocalDate.now().plusDays(7),"Zarezerwowane",false);
            if(!reservations.contains(checkIfContains)) {
                // id rezerwacji zmniejszyc o jeden i sprawdzamy
                reservationManager.saveReservation(newReservation);
            }
            loadSkisData();
        }
    }

    @FXML
    private void handlePay() throws IOException {
        Ski selectedSki = availableSkisTable.getSelectionModel().getSelectedItem();
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();

        if(selectedSki != null && selectedSki.getStatus().equals("Zarezerwowane")){
            for(Reservation reservation : reservations){
                if(!reservation.isPayMent() && reservation.getId_klienta() == clientId && reservation.getId_narty() == selectedSki.getId()){
                    reservation.setStatus("Zapłacone");
                    System.out.println();
                    reservation.setPayMent(true);
                    break;
                }
            }
        }
        reservationManager.saveReservationsToFile(reservations);
        loadSkisData();
    }

    @FXML
    private void handleTakeSki() throws IOException {
        Ski selectedSki = availableSkisTable.getSelectionModel().getSelectedItem();
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();

        if(selectedSki != null && selectedSki.getStatus().equals("Zapłacone")){
            for(Reservation reservation : reservations){
                if( reservation.getId_klienta() == clientId && reservation.getId_narty() == selectedSki.getId()){
                    reservation.setStatus("Czeka na odbiór");
                    break;
                }
            }
        }
        reservationManager.saveReservationsToFile(reservations);
        loadSkisData();
    }
    @FXML
    private void returnSkis() throws IOException {
        Ski selectedSki = availableSkisTable.getSelectionModel().getSelectedItem();
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        SkisManager skisManager = new SkisManager();
        List<Ski> skis = skisManager.readSkisFromFile();
        Ski selectedSkiInArray = skis.get(selectedSki.getId() -1);
        if(selectedSki != null && selectedSki.getStatus().equals("Wydane")){
            for(Reservation reservation : reservations){
                if(reservation.getId_klienta() == clientId && reservation.getId_narty() == selectedSki.getId()){
                    selectedSkiInArray.setStatus("Oddane");
                    reservation.setStatus("Oddane");
                    break;
                }
            }
        }
        skisManager.saveSkisToFile(skis);
        reservationManager.saveReservationsToFile(reservations);
        loadSkisData();
    }
}
