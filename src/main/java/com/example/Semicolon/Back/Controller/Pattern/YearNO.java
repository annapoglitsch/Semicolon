package com.example.Semicolon.Back.Controller.Pattern;

import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class YearNO implements State{
    @Override
    public ObservableList<Movie> doAction(ObservableList movieList) {
        Collections.sort(movieList, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.releaseYear > o2.releaseYear){
                    return -1;
                }
                return 1;
            }
        });
        return movieList;
    }
}
