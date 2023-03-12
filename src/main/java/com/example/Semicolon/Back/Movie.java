package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Movie {
    public String id, title, description, imgUrl, length;
    public String[] genres, directors, writers, cast;
    public double rating, releaseYear;

    public Movie(String id, String title, String[] genres, double releaseYear, String description, String imgUrl, String length, String[] directors, String[] writers, String[] cast, double rating){
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.length = length;
        this.directors = directors;
        this.writers = writers;
        this.cast = cast;
        this.rating = rating;
    }
    public Movie(){}
    public List<Movie> initializeMovies(String path) {
        List<Movie> movieList = new ArrayList<>();
        try {
            URL url = new URL(path);
            Scanner scanner = new Scanner(url.openStream());
            String temp = "";
            while(scanner.hasNext()){
                temp += scanner.nextLine();
            }
            movieList = new Gson().fromJson(temp, new TypeToken<List<Movie>>() {}.getType());
            return movieList;
        }catch (FileNotFoundException | MalformedURLException f){
            movieList.add(new Movie("error-404-Page-Not-Found", null, null, 0, null, "Error-404", null, null, null, null, 0));
            return movieList;
        } catch (UnknownHostException o){
            movieList.add(new Movie("error-502-Bad-Gateway-server", null, null, 0, null, "Error-502", null, null, null, null, 0));
            return movieList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
