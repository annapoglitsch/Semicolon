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
import java.util.Observable;

public class WatchlistRepository extends Observable {
    static Dao<WatchlistEntity, Long> dao;
    private static WatchlistRepository instance = getWatchlistRepository();
    private WatchlistRepository(){
        this.addObserver(HomeController.getInstance());
        setDao();
    }
    public static WatchlistRepository getWatchlistRepository(){
        if(instance==null){
            instance = new WatchlistRepository();
        }
        return instance;
    }
    public static void setDao(){
        try {
            dao = DaoManager.createDao(createConneectionSource(), WatchlistEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        List<WatchlistEntity> list = getAll();
        for (WatchlistEntity entity : list){
            if (Objects.equals(entity.apiId, movie.apiId)){
                dao.deleteById(entity.id);
            }
        }
        setChanged();
        notifyObservers("remove");
    }

    public static List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        dao.create(movie);
        setChanged();
        notifyObservers("add");
    }

    public static WatchlistEntity movieToWatchlist(Movie movie) {
        return new WatchlistEntity(movie.id, movie.title, movie.description, movie.genres, movie.imgUrl, (int) movie.releaseYear, (int) movie.lengthInMinutes, movie.rating);
    }
    public static Movie WatchlistToMovie(WatchlistEntity entity){
        if(!Objects.equals(HomeController.originalMovieList.get(0).id, "error")) {
            for (Movie movie : HomeController.originalMovieList) {
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
        }
        return watchlistMovie;
    }
}
