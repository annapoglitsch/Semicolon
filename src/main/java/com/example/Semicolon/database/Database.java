package com.example.Semicolon.database;

import com.example.Semicolon.Exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static String username;
    public static String password;
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistdb";// + username;
    public static ConnectionSource connectionSource;
    public static Dao<WatchlistEntity, Long> dao;
    private static Database instance;
    public void testDB() throws SQLException {
        WatchlistEntity entity = new WatchlistEntity("test", "test", "test", new String[]{"test", "test", "test"}, "test", 1, 2,3);
        dao.create(entity);
    }
    private Database() throws DatabaseException, SQLException {
        createConnectionSource();
        dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
        createTables();
    }
    public static Database getDatabase() throws SQLException, DatabaseException {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    private static void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
        } catch (SQLException e) {
            throw new DatabaseException("Could not create Table");
        }
    }
    private static void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Could not connect");
        }
    }
}
