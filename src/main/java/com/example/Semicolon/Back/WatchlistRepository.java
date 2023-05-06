package com.example.Semicolon.Back;

import com.example.Semicolon.database.Database;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistEntity, Long> dao = setDao();

    private static Dao<WatchlistEntity, Long> setDao(){
        try {
            return DaoManager.createDao(createConneectionSource(), WatchlistEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        dao.delete(movie);
    }

    public List<WatchlistEntity> getAll() throws SQLException {
        List<WatchlistEntity> list = dao.queryForAll();
        return list;
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        dao.create(movie);
    }

    public static void main(String[] args) throws SQLException {
        Database.getDatabase();
        WatchlistRepository test = new WatchlistRepository();
        WatchlistEntity temp = new WatchlistEntity("movie", "movie", "movie", new String[] { "movie", "movie"}, "movie", 1,2,3);
        test.addToWatchlist(temp);
        for (WatchlistEntity movie : test.getAll()){
            System.out.println(movie.id);
        }
        //test.removeFromWatchlist(temp);
        for (WatchlistEntity movie : test.getAll()){
            System.out.println(movie.id);
        }
    }
    private static ConnectionSource createConneectionSource() throws SQLException {
        return new JdbcConnectionSource(Database.DB_URL, Database.username, Database.password);
    }
}
