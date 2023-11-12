package pl.rentalApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.rentalApp.manager.ReservationManager;
import pl.rentalApp.manager.SkisManager;
import pl.rentalApp.models.Reservation;
import pl.rentalApp.models.Ski;
import pl.rentalApp.observer.Observer;

import java.io.IOException;
import java.util.List;

public class EmployeeAppController implements Observer {
    private SkisManager skisManager;
    @FXML
    private TableView<Reservation> employeeTable;

    @FXML
    private TableColumn<Reservation, Integer> employeeTableId;

    @FXML
    private TableColumn<Reservation, String> employeeTableSki;

    @FXML
    private TableColumn<Reservation, String> employeeTableStatus;
    @FXML
    TableColumn<Reservation, Boolean> employeeTablePayment;

    @FXML
    public void initialize() {
        employeeTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeTableSki.setCellValueFactory(new PropertyValueFactory<>("id_narty"));
        employeeTableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeTablePayment.setCellValueFactory(new PropertyValueFactory<>("payMent"));
        loadData();
    }

    public EmployeeAppController() {
        skisManager = SkisManager.getInstance();
        skisManager.addObserver(this);

    }

    @FXML
    private void refreshData() {
        loadData();
    }

    @FXML
    private void handleReturnSki() throws IOException {
        ReservationManager reservationManager = ReservationManager.getInstance();
        List<Reservation> reservations = reservationManager.readReservations();
        Reservation selectedReservation = employeeTable.getSelectionModel().getSelectedItem();
        Reservation selectedReservationInArray = reservations.stream().filter(reservation -> reservation.getId() == selectedReservation.getId()).findFirst().orElse(null);
        if (selectedReservation != null && selectedReservation.getStatus().equals("Oddane")) {
            SkisManager skisManager = SkisManager.getInstance();
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
//            loadData();
        }
    }

    @FXML
    private void handleGiveSki() throws IOException {
        ReservationManager reservationManager = ReservationManager.getInstance();
        List<Reservation> reservations = reservationManager.readReservations();
        Reservation selectedReservation = employeeTable.getSelectionModel().getSelectedItem();
        Reservation selectedReservationInArray = reservations.stream().filter(reservation -> reservation.getId() == selectedReservation.getId()).findFirst().orElse(null);
        System.out.println(selectedReservationInArray.getId());
        System.out.println(selectedReservation.getId());
        if (selectedReservation != null && selectedReservation.getStatus().equals("Czeka na odbiór")) {
            SkisManager skisManager = SkisManager.getInstance();
            List<Ski> skis = skisManager.readSkisFromFile();
            for (Ski ski : skis) {
                if (ski.getId() == selectedReservation.getId_narty()) {
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
//            loadData();
        }
    }


    public void loadData() {
        ReservationManager reservationManager = ReservationManager.getInstance();
        List<Reservation> reservations = reservationManager.readReservations();
        employeeTable.getItems().setAll(reservations);
    }


    @Override
    public void update() {
        loadData();
    }
}
