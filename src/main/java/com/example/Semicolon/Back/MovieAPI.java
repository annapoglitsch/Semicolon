package com.example.Semicolon.Back;

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
    public List<Movie> initializeMoviesNew(String path) {
        List<Movie> movieList = new ArrayList<>();
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String temp = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset())).lines().collect(Collectors.joining("\n"));
            movieList = new Gson().fromJson(temp, new TypeToken<List<Movie>>(){}.getType());
        } catch (MalformedURLException f) { // url not valid
            movieList.add(new Movie("error", " ", allGenres, 0, " ", "Error-404", 0, null, null, null, 0));
            return movieList;
        } catch (UnknownHostException o) { //no internet
            movieList.add(new Movie("error", " ", allGenres, 0, " ", "Error-502", 0, null, null, null, 0));
            return movieList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movieList;
    }

    public static void main(String[] args) {
        new MovieAPI().initializeMoviesNew("https://prog2.fh-campuswien.ac.at/movies");
    }
}
