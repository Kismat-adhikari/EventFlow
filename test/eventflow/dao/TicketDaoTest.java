package eventflow.dao;

import eventflow.models.Ticket;
import eventflow.models.Event;
import eventflow.dao.TicketDao.TicketWithEventDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Comprehensive test suite for TicketDao class.
 * Tests all CRUD operations and business logic methods.
 */
public class TicketDaoTest {

    private TicketDao ticketDao;
    private UserDAO userDao;
    private EventDao eventDao;
    private int testUserId = -1;
    private int testEventId = -1;
    private int testTicketId = -1;

    @Before
    public void setUp() {
        ticketDao = new TicketDao();
        userDao = new UserDAO();
        eventDao = new EventDao();
        
        // Create a test user
        eventflow.models.User testUser = new eventflow.models.User(0, "Test Ticket User", 
            "ticketuser@example.com", "password123", 100.0, 0);
        boolean userCreated = userDao.createUser(testUser);
        assertTrue("Test user should be created successfully", userCreated);
        
        eventflow.models.User createdUser = userDao.getUserByEmailAndPassword(
            "ticketuser@example.com", "password123");
        testUserId = createdUser.getId();
        
        // Create a test event
        Event testEvent = new Event("Test Event for Tickets", "Test event description", 
            10, 25.0, "2025-12-31", "18:00", "Test Venue", testUserId);
        boolean eventCreated = eventDao.createEvent(testEvent);
        assertTrue("Test event should be created successfully", eventCreated);
        
        // Find the created event ID
        List<EventDao.EventWithUser> allEvents = eventDao.getAllEventsWithUploader();
        for (EventDao.EventWithUser event : allEvents) {
            if (event.getEventTitle().equals("Test Event for Tickets") && 
                event.getCreatorUserId() == testUserId) {
                testEventId = event.getId();
                break;
            }
        }
        assertTrue("Test event should have been created and found", testEventId != -1);
    }

    @After
    public void tearDown() {
        // Clean up test data
        if (testTicketId != -1) {
            // Tickets are typically deleted when event is deleted
        }
        if (testEventId != -1) {
            eventDao.deleteEvent(testEventId);
        }
        if (testUserId != -1) {
            userDao.deleteUser(testUserId);
        }
    }

    @Test
    public void testCreateTicket() {
        // Test creating a new ticket
        Ticket newTicket = new Ticket(testUserId, testEventId, 25.0);
        boolean result = ticketDao.createTicket(newTicket);
        
        assertTrue("Ticket creation should be successful", result);
        
        // Verify the ticket was created by retrieving tickets for the user
        List<Ticket> userTickets = ticketDao.getTicketsByUserId(testUserId);
        assertNotNull("User tickets list should not be null", userTickets);
        assertTrue("User should have at least one ticket", userTickets.size() >= 1);
        
        // Find the created ticket
        Ticket createdTicket = null;
        for (Ticket ticket : userTickets) {
            if (ticket.getEventId() == testEventId) {
                createdTicket = ticket;
                testTicketId = ticket.getId();
                break;
            }
        }
        
        assertNotNull("Created ticket should be found", createdTicket);
        assertEquals("Ticket user ID should match", testUserId, createdTicket.getUserId());
        assertEquals("Ticket event ID should match", testEventId, createdTicket.getEventId());
        assertEquals("Ticket price should match", 25.0, createdTicket.getPricePaid(), 0.01);
        assertNotNull("Purchase date should be set", createdTicket.getPurchaseDate());
    }

    @Test
    public void testGetTicketsByUserId() {
        // First create a ticket
        Ticket ticket = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket);
        
        // Test getting tickets by valid user ID
        List<Ticket> tickets = ticketDao.getTicketsByUserId(testUserId);
        assertNotNull("Tickets list should not be null", tickets);
        assertTrue("User should have at least one ticket", tickets.size() >= 1);
        
        // Verify ticket details
        boolean found = false;
        for (Ticket t : tickets) {
            if (t.getEventId() == testEventId) {
                found = true;
                testTicketId = t.getId();
                assertEquals("User ID should match", testUserId, t.getUserId());
                assertEquals("Event ID should match", testEventId, t.getEventId());
                assertEquals("Price paid should match", 25.0, t.getPricePaid(), 0.01);
                break;
            }
        }
        assertTrue("Created ticket should be found", found);
        
