package com.example.Semicolon.Back.Controller.Pattern;

public class APIRequestBuilder {
    private String query, genre, releaseYear, ratingFrom, base;

    public APIRequestBuilder(String base){
        this.base = base;
    }

    public APIRequestBuilder query(String query){
        this.query = "query=" + query + "&";
        return this;
    }
    public APIRequestBuilder genre(String genre){
        this.genre = "genre=" + genre + "&";
        return this;
    }
    public APIRequestBuilder releaseYear(String releaseYear){
        this.releaseYear = "releaseYear=" + releaseYear + "&";
        return this;
    }
    public APIRequestBuilder ratingFrom(String ratingFrom){
        this.ratingFrom = "ratingFrom=" + ratingFrom;
        return this;
    }
    public String build(){
        return base + query + genre + releaseYear + ratingFrom;
    }
}
