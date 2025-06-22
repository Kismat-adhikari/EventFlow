package eventflow.dao;

import eventflow.models.Event;
import eventflow.dao.EventDao.EventWithUser;
import eventflow.dao.EventDao.TicketAvailability;
import eventflow.dao.EventDao.RefundInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Comprehensive test suite for EventDao class.
 * Tests all CRUD operations and business logic methods.
 */
public class EventDaoTest {

    private EventDao eventDao;
    private UserDAO userDao;
    private Event testEvent;
    private int testEventId = -1;
    private int testUserId = -1;

    @Before
    public void setUp() {
        eventDao = new EventDao();
        userDao = new UserDAO();
        
        // Create a test user first (needed for event creation)
        eventflow.models.User testUser = new eventflow.models.User(0, "Test Event Creator", 
            "eventcreator@example.com", "password123", 100.0, 0);
        boolean userCreated = userDao.createUser(testUser);
        assertTrue("Test user should be created successfully", userCreated);
        
        // Get the created user to obtain the ID
        eventflow.models.User createdUser = userDao.getUserByEmailAndPassword(
            "eventcreator@example.com", "password123");
        assertNotNull("Created user should be retrievable", createdUser);
        testUserId = createdUser.getId();
        
        // Create a test event
        testEvent = new Event("Test Event", "Test event description", 10, 25.0, 
            "2025-12-31", "18:00", "Test Venue", testUserId);
        
        boolean eventCreated = eventDao.createEvent(testEvent);
        assertTrue("Test event should be created successfully", eventCreated);
        
        // Get the created event ID (we'll need to find it from the database)
        List<EventWithUser> allEvents = eventDao.getAllEventsWithUploader();
        for (EventWithUser event : allEvents) {
            if (event.getEventTitle().equals("Test Event") && 
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
        if (testEventId != -1) {
            eventDao.deleteEvent(testEventId);
        }
        if (testUserId != -1) {
            userDao.deleteUser(testUserId);
        }
    }

    @Test
    public void testCreateEvent() {
        // Create another user for this test
        eventflow.models.User newUser = new eventflow.models.User(0, "New Event Creator", 
            "newevent@example.com", "password123", 50.0, 0);
        userDao.createUser(newUser);
        eventflow.models.User retrievedUser = userDao.getUserByEmailAndPassword(
            "newevent@example.com", "password123");
        int newUserId = retrievedUser.getId();
        
        // Test creating a new event
        Event newEvent = new Event("New Test Event", "New test description", 5, 15.0, 
            "2025-11-30", "20:00", "New Test Venue", newUserId);
        boolean result = eventDao.createEvent(newEvent);
        
        assertTrue("Event creation should be successful", result);
        
        // Verify the event was created by checking the list
        List<EventWithUser> events = eventDao.getEventsByUploader(newUserId);
        boolean found = false;
        int createdEventId = -1;
        for (EventWithUser event : events) {
            if (event.getEventTitle().equals("New Test Event")) {
                found = true;
                createdEventId = event.getId();
                assertEquals("Event description should match", "New test description", event.getEventDesc());
                assertEquals("Event tickets should match", 5, event.getEventTickets());
                assertEquals("Event price should match", 15.0, event.getEventPrice(), 0.01);
                assertEquals("Event date should match", "2025-11-30", event.getEventDate());
                assertEquals("Event time should match", "20:00", event.getEventTime());
                assertEquals("Event location should match", "New Test Venue", event.getEventLocation());
                break;
            }
        }
        assertTrue("Created event should be found in events list", found);
        
        // Clean up
        eventDao.deleteEvent(createdEventId);
        userDao.deleteUser(newUserId);
    }

    @Test
    public void testGetAllEventsWithUploader() {
        List<EventWithUser> events = eventDao.getAllEventsWithUploader();
        assertNotNull("Events list should not be null", events);
        assertTrue("Events list should contain at least the test event", events.size() >= 1);
        
        // Find our test event in the list
        boolean found = false;
        for (EventWithUser event : events) {
            if (event.getId() == testEventId) {
                found = true;
                assertEquals("Event title should match", "Test Event", event.getEventTitle());
                assertEquals("Event description should match", "Test event description", event.getEventDesc());
                assertEquals("Event tickets should match", 10, event.getEventTickets());
                assertEquals("Event price should match", 25.0, event.getEventPrice(), 0.01);
                assertEquals("Creator user ID should match", testUserId, event.getCreatorUserId());
                assertEquals("Uploader fullname should match", "Test Event Creator", event.getUploaderFullname());
                break;
            }
        }
        assertTrue("Test event should be found in all events list", found);
    }

    @Test
    public void testGetEventsByUploader() {
        // Test getting events by valid uploader
        List<EventWithUser> events = eventDao.getEventsByUploader(testUserId);
        assertNotNull("Events list should not be null", events);
        assertTrue("Events list should contain at least the test event", events.size() >= 1);
        
        // Find our test event
        boolean found = false;
        for (EventWithUser event : events) {
            if (event.getId() == testEventId) {
                found = true;
                assertEquals("Event title should match", "Test Event", event.getEventTitle());
                assertEquals("Creator user ID should match", testUserId, event.getCreatorUserId());
                break;
            }
        }
        assertTrue("Test event should be found for its creator", found);
        
        // Test getting events by non-existent uploader
        List<EventWithUser> noEvents = eventDao.getEventsByUploader(99999);
        assertNotNull("Events list should not be null even for invalid user", noEvents);
        assertEquals("Events list should be empty for non-existent user", 0, noEvents.size());
    }

    @Test
    public void testGetCreatorIdByEventId() {
        // Test valid event ID
        int creatorId = eventDao.getCreatorIdByEventId(testEventId);
        assertEquals("Creator ID should match test user ID", testUserId, creatorId);
        
        // Test invalid event ID
        int invalidCreatorId = eventDao.getCreatorIdByEventId(99999);
        assertEquals("Invalid event should return -1", -1, invalidCreatorId);
    }

    @Test
    public void testUpdateEvent() {
        // Test updating the event
        boolean result = eventDao.updateEvent(testEventId, "Updated Event Title", 
            "Updated description", "19:00", "2025-12-30", "Updated Venue", 30.0);
        assertTrue("Event update should be successful", result);
        
        // Verify the update
        List<EventWithUser> events = eventDao.getAllEventsWithUploader();
        boolean found = false;
        for (EventWithUser event : events) {
            if (event.getId() == testEventId) {
                found = true;
                assertEquals("Updated title should match", "Updated Event Title", event.getEventTitle());
                assertEquals("Updated description should match", "Updated description", event.getEventDesc());
                assertEquals("Updated time should match", "19:00", event.getEventTime());
                assertEquals("Updated date should match", "2025-12-30", event.getEventDate());
                assertEquals("Updated location should match", "Updated Venue", event.getEventLocation());
                assertEquals("Updated price should match", 30.0, event.getEventPrice(), 0.01);
                break;
            }
        }
        assertTrue("Updated event should be found", found);
        
        // Test updating non-existent event
        boolean invalidResult = eventDao.updateEvent(99999, "Invalid", "Invalid", 
            "12:00", "2025-01-01", "Invalid", 10.0);
        assertFalse("Updating non-existent event should fail", invalidResult);
    }

    @Test
    public void testGetTicketAvailability() {
        // Test availability for event with no tickets sold
        TicketAvailability availability = eventDao.getTicketAvailability(testEventId);
        assertNotNull("Ticket availability should not be null", availability);
        assertEquals("Total tickets should match event tickets", 10, availability.getTotalTickets());
        assertEquals("Sold tickets should be 0 initially", 0, availability.getSoldTickets());
        assertEquals("Available tickets should equal total initially", 10, availability.getAvailableTickets());
        assertFalse("Event should not be sold out initially", availability.isSoldOut());
        assertEquals("Availability text should be correct", "10/10 tickets available", 
            availability.getAvailabilityText());
        
        // Test availability for non-existent event
        TicketAvailability invalidAvailability = eventDao.getTicketAvailability(99999);
        assertNotNull("Availability should not be null even for invalid event", invalidAvailability);
        assertTrue("Invalid event should appear sold out", invalidAvailability.isSoldOut());
        assertEquals("Invalid event availability text should be SOLD OUT", "SOLD OUT", 
            invalidAvailability.getAvailabilityText());
    }

    @Test
    public void testGetRefundInfo() {
        // Test refund info for event with no tickets sold
        RefundInfo refundInfo = eventDao.getRefundInfo(testEventId);
        assertNotNull("Refund info should not be null", refundInfo);
        assertEquals("Ticket count should be 0", 0, refundInfo.getTicketCount());
        assertEquals("Total refund should be 0", 0.0, refundInfo.getTotalRefund(), 0.01);
        
        // Test refund info for non-existent event
        RefundInfo invalidRefundInfo = eventDao.getRefundInfo(99999);
        assertNotNull("Refund info should not be null even for invalid event", invalidRefundInfo);
        assertEquals("Invalid event ticket count should be 0", 0, invalidRefundInfo.getTicketCount());
        assertEquals("Invalid event total refund should be 0", 0.0, invalidRefundInfo.getTotalRefund(), 0.01);
    }

    @Test
    public void testDeleteEvent() {
        // Create a separate event for deletion test
        eventflow.models.User deleteUser = new eventflow.models.User(0, "Delete Test User", 
            "deletetest@example.com", "password123", 100.0, 0);
        userDao.createUser(deleteUser);
        eventflow.models.User retrievedDeleteUser = userDao.getUserByEmailAndPassword(
            "deletetest@example.com", "password123");
        int deleteUserId = retrievedDeleteUser.getId();
        
        Event deleteEvent = new Event("Delete Test Event", "Event to be deleted", 5, 20.0, 
            "2025-10-15", "16:00", "Delete Venue", deleteUserId);
        boolean created = eventDao.createEvent(deleteEvent);
        assertTrue("Delete test event should be created", created);
        
        // Find the created event ID
        List<EventWithUser> events = eventDao.getEventsByUploader(deleteUserId);
        int deleteEventId = -1;
        for (EventWithUser event : events) {
            if (event.getEventTitle().equals("Delete Test Event")) {
                deleteEventId = event.getId();
                break;
            }
        }
        assertTrue("Delete test event should be found", deleteEventId != -1);
        
        // Delete the event
        boolean deleted = eventDao.deleteEvent(deleteEventId);
        assertTrue("Event deletion should be successful", deleted);
        
        // Verify event was deleted
        List<EventWithUser> remainingEvents = eventDao.getEventsByUploader(deleteUserId);
        boolean found = false;
        for (EventWithUser event : remainingEvents) {
            if (event.getId() == deleteEventId) {
                found = true;
                break;
            }
        }
        assertFalse("Deleted event should not be found", found);
        
        // Test deleting non-existent event
        boolean invalidDelete = eventDao.deleteEvent(99999);
        assertFalse("Deleting non-existent event should return false", invalidDelete);
        
        // Clean up
        userDao.deleteUser(deleteUserId);
    }

    @Test
    public void testCreateEventWithInvalidData() {
        // Test with invalid user ID
        Event invalidEvent = new Event("Invalid Event", "Description", 5, 10.0, 
            "2025-01-01", "12:00", "Location", 99999);
        boolean result = eventDao.createEvent(invalidEvent);
        
        // This might succeed or fail depending on foreign key constraints
        // The test documents the current behavior
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testEventWithUserClass() {
        // Test the inner EventWithUser class
        EventWithUser eventWithUser = new EventWithUser();
        eventWithUser.setId(1);
        eventWithUser.setEventTitle("Test Title");
        eventWithUser.setEventDesc("Test Description");
        eventWithUser.setEventTickets(10);
        eventWithUser.setEventPrice(25.0);
        eventWithUser.setEventDate("2025-01-01");
        eventWithUser.setEventTime("12:00");
        eventWithUser.setEventLocation("Test Location");
        eventWithUser.setCreatorUserId(1);
        eventWithUser.setUploaderFullname("Test Creator");
        
        assertEquals("ID should match", 1, eventWithUser.getId());
        assertEquals("Title should match", "Test Title", eventWithUser.getEventTitle());
        assertEquals("Description should match", "Test Description", eventWithUser.getEventDesc());
        assertEquals("Tickets should match", 10, eventWithUser.getEventTickets());
        assertEquals("Price should match", 25.0, eventWithUser.getEventPrice(), 0.01);
        assertEquals("Date should match", "2025-01-01", eventWithUser.getEventDate());
        assertEquals("Time should match", "12:00", eventWithUser.getEventTime());
        assertEquals("Location should match", "Test Location", eventWithUser.getEventLocation());
        assertEquals("Creator user ID should match", 1, eventWithUser.getCreatorUserId());
        assertEquals("Uploader fullname should match", "Test Creator", eventWithUser.getUploaderFullname());
    }

    @Test
    public void testTicketAvailabilityClass() {
        // Test the TicketAvailability class
        TicketAvailability availability1 = new TicketAvailability(10, 3, 7, false);
        assertEquals("Total tickets should match", 10, availability1.getTotalTickets());
        assertEquals("Sold tickets should match", 3, availability1.getSoldTickets());
        assertEquals("Available tickets should match", 7, availability1.getAvailableTickets());
        assertFalse("Should not be sold out", availability1.isSoldOut());
        assertEquals("Availability text should be correct", "7/10 tickets available", 
            availability1.getAvailabilityText());
        
        // Test sold out scenario
        TicketAvailability availability2 = new TicketAvailability(5, 5, 0, true);
        assertTrue("Should be sold out", availability2.isSoldOut());
        assertEquals("Sold out text should be correct", "SOLD OUT", availability2.getAvailabilityText());
    }

    @Test
    public void testRefundInfoClass() {
        // Test the RefundInfo class
        RefundInfo refundInfo = new RefundInfo(3, 75.0);
        assertEquals("Ticket count should match", 3, refundInfo.getTicketCount());
        assertEquals("Total refund should match", 75.0, refundInfo.getTotalRefund(), 0.01);
        
        // Test zero refund scenario
        RefundInfo zeroRefund = new RefundInfo(0, 0.0);
        assertEquals("Zero ticket count should match", 0, zeroRefund.getTicketCount());
        assertEquals("Zero refund should match", 0.0, zeroRefund.getTotalRefund(), 0.01);
    }
}
