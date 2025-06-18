package eventflow.controllers;

import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;

public class TicketController {
    
 
 
 public static double getUserBalance(User user) {
    return new eventflow.dao.UserDAO().getBalanceById(user.getId());
}

}
