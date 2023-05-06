package com.example.Semicolon.database;

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
    private Database(){
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
            createTables();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static Database getDatabase(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
    }
    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        public void saveMovie(Movie movie) throws DatabaseException {
            try {
                // Code to save movie to database
            } catch (Exception e) {
                throw new DatabaseException("Error saving movie to database.");
            }
        }

        public List<Movie> getAllMovies() throws DatabaseException {
            try {
                // Code to retrieve all movies from database
            } catch (Exception e) {
                throw new DatabaseException("Error retrieving movies from database.");
            }
        }

    }
}
