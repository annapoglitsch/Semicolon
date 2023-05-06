package com.example.Semicolon.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UserRepo {
    Dao<User, Long> dao = setDao();

    private static Dao<User, Long> setDao(){
        try {
            return DaoManager.createDao(createConnectionSource(), User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static ConnectionSource createConnectionSource() throws SQLException {
        return new JdbcConnectionSource(UserDatabase.DB_NAME, UserDatabase.username, UserDatabase.password);
    }
    public void addUser(User user) throws SQLException{
        dao.create(user);
    }
    public List<User> getUsers() throws SQLException{
        return dao.queryForAll();
    }
}
