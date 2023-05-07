package com.example.Semicolon.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UserRepo {
    static Dao<UserEntity, Long> dao;

    public static void setDao(){
        try {
            dao = DaoManager.createDao(createConnectionSource(), UserEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static ConnectionSource createConnectionSource() throws SQLException {
        return new JdbcConnectionSource(UserDatabase.DB_NAME, UserDatabase.username, UserDatabase.password);
    }
    public static void addUser(UserEntity user) throws SQLException{
        dao.create(user);
    }
    public static List<UserEntity> getUsers() throws SQLException{
        return dao.queryForAll();
    }
}
