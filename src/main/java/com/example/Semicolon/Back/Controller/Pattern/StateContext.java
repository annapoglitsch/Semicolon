package com.example.Semicolon.Back.Controller.Pattern;

import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;

public class StateContext implements State{
    private State listState;

    public void setState(State state){
        this.listState = state;
    }

    public State getState(){
        return this.listState;
    }

    @Override
    public ObservableList<Movie> doAction(ObservableList movieList) {
       return this.listState.doAction(movieList);
    }
}
