package com.example.Semicolon.Back.Controller.Pattern;

import com.example.Semicolon.Back.Controller.HomeController;
import com.example.Semicolon.Back.Controller.LoginController;
import javafx.util.Callback;

public class Factory implements Callback<Class<?>, Object> {

    @Override
    public Object call(Class<?> aClass) {
        try{
            if(aClass.equals(LoginController.class)){
                return LoginController.getInstance();
            }else if(aClass.equals(HomeController.class)){
                return HomeController.getInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
