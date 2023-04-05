package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieAPI {
    public List<Movie> initializeMoviesNew(String path) {
        List<Movie> movieList;
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println();
            String temp = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset())).lines().collect(Collectors.joining("\n"));
            movieList = new Gson().fromJson(temp, new TypeToken<List<Movie>>(){}.getType());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movieList;
    }

    public static void main(String[] args) {
        new MovieAPI().initializeMoviesNew("https://prog2.fh-campuswien.ac.at/movies");
    }
}
