package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.*;
import java.util.*;

public class Movie {
    public String id, title, description, imgUrl;
    public String[] genres, directors, writers, mainCast;
    public double rating, releaseYear, lengthInMinutes;

    public Movie(String id, String title, String[] genres, double releaseYear, String description, String imgUrl, double length, String[] directors, String[] writers, String[] mainCast, double rating) {
        this.id = id; //apiId
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = length;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }
    public Movie(Movie movie){
        this.id = movie.id; //apiId
        this.title = movie.title;
        this.genres = movie.genres;
        this.releaseYear = movie.releaseYear;
        this.description = movie.description;
        this.imgUrl = movie.imgUrl;
        this.lengthInMinutes = movie.lengthInMinutes;
        this.directors = movie.directors;
        this.writers = movie.writers;
        this.mainCast = movie.mainCast;
        this.rating = movie.rating;
    }

    public Movie() {}

    public double getReleaseYear() {
        return releaseYear;
    }
}


