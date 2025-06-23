package eventflow.controllers;

import eventflow.models.User;
import eventflow.services.ProfileService;

public class InfoController {

    public static void updateUserBio(User user, String newBio) {
        System.out.println("Updating bio for user: " + user.getId() + " with bio: " + newBio);
        ProfileService.updateBio(user.getId(), newBio);
    }

    public static void updateUserLocation(User user, String newLocation) {
        System.out.println("Updating location for user: " + user.getId() + " with location: " + newLocation);
        ProfileService.updateLocation(user.getId(), newLocation);
    }
    
    public static void updateUserProfile(User user, String newBio, String newLocation) {
        System.out.println("Updating profile for user: " + user.getId() + " with bio: '" + newBio + "' and location: '" + newLocation + "'");
        ProfileService.updateProfile(user.getId(), newBio, newLocation);
    }
}