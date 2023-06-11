package com.example.Semicolon.Back.Controller.Pattern;

import com.example.Semicolon.Back.Controller.HomeController;
import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class TitleZA implements State{
    @Override
    public ObservableList<Movie> doAction(ObservableList movieList) {
        Collections.sort(movieList, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.title.compareTo(o1.title);
            }
        });
        return movieList;
    }
    }

