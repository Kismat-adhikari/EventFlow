package eventflow.controllers;

import eventflow.models.User;
import eventflow.services.AuthService;
import eventflow.views.AdminLogin;
import eventflow.views.SignupForm;

public class LoginController {
    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }
    
//    called when user clicks on the lbael to go to signup
public void handleLoginToSignupClick() {
    new SignupForm().setVisible(true);
}

//to redirect user to admin
public void handleLoginToAdminClick() {
    new AdminLogin().setVisible(true);
}


    public User handleLogin(String email, String password) {
        return authService.login(email, password);
    }
}
    