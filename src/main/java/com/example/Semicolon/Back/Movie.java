package com.example.Semicolon.Back;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Movie {
    public String id, title, description, imgUrl, legnth;
    public String[] genres, directors, writers, cast;
    public double rating, releaseYear;

    public Movie(String id, String title, String[] genres, double releaseYear, String description, String imgUrl, String legnth, String[] directors, String[] writers, String[] cast, double rating){
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.legnth = legnth;
        this.directors = directors;
        this.writers = writers;
        this.cast = cast;
        this.rating = rating;
    }
    public Movie(){}
    public List<Movie> initializeMovies() {
        URL url;
        try {
            url = new URL("https://prog2.fh-campuswien.ac.at/movies");
            Scanner scanner = new Scanner(url.openStream());
            String temp = "";
            while(scanner.hasNext()){
                temp += scanner.nextLine();
            }
            List<Movie> movies = new Gson().fromJson(temp, new TypeToken<List<Movie>>() {}.getType());
            return movies;
        }catch (FileNotFoundException f){
            System.out.println("no page found");
        } catch (UnknownHostException o){
            System.out.println("no Internet connection");
        } catch (MalformedURLException e) {
            System.out.println("no page found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
