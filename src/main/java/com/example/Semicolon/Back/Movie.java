package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Movie {
    public String id, title, description, imgURL, legnth;
    public String[] genres, directors, writers, cast;
    public double rating, releaseYear;

    public Movie(String id, String title, String[] genres, double releaseYear, String description, String imgURL, String legnth, String[] directors, String[] writers, String[] cast, double rating){
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgURL = imgURL;
        this.legnth = legnth;
        this.directors = directors;
        this.writers = writers;
        this.cast = cast;
        this.rating = rating;
    }
    public Movie(){}
    public List<Movie> initializeMovies() throws IOException {
        URL url =  new URL("https://prog2.fh-campuswien.ac.at/movies");
        Scanner scanner = new Scanner(url.openStream());
        String temp = "";
        while(scanner.hasNext()){
            temp += scanner.nextLine();
        }
        List<Movie> movies = new Gson().fromJson(temp, new TypeToken<List<Movie>>() {}.getType());
        return movies;
    }
}
