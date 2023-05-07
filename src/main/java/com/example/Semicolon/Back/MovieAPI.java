package com.example.Semicolon.Back;

import com.example.Semicolon.Exceptions.MovieApiException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieAPI {
    private final String[] allGenres = new String[]{"---ALL GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"};

    public List<Movie> initializeMoviesNew(String path) throws MovieApiException {
        List<Movie> movieList = new ArrayList<>();
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() != 200){
                throw new MovieApiException("Malformed URL");
            }
            String temp = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset())).lines().collect(Collectors.joining("\n"));
            movieList = new Gson().fromJson(temp, new TypeToken<List<Movie>>() {}.getType());
        } catch (UnknownHostException o) { //no internet
            throw new MovieApiException("No Internet connection");
        } catch (IOException e) {
            throw new MovieApiException("Unexpected Error");
        }
        return movieList;
    }

    public static void main(String[] args) {
        //new MovieAPI().initializeMoviesNew("https://prog2.fh-campuswien.ac.at/movies");
    }
}