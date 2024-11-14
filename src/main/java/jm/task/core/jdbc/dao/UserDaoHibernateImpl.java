package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    public static void tryQuery(String sql) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age SMALLINT)";
        tryQuery(sql);
        System.out.println("Table created");

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        tryQuery(sql);
        System.out.println("Table dropped");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES ('" + name + "','" + lastName + "'," + String.valueOf(age) + ")";
        tryQuery(sql);
        System.out.println("User " + name + " created");
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=" + id;
        tryQuery(sql);
        System.out.println("User with id = " + id + " removed");
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        tryQuery(sql);
        System.out.println("Table cleaned");
    }
}
