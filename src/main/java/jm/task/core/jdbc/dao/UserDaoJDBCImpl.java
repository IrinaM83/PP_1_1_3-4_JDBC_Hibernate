package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        connection = getConnection();
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "  `id` INT(10) NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(45) NOT NULL," +
                "  `lastName` VARCHAR(45) NOT NULL," +
                "  `age` INT(3) NULL," +
                "  PRIMARY KEY (`id`))";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!statement.isClosed()) {
                statement.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        connection = getConnection();
        Statement statement = null;
        String sql = "DROP TABLE IF EXISTS users";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        connection = getConnection();
        String sql = "INSERT INTO users (name, lastName, age) value (?,?,?) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем Ц %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        connection = getConnection();
        List<User> result = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id, name, lastName, age FROM users";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                result.add(user);
            }
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {

    }
}
