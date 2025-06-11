package eventflow.services;

import eventflow.dao.UserDAO;
import eventflow.models.User;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    public boolean signup(User user) {
        return userDAO.createUser(user);
    }
}
