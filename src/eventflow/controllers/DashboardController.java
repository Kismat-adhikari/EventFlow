package eventflow.controllers;

import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;

import eventflow.dao.EventDao;
import eventflow.dao.EventDao.EventWithUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DashboardController {

    public static void goToDashboard(User user) {
        Dashboard dashboardPage = new Dashboard(user);

        Connection conn = null;
        try {
            // Connect to your database - replace with your actual connection details
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");

            EventDao eventDao = new EventDao(conn);
            List<EventWithUser> events = eventDao.getAllEventsWithUploader();

            // Pass event list to Dashboard UI to display
            dashboardPage.loadEvents(events);
            System.out.println("Number of events fetched: " + events.size());


            dashboardPage.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public static void goToCreate(User user) {
        Create createPage = new Create(user);
        createPage.setVisible(true);
    }

    public static void goToMyEvents(User user) {
        MyEvents myEventsPage = new MyEvents(user);
        myEventsPage.setVisible(true);
    }

    public static void goToMyTickets(User user) {
        MyTickets myTicketsPage = new MyTickets(user);
        myTicketsPage.setVisible(true);
    }

    public static void goToProfile(User user) {
        ProfileForm profilePage = new ProfileForm(user);
        profilePage.setVisible(true);
    }
}
