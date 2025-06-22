/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventflow.controllers;

import database.DbConnection;
import eventflow.dao.UserDAO;
import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.WalletForm;

/**
 *
 * @author User
 */
public class WalletController {
    
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
      public static boolean increaseBalance(User user, double amount) {
        UserDAO userDao = new UserDAO();
        try {
            // Get connection and update balance
            java.sql.Connection conn = DbConnection.getConnection();
            conn.setAutoCommit(false);
            
            boolean success = userDao.updateUserBalance(user.getId(), amount, conn);
            if (success) {
                // Update the user object with new balance
                user.setBalance(user.getBalance() + amount);
                conn.commit();
                conn.close();
                return true;
            } else {
                conn.rollback();
                conn.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
