package eventflow.models;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private int userId;
    private int eventId;
    private LocalDateTime purchaseDate;
    private double pricePaid;

    // Default constructor
    public Ticket() {
    }

    // Constructor for creating new ticket (without id)
    public Ticket(int userId, int eventId, double pricePaid) {
        this.userId = userId;
        this.eventId = eventId;
        this.pricePaid = pricePaid;
        this.purchaseDate = LocalDateTime.now();
    }

    // Constructor with all fields (for reading from database)
    public Ticket(int id, int userId, int eventId, LocalDateTime purchaseDate, double pricePaid) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.purchaseDate = purchaseDate;
        this.pricePaid = pricePaid;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }
}
