package eventflow.models;

public class Event {
    private int id;
    private String eventTitle;
    private String eventDesc;
    private int eventTickets;
    private double eventPrice;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private int userId;  // new field to store user who created the event

    public Event() {
        // default constructor
    }

    // Constructor with id and userId (for reading from DB)
    public Event(int id, String eventTitle, String eventDesc, int eventTickets, double eventPrice,
                 String eventDate, String eventTime, String eventLocation, int userId) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventTickets = eventTickets;
        this.eventPrice = eventPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.userId = userId;
    }

    // Constructor without id (for inserting new event)
    public Event(String eventTitle, String eventDesc, int eventTickets, double eventPrice,
                 String eventDate, String eventTime, String eventLocation, int userId) {
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventTickets = eventTickets;
        this.eventPrice = eventPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.userId = userId;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public int getEventTickets() {
        return eventTickets;
    }

    public void setEventTickets(int eventTickets) {
        this.eventTickets = eventTickets;
    }

    public double getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
