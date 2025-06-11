package eventflow.controllers;

import eventflow.models.User;
import eventflow.views.Create;

public class DashboardController {

    public static void goToCreate(User user) {
        Create createPage = new Create(user);
        createPage.setVisible(true);
    }
}
