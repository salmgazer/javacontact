package com.micontact.app.model;

public class User {
    private String id;
    private String name;
    private String username;
    private String phone;
    private String password;

    public User() {}

    public User(String name, String username, String phone, String id) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
