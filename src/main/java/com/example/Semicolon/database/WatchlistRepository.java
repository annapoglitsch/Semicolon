package com.example.Semicolon.database;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.Controller.HomeController;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WatchlistRepository {
    static Dao<WatchlistEntity, Long> dao;

    public static void setDao(){
        try {
            dao = DaoManager.createDao(createConneectionSource(), WatchlistEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        List<WatchlistEntity> list = getAll();
        for (WatchlistEntity entity : list){
            if (Objects.equals(entity.apiId, movie.apiId)){
                dao.deleteById(entity.id);
                //dao.delete(entity);
            }
        }
    }

    public static List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public static void addToWatchlist(WatchlistEntity movie) throws SQLException {
        dao.create(movie);
    }

    public static void main(String[] args) throws SQLException {
        /*Database.getDatabase();
        WatchlistRepository test = new WatchlistRepository();
        WatchlistEntity temp = new WatchlistEntity("movie", "movie", "movie", new String[] { "movie", "movie"}, "movie", 1,2,3);
        test.addToWatchlist(temp);
        for (WatchlistEntity movie : test.getAll()){
            System.out.println(movie.id);
        }
        //test.removeFromWatchlist(temp);
        for (WatchlistEntity movie : test.getAll()){
            System.out.println(movie.id);
        }*/
    }
    public static WatchlistEntity movieToWatchlist(Movie movie) {
        return new WatchlistEntity(movie.id, movie.title, movie.description, movie.genres, movie.imgUrl, (int) movie.releaseYear, (int) movie.lengthInMinutes, movie.rating);
    }
    public static Movie WatchlistToMovie(WatchlistEntity entity){
        HomeController hc = new HomeController();
        if(!Objects.equals(hc.originalMovieList.get(0).id, "error")) {
            for (Movie movie : hc.originalMovieList) {
                if (Objects.equals(entity.apiId, movie.id)) {
                    return movie;
                }
            }
        }
        return null;
    }
    private static ConnectionSource createConneectionSource() throws SQLException {
        String url = "jdbc:h2:file: ./db/watchlistdb" + Database.username;
        return new JdbcConnectionSource(url, Database.username, Database.password);
    }

    public static List<Movie> getWatchlistAsMovies(){
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
        }/*
        if(watchlistMovie.isEmpty()){
            HomeController hc = new HomeController();
            if(Objects.equals(hc.originalMovieList.get(0).id, "error")){
                watchlistMovie.add(hc.originalMovieList.get(0));
            }
        }*/
        return watchlistMovie;
    }
}
