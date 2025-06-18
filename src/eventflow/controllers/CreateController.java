package eventflow.controllers;

import eventflow.models.Event;
import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.WalletForm;

public class CreateController {

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
 
 

  

    // Method to create a new event by delegating to the EventService
    public static boolean createEvent(Event event) {
        eventflow.services.EventService service = new eventflow.services.EventService();
        return service.addNewEvent(event);
    }
}
