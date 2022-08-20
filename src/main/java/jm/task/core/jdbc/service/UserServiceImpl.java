package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl usDao = new UserDaoJDBCImpl();
    public void createUsersTable() throws SQLException {
        usDao.createUsersTable();
    }

    public void dropUsersTable() throws SQLException {
        usDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        usDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        usDao.removeUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return usDao.getAllUsers();
    }

    public void cleanUsersTable() throws SQLException {
        usDao.dropUsersTable();
        usDao.createUsersTable();
    }
}
