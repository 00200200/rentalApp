package pl.rentalApp.models;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int id_narty;
    private int id_klienta;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private boolean payMent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_narty() {
        return id_narty;
    }

    public void setId_narty(int id_narty) {
        this.id_narty = id_narty;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPayMent() {
        return payMent;
    }

    public void setPayMent(boolean payMent) {
        this.payMent = payMent;
    }

    public Reservation(int id, int id_narty, int id_klienta, LocalDate startDate, LocalDate endDate, String status, boolean payMent) {
        this.id = id;
        this.id_narty = id_narty;
        this.id_klienta = id_klienta;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.payMent = payMent;
    }
}