        // Test getting tickets by non-existent user
        List<Ticket> noTickets = ticketDao.getTicketsByUserId(99999);
        assertNotNull("Tickets list should not be null even for invalid user", noTickets);
        assertEquals("Tickets list should be empty for non-existent user", 0, noTickets.size());
    }

    @Test
    public void testGetTicketsByEventId() {
        // First create a ticket
        Ticket ticket = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket);
        
        // Test getting tickets by valid event ID
        List<Ticket> tickets = ticketDao.getTicketsByEventId(testEventId);
        assertNotNull("Tickets list should not be null", tickets);
        assertTrue("Event should have at least one ticket", tickets.size() >= 1);
        
        // Verify ticket details
        boolean found = false;
        for (Ticket t : tickets) {
            if (t.getUserId() == testUserId) {
                found = true;
                testTicketId = t.getId();
                assertEquals("User ID should match", testUserId, t.getUserId());
                assertEquals("Event ID should match", testEventId, t.getEventId());
                assertEquals("Price paid should match", 25.0, t.getPricePaid(), 0.01);
                break;
            }
        }
        assertTrue("Created ticket should be found", found);
        
        // Test getting tickets by non-existent event
        List<Ticket> noTickets = ticketDao.getTicketsByEventId(99999);
        assertNotNull("Tickets list should not be null even for invalid event", noTickets);
        assertEquals("Tickets list should be empty for non-existent event", 0, noTickets.size());
    }

    @Test
    public void testGetTicketById() {
        // First create a ticket
        Ticket ticket = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket);
        
        // Get the ticket ID
        List<Ticket> tickets = ticketDao.getTicketsByUserId(testUserId);
        int ticketId = -1;
        for (Ticket t : tickets) {
            if (t.getEventId() == testEventId) {
                ticketId = t.getId();
                testTicketId = ticketId;
                break;
            }
        }
        assertTrue("Ticket should be found", ticketId != -1);
        
        // Test getting ticket by valid ID
        Ticket retrievedTicket = ticketDao.getTicketById(ticketId);
        assertNotNull("Ticket should be found by ID", retrievedTicket);
        assertEquals("Ticket ID should match", ticketId, retrievedTicket.getId());
        assertEquals("User ID should match", testUserId, retrievedTicket.getUserId());
        assertEquals("Event ID should match", testEventId, retrievedTicket.getEventId());
        assertEquals("Price paid should match", 25.0, retrievedTicket.getPricePaid(), 0.01);
        
        // Test getting ticket by non-existent ID
        Ticket invalidTicket = ticketDao.getTicketById(99999);
        assertNull("Non-existent ticket should return null", invalidTicket);
    }

    @Test
    public void testGetTicketsWithEventDetailsByUserId() {
        // First create a ticket
        Ticket ticket = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket);
        
        // Test getting tickets with event details
        List<TicketWithEventDetails> ticketsWithDetails = 
            ticketDao.getTicketsWithEventDetailsByUserId(testUserId);
        
        assertNotNull("Tickets with details list should not be null", ticketsWithDetails);
        assertTrue("User should have at least one ticket with details", ticketsWithDetails.size() >= 1);
        
        // Verify ticket and event details
        boolean found = false;
        for (TicketWithEventDetails twd : ticketsWithDetails) {
            if (twd.getEventId() == testEventId) {
                found = true;
                testTicketId = twd.getTicketId();
                assertEquals("User ID should match", testUserId, twd.getUserId());
                assertEquals("Event ID should match", testEventId, twd.getEventId());
                assertEquals("Price paid should match", 25.0, twd.getPricePaid(), 0.01);
                assertEquals("Event title should match", "Test Event for Tickets", twd.getEventTitle());
                assertEquals("Event description should match", "Test event description", twd.getEventDesc());
                assertEquals("Event date should match", "2025-12-31", twd.getEventDate());
                assertEquals("Event time should match", "18:00", twd.getEventTime());
                assertEquals("Event location should match", "Test Venue", twd.getEventLocation());
                assertNotNull("Purchase date should be set", twd.getPurchaseDate());
                break;
            }
        }
        assertTrue("Created ticket with details should be found", found);
        
        // Test getting tickets with details for non-existent user
        List<TicketWithEventDetails> noTicketsWithDetails = 
            ticketDao.getTicketsWithEventDetailsByUserId(99999);
        assertNotNull("Tickets with details list should not be null even for invalid user", 
            noTicketsWithDetails);
        assertEquals("Tickets with details list should be empty for non-existent user", 
            0, noTicketsWithDetails.size());
    }

    @Test
    public void testTicketConstructors() {
        // Test constructor without ID (for creating new ticket)
        Ticket newTicket = new Ticket(testUserId, testEventId, 15.0);
        assertEquals("User ID should match", testUserId, newTicket.getUserId());
        assertEquals("Event ID should match", testEventId, newTicket.getEventId());
        assertEquals("Price paid should match", 15.0, newTicket.getPricePaid(), 0.01);
        assertNotNull("Purchase date should be set to current time", newTicket.getPurchaseDate());
        
        // Test constructor with all fields
        LocalDateTime now = LocalDateTime.now();
        Ticket fullTicket = new Ticket(1, testUserId, testEventId, now, 20.0);
        assertEquals("ID should match", 1, fullTicket.getId());
        assertEquals("User ID should match", testUserId, fullTicket.getUserId());
        assertEquals("Event ID should match", testEventId, fullTicket.getEventId());
        assertEquals("Purchase date should match", now, fullTicket.getPurchaseDate());
        assertEquals("Price paid should match", 20.0, fullTicket.getPricePaid(), 0.01);
        
        // Test default constructor
        Ticket emptyTicket = new Ticket();
        assertNotNull("Default constructor should create valid object", emptyTicket);
    }

    @Test
    public void testTicketSetters() {
        Ticket ticket = new Ticket();
        
        ticket.setId(123);
        ticket.setUserId(testUserId);
        ticket.setEventId(testEventId);
        ticket.setPricePaid(30.0);
        LocalDateTime testDate = LocalDateTime.of(2025, 6, 15, 14, 30);
        ticket.setPurchaseDate(testDate);
        
        assertEquals("ID should be set correctly", 123, ticket.getId());
        assertEquals("User ID should be set correctly", testUserId, ticket.getUserId());
        assertEquals("Event ID should be set correctly", testEventId, ticket.getEventId());
        assertEquals("Price paid should be set correctly", 30.0, ticket.getPricePaid(), 0.01);
        assertEquals("Purchase date should be set correctly", testDate, ticket.getPurchaseDate());
    }

    @Test
    public void testTicketWithEventDetailsClass() {
        // Test the inner TicketWithEventDetails class
        TicketWithEventDetails twd = new TicketWithEventDetails();
        
        LocalDateTime testDate = LocalDateTime.of(2025, 5, 20, 16, 45);
        
        twd.setTicketId(1);
        twd.setUserId(testUserId);
        twd.setEventId(testEventId);
        twd.setPurchaseDate(testDate);
        twd.setPricePaid(40.0);
        twd.setEventTitle("Test Event Title");
        twd.setEventDesc("Test Event Description");
        twd.setEventDate("2025-05-20");
        twd.setEventTime("16:45");
        twd.setEventLocation("Test Location");
        
        assertEquals("Ticket ID should match", 1, twd.getTicketId());
        assertEquals("User ID should match", testUserId, twd.getUserId());
        assertEquals("Event ID should match", testEventId, twd.getEventId());
        assertEquals("Purchase date should match", testDate, twd.getPurchaseDate());
        assertEquals("Price paid should match", 40.0, twd.getPricePaid(), 0.01);
        assertEquals("Event title should match", "Test Event Title", twd.getEventTitle());
        assertEquals("Event description should match", "Test Event Description", twd.getEventDesc());
        assertEquals("Event date should match", "2025-05-20", twd.getEventDate());
        assertEquals("Event time should match", "16:45", twd.getEventTime());
        assertEquals("Event location should match", "Test Location", twd.getEventLocation());
    }

    @Test
    public void testMultipleTicketsForSameEvent() {
        // Create another user
        eventflow.models.User secondUser = new eventflow.models.User(0, "Second Ticket User", 
            "seconduser@example.com", "password123", 50.0, 0);
        userDao.createUser(secondUser);
        eventflow.models.User retrievedSecondUser = userDao.getUserByEmailAndPassword(
            "seconduser@example.com", "password123");
        int secondUserId = retrievedSecondUser.getId();
        
        // Create tickets for both users for the same event
        Ticket ticket1 = new Ticket(testUserId, testEventId, 25.0);
        Ticket ticket2 = new Ticket(secondUserId, testEventId, 25.0);
        
        boolean result1 = ticketDao.createTicket(ticket1);
        boolean result2 = ticketDao.createTicket(ticket2);
        
        assertTrue("First ticket should be created", result1);
        assertTrue("Second ticket should be created", result2);
        
        // Verify both tickets exist for the event
        List<Ticket> eventTickets = ticketDao.getTicketsByEventId(testEventId);
        assertTrue("Event should have at least 2 tickets", eventTickets.size() >= 2);
        
        boolean foundUser1Ticket = false;
        boolean foundUser2Ticket = false;
        
        for (Ticket ticket : eventTickets) {
            if (ticket.getUserId() == testUserId) {
                foundUser1Ticket = true;
            } else if (ticket.getUserId() == secondUserId) {
                foundUser2Ticket = true;
            }
        }
        
        assertTrue("First user's ticket should be found", foundUser1Ticket);
        assertTrue("Second user's ticket should be found", foundUser2Ticket);
        
        // Clean up
        userDao.deleteUser(secondUserId);
    }

    @Test
    public void testMultipleTicketsForSameUser() {
        // Create another event
        Event secondEvent = new Event("Second Test Event", "Second event description", 
            5, 15.0, "2025-11-30", "20:00", "Second Venue", testUserId);
        eventDao.createEvent(secondEvent);
        
        // Find the second event ID
        List<EventDao.EventWithUser> events = eventDao.getEventsByUploader(testUserId);
        int secondEventId = -1;
        for (EventDao.EventWithUser event : events) {
            if (event.getEventTitle().equals("Second Test Event")) {
                secondEventId = event.getId();
                break;
            }
        }
        
        // Create tickets for both events for the same user
        Ticket ticket1 = new Ticket(testUserId, testEventId, 25.0);
        Ticket ticket2 = new Ticket(testUserId, secondEventId, 15.0);
        
        boolean result1 = ticketDao.createTicket(ticket1);
        boolean result2 = ticketDao.createTicket(ticket2);
        
        assertTrue("First ticket should be created", result1);
        assertTrue("Second ticket should be created", result2);
        
        // Verify both tickets exist for the user
        List<Ticket> userTickets = ticketDao.getTicketsByUserId(testUserId);
        assertTrue("User should have at least 2 tickets", userTickets.size() >= 2);
        
        boolean foundFirstEventTicket = false;
        boolean foundSecondEventTicket = false;
        
        for (Ticket ticket : userTickets) {
            if (ticket.getEventId() == testEventId) {
                foundFirstEventTicket = true;
            } else if (ticket.getEventId() == secondEventId) {
                foundSecondEventTicket = true;
            }
        }
        
        assertTrue("First event ticket should be found", foundFirstEventTicket);
        assertTrue("Second event ticket should be found", foundSecondEventTicket);
        
        // Clean up the second event
        eventDao.deleteEvent(secondEventId);
    }

    @Test
    public void testTicketOrderByPurchaseDate() {
        // Create multiple tickets with slight time differences
        Ticket ticket1 = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket1);
        
        // Small delay to ensure different timestamps
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        Ticket ticket2 = new Ticket(testUserId, testEventId, 25.0);
        ticketDao.createTicket(ticket2);
        
        // Get tickets (should be ordered by purchase_date DESC)
        List<Ticket> tickets = ticketDao.getTicketsByUserId(testUserId);
        assertTrue("Should have at least 2 tickets", tickets.size() >= 2);
        
        // Verify ordering (most recent first)
        if (tickets.size() >= 2) {
            LocalDateTime firstTicketDate = tickets.get(0).getPurchaseDate();
            LocalDateTime secondTicketDate = tickets.get(1).getPurchaseDate();
            assertTrue("First ticket should be more recent or equal", 
                !firstTicketDate.isBefore(secondTicketDate));
        }
    }
}
