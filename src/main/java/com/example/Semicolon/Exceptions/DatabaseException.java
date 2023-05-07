package com.example.Semicolon.Exceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String str){
        super(str);
    }
}
