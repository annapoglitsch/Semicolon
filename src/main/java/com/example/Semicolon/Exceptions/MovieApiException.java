package com.example.Semicolon.Exceptions;

public class MovieApiException extends RuntimeException {
    public MovieApiException(String str){
        super(str);
    }
}
