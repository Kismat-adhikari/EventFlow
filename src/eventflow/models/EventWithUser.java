package eventflow.models;

public class EventWithUser {
    private int id;
    private String eventTitle;
    private String eventDesc;
    private int eventTickets;
    private double eventPrice;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String uploaderName;  // new field for uploader's fullname

    // getters and setters for all fields including uploaderName
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getEventDesc() { return eventDesc; }
    public void setEventDesc(String eventDesc) { this.eventDesc = eventDesc; }

    public int getEventTickets() { return eventTickets; }
    public void setEventTickets(int eventTickets) { this.eventTickets = eventTickets; }

    public double getEventPrice() { return eventPrice; }
    public void setEventPrice(double eventPrice) { this.eventPrice = eventPrice; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }

    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }

    public String getUploaderName() { return uploaderName; }
    public void setUploaderName(String uploaderName) { this.uploaderName = uploaderName; }
}
