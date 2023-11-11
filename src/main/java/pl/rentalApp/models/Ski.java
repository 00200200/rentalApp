package pl.rentalApp.models;

public class Ski {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private String type;
    private int length;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Ski(int id, String type, int length, String status) {
        this.id = id;
        this.type = type;
        this.length = length;
        this.status = status;
    }

}
