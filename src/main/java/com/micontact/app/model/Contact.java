package com.micontact.app.model;

public class Contact {
    private String name;
    private String phone;
    private String userId;

    public Contact() {}

    public Contact(String name, String phone, String userId) {
        this.name = name;
        this.phone = phone;
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
