package eventflow.controllers;

import eventflow.dao.EventDao;
import eventflow.dao.EventDao.EventWithUser;
import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.WalletForm;
import java.util.List;

public class DashboardController {

    public static void goToDashboard(User user) {
        Dashboard dashboardPage = new Dashboard(user);

        try {
            EventDao eventDao = new EventDao();
            List<EventWithUser> events = eventDao.getAllEventsWithUploader();

            // Pass event list to Dashboard UI to display
            dashboardPage.loadEvents(events);
            System.out.println("Number of events fetched: " + events.size());


            dashboardPage.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error loading events: " + e.getMessage());
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
    
    public static void goToWallet(User user) {
    WalletForm walletPage = new WalletForm(user);
    walletPage.setVisible(true);
}

}
