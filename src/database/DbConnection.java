/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Eventflow"; // replace with your DB name
        String user = "root"; // timi haru ko MySQL ko username
        String password = "BEMUfyUA#0"; // timiharu ko MySQL ko password

        return DriverManager.getConnection(url, user, password);
    }
}
    