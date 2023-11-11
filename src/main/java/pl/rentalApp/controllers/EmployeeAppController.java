package pl.rentalApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.rentalApp.manager.ReservationManager;
import pl.rentalApp.manager.SkisManager;
import pl.rentalApp.models.Reservation;
import pl.rentalApp.models.Ski;

import java.io.IOException;
import java.util.List;

public class EmployeeAppController {

    @FXML
    private Button employeButonTake;
    @FXML
    private Button employeeButtonGive;
    @FXML
    private Button employeeRefreshButton;

    @FXML
    private TableView<Reservation> employeeTable;

    @FXML
    private TableColumn<Reservation, Integer> employeeTableId;

    @FXML
    private TableColumn<Reservation, String > employeeTableSki;

    @FXML
    private TableColumn<Reservation, String> employeeTableStatus;
    @FXML TableColumn<Reservation,Boolean> employeeTablePayment;
    @FXML
    public void initialize(){
        employeeTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeTableSki.setCellValueFactory(new PropertyValueFactory<>("id_narty"));
        employeeTableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeTablePayment.setCellValueFactory(new PropertyValueFactory<>("payMent"));
        loadData();
    }

    @FXML
    private void refreshData(){
        loadData();
    }

    @FXML
    private void handleReturnSki () throws IOException {
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        Reservation selectedReservation = employeeTable.getSelectionModel().getSelectedItem();
        Reservation selectedReservationInArray = reservations.get(selectedReservation.getId() -1);
        System.out.println(selectedReservationInArray.getId());
        System.out.println(selectedReservation.getId());
        if(selectedReservation != null && selectedReservation.getStatus().equals("Oddane")) {
            SkisManager skisManager = new SkisManager();
            List<Ski> skis = skisManager.readSkisFromFile();
            for (Ski ski : skis) {
                if (ski.getId() == selectedReservation.getId_narty()) {
                    if (selectedReservation.getStatus().equals("Oddane")) {
                        ski.setStatus("dostepne");
                        reservations.remove(selectedReservationInArray);
                        break;
                    }
                }
            }
            reservationManager.saveReservationsToFile(reservations);
            skisManager.saveSkisToFile(skis);
            loadData();
        }
    }

    @FXML
    private void handleGiveSki() throws IOException {
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        Reservation selectedReservation = employeeTable.getSelectionModel().getSelectedItem();
        Reservation selectedReservationInArray = reservations.get(selectedReservation.getId() -1);
        System.out.println(selectedReservationInArray.getId());
        System.out.println(selectedReservation.getId());
        if(selectedReservation != null && selectedReservation.getStatus().equals("Czeka na odbiór")) {
            SkisManager skisManager = new SkisManager();
            List<Ski> skis = skisManager.readSkisFromFile();
            for (Ski ski : skis) {
                if (ski.getId() == selectedReservation.getId_narty()) {
                    System.out.println("NO JESTESMY TU");
                    if (selectedReservation.getStatus().equals("Czeka na odbiór")) {
                        System.out.println(selectedReservation.getStatus());
                        ski.setStatus("Wydane");
                        selectedReservationInArray.setStatus("Wydane");
                        break;
                    }
                }
            }
            reservationManager.saveReservationsToFile(reservations);
            skisManager.saveSkisToFile(skis);
            loadData();
        }
    }


    public void loadData(){
        ReservationManager reservationManager = new ReservationManager();
        List<Reservation> reservations = reservationManager.readReservations();
        employeeTable.getItems().setAll(reservations);
    }


}