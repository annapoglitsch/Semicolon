package com.example.Semicolon.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Watchlist")
public class WatchlistEntity {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField()
    public String apiId, title, description, genres, imgURL;
    @DatabaseField()
    public int releaseYear, lenInMin;
    @DatabaseField()
    public double rating;
    public WatchlistEntity(){}

    public WatchlistEntity(String apild, String title, String description, String[] genres, String imgURL,int releaseYear, int lenInMin, double rating){
        this.apiId = apild;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.imgURL = imgURL;
        this.releaseYear = releaseYear;
        this.lenInMin = lenInMin;
        this.rating = rating;
    }
    private String genresToString(String[] genres){
        StringBuilder allGenres = new StringBuilder();
        for(String genre : genres){
            allGenres.append(genre).append(", ");
        }
        return allGenres.toString();
    }
}
