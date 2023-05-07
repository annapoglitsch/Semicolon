package com.example.Semicolon.Back;

import com.example.Semicolon.Exceptions.DatabaseException;
import com.example.Semicolon.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.example.Semicolon.Back.HomeController.allGenres;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*UserDatabase.getUserDatabase();
        String name, password;
        User currentUser = new User("user", "password", "test@gmail.com");
        UserRepo repo = new UserRepo();
        Scanner scan  = new Scanner(System.in);
        System.out.print("login: ");
        String coice = scan.next();
        if (Objects.equals(coice, "n")){
            System.out.print("Name: ");
             name = scan.next();
            System.out.print("Email: ");
            String email = scan.next();
            System.out.print("Password: ");
             password = scan.next();
            User user = new User(name, password, email);
            try {
                repo.addUser(user);
                currentUser = user;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.print("Name: ");
             name = scan.next();
            System.out.print("Password: ");
             password = scan.next();
            try {
                for(User user : repo.getUsers()) {
                    String username = user.username.replaceAll(String.valueOf(user.id), "");
                    if (username.equals(name)){
                        if(Objects.equals(user.password, password)){
                            currentUser = user;
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }*/
        Database.username = "user";
        Database.password = "password";
        try {
            Database.getDatabase();
            HomeController.setOriginalMovieList();
            HomeController.setWatchlist();
        } catch (DatabaseException e) {
            HomeController.watchlist.add(new Movie("error", " ", allGenres, 0, " ", "Error \n Could not connect to database!", 0, null, null, null, 0));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),  1000, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        System.out.println(HomeController.watchlist.size());
    }

    public static void main(String[] args) {
        launch();
    }
}