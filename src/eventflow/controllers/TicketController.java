package eventflow.controllers;

import eventflow.dao.TicketDao;
import eventflow.dao.TicketDao.TicketWithEventDetails;
import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.SignupForm;
import eventflow.views.WalletForm;
import java.util.List;

public class TicketController {
    
    public static void goToDashboard(User user){
        
        Dashboard dashboardPage = new Dashboard(user);
        dashboardPage.setVisible(true);
    }
    public static void goToCreate(User user) {
        Create createPage = new Create(user);
        createPage.setVisible(true);
    }

    public static void goToMyEvents(User user) {
        MyEvents myEventsPage = new MyEvents(user);
        myEventsPage.setVisible(true);
    }
    public static void goToSignup() {
        SignupForm signupPage = new SignupForm();
        signupPage.setVisible(true);
    }

    public static void goToMyTickets(User user) {
        MyTickets myTicketsPage = new MyTickets(user);
        
        try {
            TicketDao ticketDao = new TicketDao();
            List<TicketWithEventDetails> tickets = ticketDao.getTicketsWithEventDetailsByUserId(user.getId());
            
            // Pass ticket list to MyTickets UI to display
            myTicketsPage.loadTickets(tickets);
            System.out.println("Number of tickets fetched for user " + user.getId() + ": " + tickets.size());
            
            myTicketsPage.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error loading tickets: " + e.getMessage());
        }
    }

    public static void goToProfile(User user) {
        ProfileForm profilePage = new ProfileForm(user);
        profilePage.setVisible(true);
    }
    
    public static void goToWallet(User user) {
    WalletForm walletPage = new WalletForm(user);
    walletPage.setVisible(true);
}
 
 
 public static double getUserBalance(User user) {
    return new eventflow.dao.UserDAO().getBalanceById(user.getId());
}

}
