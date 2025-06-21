package eventflow.controllers;

import eventflow.models.User;
import eventflow.services.ProfileService;

public class InfoController {

    public static void updateUserBio(User user, String newBio) {
        ProfileService.updateBio(user.getId(), newBio);
    }

    public static void updateUserLocation(User user, String newLocation) {
        ProfileService.updateLocation(user.getId(), newLocation);
    }
}