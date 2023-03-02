package com.example.Semicolon.Back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class test {
    private void getFromWeb() throws IOException {
        URL url =  new URL("https://prog2.fh-campuswien.ac.at/movies");
        Scanner scanner = new Scanner(url.openStream());
        String temp = "";
        while(scanner.hasNext()){
            temp += scanner.nextLine();
        }
        List<Movie> movies = new Gson().fromJson(temp, new TypeToken<List<Movie>>() {}.getType());
        System.out.println(movies.get(1).title);
    }

    public static void main(String[] args) throws IOException {
        test test = new test();
        test.getFromWeb();
    }
}
