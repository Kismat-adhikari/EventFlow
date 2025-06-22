/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author kisma
 */
public class MyEventsController {
    
    private EventDao eventDao;
    
    public MyEventsController() {
        this.eventDao = new EventDao();
    }
    
    /**
     * Toggle edit mode for events display
     * @param isEditMode true to enter edit mode, false to exit
     * @return true if successful
     */
    public boolean toggleEditMode(boolean isEditMode) {
        // For now, just return true since we're not implementing save yet
        // This will be used later when implementing the save functionality
        return true;
    }
      /**
     * Save updated event data to database
     * @param eventId Event ID to update
     * @param title New event title
     * @param description New event description
     * @param time New event time
     * @param date New event date
     * @param location New event location
     * @param price New event price
     * @return true if successful, false otherwise
     */
    public boolean saveEvent(int eventId, String title, String description, String time, 
                           String date, String location, String price) {
        // Validate the data first
        if (!validateEventData(title, description, time, date, location, price)) {
            return false;
        }
        
        try {
            double priceValue = Double.parseDouble(price);
            return eventDao.updateEvent(eventId, title, description, time, date, location, priceValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate event data (for future save functionality)
     * @param title Event title
     * @param description Event description
     * @param time Event time
     * @param date Event date
     * @param location Event location
     * @param price Event price
     * @return true if valid, false otherwise
     */
    public boolean validateEventData(String title, String description, String time, 
                                   String date, String location, String price) {
        // Basic validation - not empty and price is valid number
        if (title == null || title.trim().isEmpty()) return false;
        if (description == null || description.trim().isEmpty()) return false;
        if (time == null || time.trim().isEmpty()) return false;
        if (date == null || date.trim().isEmpty()) return false;
        if (location == null || location.trim().isEmpty()) return false;
        
        try {
            double priceValue = Double.parseDouble(price);
            return priceValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
 
 
}
