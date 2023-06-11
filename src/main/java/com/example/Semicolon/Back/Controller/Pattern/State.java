package com.example.Semicolon.Back.Controller.Pattern;

import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;

public interface State {
    public ObservableList<Movie> doAction(ObservableList movieList);
}
