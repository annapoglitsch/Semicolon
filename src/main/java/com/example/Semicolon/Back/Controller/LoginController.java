package com.example.Semicolon.Back.Controller;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.SendEmail;
import com.example.Semicolon.Exceptions.DatabaseException;
import com.example.Semicolon.database.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.Semicolon.Back.Controller.HomeController.allGenres;

public class LoginController {
    @FXML
    public GridPane HomeGrid;
    @FXML
    public Button switchLogin, switchRegister, login, register;
    @FXML
    public TextField registerUsername, loginUsername, email;
    @FXML
    public PasswordField loginPassword, registerPassword, registerRepeatPassword;
    @FXML
    public Label usernameLabel, passwordLabel;

    public void switchToRegister() {
        switchRegister.setDisable(true);
        switchRegister.setVisible(false);
        login.setDisable(true);
        login.setVisible(false);
        loginUsername.setDisable(true);
        loginUsername.setVisible(false);
        loginPassword.setDisable(true);
        loginPassword.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);

        switchLogin.setDisable(false);
        switchLogin.setVisible(true);
        register.setDisable(false);
        register.setVisible(true);
        registerUsername.setDisable(false);
        registerUsername.setVisible(true);
        registerPassword.setDisable(false);
        registerPassword.setVisible(true);
        registerRepeatPassword.setDisable(false);
        registerRepeatPassword.setVisible(true);
        email.setVisible(true);
        email.setDisable(false);
    }

    public void switchToLogin() {

        switchLogin.setDisable(true);
        switchLogin.setVisible(false);
        login.setDisable(false);
        login.setVisible(true);
        loginUsername.setDisable(false);
        loginUsername.setVisible(true);
        loginPassword.setDisable(false);
        loginPassword.setVisible(true);
        passwordLabel.setVisible(true);
        usernameLabel.setVisible(true);

        switchRegister.setDisable(false);
        switchRegister.setVisible(true);
        register.setDisable(true);
        register.setVisible(false);
        registerUsername.setDisable(true);
        registerUsername.setVisible(false);
        registerPassword.setDisable(true);
        registerPassword.setVisible(false);
        registerRepeatPassword.setDisable(true);
        registerRepeatPassword.setVisible(false);
        email.setVisible(false);
        email.setDisable(true);
    }

    public void login(ActionEvent event) {
        String password = loginPassword.getText();
        String username = loginUsername.getText();
        boolean exists = false;
        UserRepo repo = new UserRepo();
        try {
            List<UserEntity> users = repo.getUsers();
            for (UserEntity user : users) {
                if (Objects.equals(user.username, username) && Objects.equals(user.password, password)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                switchScene(username, password, event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(ActionEvent event) {
        String username = registerUsername.getText();
        String password = registerPassword.getText();
        String repeatPassword = registerRepeatPassword.getText();
        String mail = email.getText();
        if(username != null && password != null && mail != null && repeatPassword != null) {
            boolean exists = false;
            UserRepo repo = new UserRepo();
            List<UserEntity> users = null;
            try {
                users = repo.getUsers();
                for (UserEntity user : users) {
                    if (Objects.equals(user.username, username)) {
                        exists = true;
                    }
                }
                if (Objects.equals(password, repeatPassword) && !exists) {
                    UserRepo.addUser(new UserEntity(username, password, mail));
                    //SendEmail.send(mail);
                    switchScene(username, password, event);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        registerPassword.setText("");
        registerRepeatPassword.setText("");
    }
    private void switchScene(String username, String password, Event event){
        Database.username = username;
        Database.password = password;
        try {
            Database.getDatabase();
            HomeController.setOriginalMovieList();
            WatchlistRepository.getWatchlistRepository();
            HomeController.setWatchlist();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("test.fxml"))));
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        } catch (DatabaseException e) {
            HomeController.watchlist.add(new Movie("error", " ", allGenres, 0, " ", "Error \n Could not connect to database!", 0, null, null, null, 0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
