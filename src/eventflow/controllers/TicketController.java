package eventflow.controllers;

import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;

public class TicketController {
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
 public static void goToDashboard(User user) {
        Dashboard dashboardPage = new Dashboard(user);
        dashboardPage.setVisible(true);
    }
 
 
 public static double getUserBalance(User user) {
    return new eventflow.dao.UserDAO().getBalanceById(user.getId());
}

}
