package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // SECURITY ISSUE: Hardcoded credentials
    private String password = "admin123";

    // Parameterized query prevents SQL injection
    public void findUser(String username) throws SQLException {
        String query = "SELECT id, name FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "root", password);
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    rs.getLong("id");
                    rs.getString("name");
                }
            }
        }
    }

    // SMELL: Unused method
    public void notUsed() {
        System.out.println("I am never called");
    }

    // Parameterized query prevents SQL injection; try-with-resources closes resources
    public void deleteUser(String username) throws SQLException {
        String query = "DELETE FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "root", password);
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, username);
            st.executeUpdate();
        }
    }
}
