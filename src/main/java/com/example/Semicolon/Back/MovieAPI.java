package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
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
}
