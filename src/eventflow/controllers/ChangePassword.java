/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventflow.controllers;

import eventflow.dao.UserDAO;
import eventflow.models.User;
import javax.swing.JOptionPane;

/**
 *
 * @author kisma
 */
public class ChangePassword {
    
    private UserDAO userDAO;
    
    public ChangePassword() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Change password for a user
     * @param user The current logged-in user
     * @param currentPassword The current password entered by user
     * @param newPassword The new password
     * @param confirmPassword The confirmation of new password
     * @return true if password change was successful, false otherwise
     */
    public boolean changePassword(User user, String currentPassword, String newPassword, String confirmPassword) {
        // Validate current password
        if (!user.getPassword().equals(currentPassword)) {
            JOptionPane.showMessageDialog(null, "Current password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate new password and confirmation match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "New password and confirmation password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate new password is not empty
        if (newPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate new password is different from current password
        if (newPassword.equals(currentPassword)) {
            JOptionPane.showMessageDialog(null, "New password must be different from current password!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Update password in database
        boolean success = userDAO.updatePassword(user.getId(), newPassword);
        
        if (success) {
            JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to change password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
