package eventflow.dao;

import eventflow.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import database.DbConnection;

/**
 * Comprehensive test suite for UserDAO class.
 * Tests all CRUD operations and business logic methods.
 */
public class UserDAOTest {

    private UserDAO userDAO;
    private User testUser;
    private int testUserId = -1;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
        
        // Create a test user for testing
        testUser = new User(0, "Test User", "test@example.com", "password123", 100.0, 0);
        
        // Create the test user in database
        boolean created = userDAO.createUser(testUser);
        assertTrue("Test user should be created successfully", created);
        
        // Get the created user to obtain the ID
        User createdUser = userDAO.getUserByEmailAndPassword("test@example.com", "password123");
        assertNotNull("Created user should be retrievable", createdUser);
        testUserId = createdUser.getId();
    }

    @After
    public void tearDown() {
        // Clean up test data
        if (testUserId != -1) {
            userDAO.deleteUser(testUserId);
        }
    }

    @Test
    public void testCreateUser() {
        // Test creating a new user
        User newUser = new User(0, "New Test User", "newtest@example.com", "newpass123", 50.0, 0);
        boolean result = userDAO.createUser(newUser);
        
        assertTrue("User creation should be successful", result);
        
        // Verify the user was created by retrieving it
        User retrievedUser = userDAO.getUserByEmailAndPassword("newtest@example.com", "newpass123");
        assertNotNull("Created user should be retrievable", retrievedUser);
        assertEquals("Full name should match", "New Test User", retrievedUser.getFullname());
        assertEquals("Email should match", "newtest@example.com", retrievedUser.getEmail());
        assertEquals("Balance should match", 50.0, retrievedUser.getBalance(), 0.01);
        
        // Clean up
        userDAO.deleteUser(retrievedUser.getId());
    }

    @Test
    public void testCreateUserWithDuplicateEmail() {
        // Try to create a user with the same email as test user
        User duplicateUser = new User(0, "Duplicate User", "test@example.com", "different123", 75.0, 0);
        boolean result = userDAO.createUser(duplicateUser);
        
        // This should fail due to unique email constraint
        assertFalse("Creating user with duplicate email should fail", result);
    }

    @Test
    public void testGetUserByEmailAndPassword() {
        // Test valid credentials
        User retrievedUser = userDAO.getUserByEmailAndPassword("test@example.com", "password123");
        assertNotNull("User should be found with correct credentials", retrievedUser);
        assertEquals("User ID should match", testUserId, retrievedUser.getId());
        assertEquals("Full name should match", "Test User", retrievedUser.getFullname());
        assertEquals("Email should match", "test@example.com", retrievedUser.getEmail());
        assertEquals("Balance should match", 100.0, retrievedUser.getBalance(), 0.01);

        // Test invalid email
        User invalidEmailUser = userDAO.getUserByEmailAndPassword("invalid@example.com", "password123");
        assertNull("User should not be found with invalid email", invalidEmailUser);

        // Test invalid password
        User invalidPasswordUser = userDAO.getUserByEmailAndPassword("test@example.com", "wrongpassword");
        assertNull("User should not be found with invalid password", invalidPasswordUser);
    }

    @Test
    public void testGetUserById() {
        // Test valid user ID
        User retrievedUser = userDAO.getUserById(testUserId);
        assertNotNull("User should be found with valid ID", retrievedUser);
        assertEquals("User ID should match", testUserId, retrievedUser.getId());
        assertEquals("Full name should match", "Test User", retrievedUser.getFullname());
        assertEquals("Email should match", "test@example.com", retrievedUser.getEmail());

        // Test invalid user ID
        User invalidUser = userDAO.getUserById(99999);
        assertNull("User should not be found with invalid ID", invalidUser);
    }

    @Test
    public void testGetBalanceById() {
        // Test valid user ID
        double balance = userDAO.getBalanceById(testUserId);
        assertEquals("Balance should match expected value", 100.0, balance, 0.01);

        // Test invalid user ID
        double invalidBalance = userDAO.getBalanceById(99999);
        assertEquals("Invalid user ID should return 0.0", 0.0, invalidBalance, 0.01);
    }

    @Test
    public void testUpdatePassword() {
        String newPassword = "newpassword456";
        
        // Update password
        boolean result = userDAO.updatePassword(testUserId, newPassword);
        assertTrue("Password update should be successful", result);

        // Verify password was updated by trying to login with new password
        User userWithNewPassword = userDAO.getUserByEmailAndPassword("test@example.com", newPassword);
        assertNotNull("User should be found with new password", userWithNewPassword);

        // Verify old password no longer works
        User userWithOldPassword = userDAO.getUserByEmailAndPassword("test@example.com", "password123");
        assertNull("User should not be found with old password", userWithOldPassword);

        // Test updating password for invalid user ID
        boolean invalidResult = userDAO.updatePassword(99999, "somepassword");
        assertFalse("Updating password for invalid user should fail", invalidResult);
    }

    @Test
    public void testProcessPaymentSuccess() {
        // Create a second user as the creator
        User creator = new User(0, "Creator User", "creator@example.com", "creatorpass", 0.0, 0);
        boolean creatorCreated = userDAO.createUser(creator);
        assertTrue("Creator should be created", creatorCreated);
        
        User retrievedCreator = userDAO.getUserByEmailAndPassword("creator@example.com", "creatorpass");
        int creatorId = retrievedCreator.getId();

        // Test successful payment
        double paymentAmount = 25.0;
        boolean result = userDAO.processPayment(testUserId, creatorId, paymentAmount);
        assertTrue("Payment should be processed successfully", result);

        // Verify balances were updated correctly
        double buyerBalance = userDAO.getBalanceById(testUserId);
        double creatorBalance = userDAO.getBalanceById(creatorId);
        
        assertEquals("Buyer balance should be reduced", 75.0, buyerBalance, 0.01);
        assertEquals("Creator balance should be increased", 25.0, creatorBalance, 0.01);

        // Clean up
        userDAO.deleteUser(creatorId);
    }

    @Test
    public void testProcessPaymentInsufficientFunds() {
        // Create a second user as the creator
        User creator = new User(0, "Creator User 2", "creator2@example.com", "creatorpass", 0.0, 0);
        userDAO.createUser(creator);
        User retrievedCreator = userDAO.getUserByEmailAndPassword("creator2@example.com", "creatorpass");
        int creatorId = retrievedCreator.getId();

        // Test payment with insufficient funds (more than user's balance)
        double paymentAmount = 150.0; // User only has 100.0
        boolean result = userDAO.processPayment(testUserId, creatorId, paymentAmount);
        assertFalse("Payment should fail due to insufficient funds", result);

        // Verify balances remained unchanged
        double buyerBalance = userDAO.getBalanceById(testUserId);
        double creatorBalance = userDAO.getBalanceById(creatorId);
        
        assertEquals("Buyer balance should remain unchanged", 100.0, buyerBalance, 0.01);
        assertEquals("Creator balance should remain unchanged", 0.0, creatorBalance, 0.01);

        // Clean up
        userDAO.deleteUser(creatorId);
    }

    @Test
    public void testProcessPaymentInvalidUsers() {
        // Test payment with invalid buyer ID
        boolean result1 = userDAO.processPayment(99999, testUserId, 10.0);
        assertFalse("Payment should fail with invalid buyer ID", result1);

        // Test payment with invalid creator ID
        boolean result2 = userDAO.processPayment(testUserId, 99999, 10.0);
        assertFalse("Payment should fail with invalid creator ID", result2);
    }

    @Test
    public void testUpdateUserBalanceWithConnection() {
        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            // Test adding to balance
            boolean result1 = userDAO.updateUserBalance(testUserId, 50.0, conn);
            assertTrue("Balance update should be successful", result1);
            
            // Test subtracting from balance
            boolean result2 = userDAO.updateUserBalance(testUserId, -25.0, conn);
            assertTrue("Balance update should be successful", result2);
            
            conn.commit();
            
            // Verify final balance
            double finalBalance = userDAO.getBalanceById(testUserId);
            assertEquals("Final balance should be correct", 125.0, finalBalance, 0.01);
            
        } catch (SQLException e) {
            fail("Database connection should work: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUser() {
        // Create a temporary user for deletion test
        User tempUser = new User(0, "Temp User", "temp@example.com", "temppass", 25.0, 0);
        boolean created = userDAO.createUser(tempUser);
        assertTrue("Temp user should be created", created);
        
        User retrievedTempUser = userDAO.getUserByEmailAndPassword("temp@example.com", "temppass");
        int tempUserId = retrievedTempUser.getId();

        // Delete the user
        boolean deleted = userDAO.deleteUser(tempUserId);
        assertTrue("User deletion should be successful", deleted);

        // Verify user was deleted
        User deletedUser = userDAO.getUserById(tempUserId);
        assertNull("Deleted user should not be found", deletedUser);

        // Test deleting non-existent user
        boolean invalidDelete = userDAO.deleteUser(99999);
        assertFalse("Deleting non-existent user should return false", invalidDelete);
    }

    @Test
    public void testUserAdminFlag() {
        // Create an admin user
        User adminUser = new User(0, "Admin User", "admin@example.com", "adminpass", 0.0, 1);
        boolean created = userDAO.createUser(adminUser);
        assertTrue("Admin user should be created", created);
        
        User retrievedAdmin = userDAO.getUserByEmailAndPassword("admin@example.com", "adminpass");
        assertNotNull("Admin user should be retrievable", retrievedAdmin);
        assertEquals("Admin flag should be set", 1, retrievedAdmin.getIsAdmin());
        assertTrue("User should be identified as admin", retrievedAdmin.isAdmin());

        // Clean up
        userDAO.deleteUser(retrievedAdmin.getId());
    }

    @Test
    public void testZeroBalance() {
        // Create user with zero balance
        User zeroBalanceUser = new User(0, "Zero Balance User", "zero@example.com", "zeropass", 0.0, 0);
        boolean created = userDAO.createUser(zeroBalanceUser);
        assertTrue("Zero balance user should be created", created);
        
        User retrieved = userDAO.getUserByEmailAndPassword("zero@example.com", "zeropass");
        assertEquals("Balance should be zero", 0.0, retrieved.getBalance(), 0.01);

        // Clean up
        userDAO.deleteUser(retrieved.getId());
    }

    @Test
    public void testNegativeBalance() {
        // Test payment that would result in negative balance
        User creator = new User(0, "Creator User 3", "creator3@example.com", "creatorpass", 0.0, 0);
        userDAO.createUser(creator);
        User retrievedCreator = userDAO.getUserByEmailAndPassword("creator3@example.com", "creatorpass");
        int creatorId = retrievedCreator.getId();

        // Try to pay exactly the user's balance
        double paymentAmount = 100.0;
        boolean result = userDAO.processPayment(testUserId, creatorId, paymentAmount);
        assertTrue("Payment of exact balance should succeed", result);

        // Verify buyer has zero balance
        double buyerBalance = userDAO.getBalanceById(testUserId);
        assertEquals("Buyer should have zero balance", 0.0, buyerBalance, 0.01);

        // Clean up
        userDAO.deleteUser(creatorId);
    }
}
