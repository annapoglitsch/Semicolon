package com.example.Semicolon.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "UserEntity")
public class UserEntity {
    @DatabaseField(generatedId = true)
    public long id;
    @DatabaseField()
    public String username, password, email;

    public UserEntity(){}
    public UserEntity(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
