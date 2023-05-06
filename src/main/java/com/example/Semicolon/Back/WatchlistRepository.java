package com.example.Semicolon.Back;

import com.example.Semicolon.HomeController;
import com.example.Semicolon.database.Database;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<WatchlistEntity> list = getAll();
        for (WatchlistEntity entity : list){
            if (Objects.equals(entity.apiId, movie.apiId)){
                dao.deleteById(entity.id);
            }
        }
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
    public WatchlistEntity movieToWatchlist(Movie movie) {
        return new WatchlistEntity(movie.id, movie.title, movie.description, movie.genres, movie.imgUrl, (int) movie.releaseYear, (int) movie.length, movie.rating);
    }
    private static ConnectionSource createConneectionSource() throws SQLException {
        return new JdbcConnectionSource(Database.DB_URL, Database.username, Database.password);
    }

    public Movie WatchlistToMovie(WatchlistEntity entity){
        HomeController hc = new HomeController();
        for(Movie movie : hc.originalMovieList){
            if(Objects.equals(entity.apiId, movie.id)){
                return movie;
            }
        }
        return null;
    }
    public List<Movie> getWatchlistAsMovies(){
        List<Movie> watchlistMovie = new ArrayList<>();
        try {
            List<WatchlistEntity> WatchList = getAll();
            for(WatchlistEntity entity : WatchList){
                Movie movie = WatchlistToMovie(entity);
                if(movie != null){
                    watchlistMovie.add(movie);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return watchlistMovie;
    }
}
