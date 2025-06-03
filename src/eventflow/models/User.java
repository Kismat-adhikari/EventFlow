package eventflow.models;

public class User {
    private final int id;
    private String fullname;  
    private final String email;
    private final String password;
    private double balance;
    private final int isAdmin;

    // Corrected constructor to use 'fullname' instead of 'name'
    public User(int id, String fullname, String email, String password, double balance, int isAdmin) {
        this.id = id;
        this.fullname = fullname;  // Changed from 'name'
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    // Getters
    public int getId() { return id; }
    
    // Changed from getName() to getFullname() to match field
    public String getFullname() { return fullname; }
    
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public int getIsAdmin() { return isAdmin; }

    // Setters
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    // Optional: Add setter for fullname if needed
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}