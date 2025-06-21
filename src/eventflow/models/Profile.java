package eventflow.models;

public class Profile {
    private int id;
    private int userId;
    private String bio;
    private String location;  // new field

    // Constructor including location (optional: you can add overloads as needed)
    public Profile(int userId, String bio, String location) {
        this.userId = userId;
        this.bio = bio;
        this.location = location;
    }

    // You may want a no-arg constructor too if needed
    public Profile() {
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}