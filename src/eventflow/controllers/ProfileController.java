/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventflow.controllers;

import eventflow.models.User;
import eventflow.views.Create;
import eventflow.views.Dashboard;
import eventflow.views.InfoForm;
import eventflow.views.MyEvents;
import eventflow.views.MyTickets;
import eventflow.views.ProfileForm;
import eventflow.views.WalletForm;

/**
 *
 * @author kisma
 */
public class ProfileController {
    public static void goToDashboard(User user) {

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

    public static void goToEditProfile(User user, ProfileForm profileForm) {
        InfoForm infoForm = new InfoForm(user, profileForm);
        infoForm.setVisible(true);
    }

    /**
     * Handle account deletion with confirmation
     * 
     * @param user       The user to delete
     * @param parentForm The parent form to close after deletion
     */
    public static void deleteAccount(User user, ProfileForm parentForm) {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                parentForm,
                "Are you sure you want to delete your account?\nThis action cannot be undone.",
                "Confirm Account Deletion",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            // Delete the account
            eventflow.dao.UserDAO userDao = new eventflow.dao.UserDAO();
            boolean deleted = userDao.deleteUser(user.getId());

            if (deleted) {
                javax.swing.JOptionPane.showMessageDialog(
                        parentForm,
                        "Account deleted successfully.",
                        "Account Deleted",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                // Navigate to signup form
                parentForm.dispose();
                new eventflow.views.SignupForm().setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                        parentForm,
                        "Failed to delete account. Please try again.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get the number of events created by the user
     * 
     * @param userId The user ID
     * @return The count of events created by the user
     */
    public static int getUserEventCount(int userId) {
        try {
            eventflow.dao.EventDao eventDao = new eventflow.dao.EventDao();
            java.util.List<eventflow.dao.EventDao.EventWithUser> userEvents = eventDao.getEventsByUploader(userId);
            return (userEvents != null) ? userEvents.size() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}