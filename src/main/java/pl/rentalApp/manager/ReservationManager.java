package pl.rentalApp.manager;

import pl.rentalApp.models.Reservation;
import pl.rentalApp.observer.Observer;
import pl.rentalApp.observer.Subject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager implements Subject {
    private List<Observer> observers = new ArrayList<>();

    private String filePath = "src/main/java/pl/rentalApp/data/reservation.txt";

    private static ReservationManager instance;

    private ReservationManager() {
    }

    ;

    public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }

    public ReservationManager saveReservation(Reservation reservation) throws IOException {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(reservation.getId() + "," + reservation.getId_narty() + "," + reservation.getId_klienta() + "," + reservation.getStartDate() + "," + reservation.getEndDate() + "," + reservation.getStatus() + "," + reservation.isPayMent() +
                    "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifyObservers();
        return null;
    }

    public void saveReservationsToFile(List<Reservation> reservations) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (Reservation reservation : reservations) {
            String line = reservation.getId() + "," + reservation.getId_narty() + "," + reservation.getId_klienta() + "," + reservation.getStartDate() + "," + reservation.getEndDate() + "," + reservation.getStatus() + "," + reservation.isPayMent() +
                    "\n";
            writer.write(line);
        }
        writer.close();
        notifyObservers();
    }

    public List<Reservation> readReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while (((line = reader.readLine()) != null)) {
                String[] parts = line.split(",");
                int reservation_id = Integer.parseInt(parts[0]);
                int ski_id = Integer.parseInt(parts[1]);
                int client_id = Integer.parseInt(parts[2]);
                LocalDate startDate = LocalDate.parse(parts[3]);
                LocalDate endDate = LocalDate.parse(parts[4]);
                String status = parts[5];
                boolean payMent = Boolean.parseBoolean(parts[6]);
                reservations.add(new Reservation(reservation_id, ski_id, client_id, startDate, endDate, status, payMent));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
