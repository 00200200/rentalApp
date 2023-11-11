package pl.rentalApp.models;
public class Client {
    private int id;
    private String name;

    private Boolean registered;
    private Reservation[] reservations;

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Reservation[] getReservations() {
        return reservations;
    }

    public void setReservations(Reservation[] reservations) {
        this.reservations = reservations;
    }

    public Client(int id, String name, Boolean registered) {
        this.name = name;
        this.id = id;
        this.registered = registered;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
