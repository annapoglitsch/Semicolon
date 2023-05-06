package com.example.Semicolon.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class UserDatabase {
    public static final String DB_NAME = "jdbc:h2:file: ./Users/users", username = "admin", password = "admin";
    public static ConnectionSource connectionSource;
    public static Dao<User, Long> dao;
    private static UserDatabase instance;
    public static UserDatabase getUserDatabase(){
        if(instance == null){
            instance = new UserDatabase();
        }
        return instance;
    }
    private UserDatabase(){
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, User.class);
            createTables();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }
    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(DB_NAME, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
