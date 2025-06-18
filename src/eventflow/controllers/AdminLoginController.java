/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventflow.controllers;

import eventflow.models.User;
import eventflow.services.AuthService;
import eventflow.views.AdminLogin;   // your AdminLogin form
import eventflow.views.Dashboard;    // Assuming you have Dashboard for admin or create AdminDashboard if separate
import javax.swing.JOptionPane;

public class AdminLoginController {
    private AdminLogin adminLogin;
    private AuthService authService;

    public AdminLoginController(AdminLogin adminLogin) {
        this.adminLogin = adminLogin;
        this.authService = new AuthService();
    }
    
    public void handleAdminLogin(String email, String password) {
    User user = authService.login(email, password);
    if (user != null && user.getIsAdmin() == 1) {
        Dashboard dashboard = new Dashboard(user);
        dashboard.setVisible(true);
        adminLogin.dispose();
    } else {
        JOptionPane.showMessageDialog(adminLogin, "Invalid admin credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}

}