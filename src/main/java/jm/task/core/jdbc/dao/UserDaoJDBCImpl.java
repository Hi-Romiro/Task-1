package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    public static void tryQuery(String query) {
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastname VARCHAR(255) NOT NULL,"
                + "age SMALLINT NOT NULL)";
        tryQuery(query);
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        tryQuery(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("User с ID – " + id + " удален из базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, lastname, age FROM users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastname");
                Byte age = rs.getByte("age");
                User user = new User(id, name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM users";
        tryQuery(query);
        System.out.println("Table cleaned");
    }
}