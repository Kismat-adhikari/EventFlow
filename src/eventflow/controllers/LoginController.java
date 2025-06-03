package eventflow.controllers;

import eventflow.models.User;
import eventflow.services.AuthService;

public class LoginController {
    private AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    public User handleLogin(String email, String password) {
        return authService.login(email, password);
    }
}
    