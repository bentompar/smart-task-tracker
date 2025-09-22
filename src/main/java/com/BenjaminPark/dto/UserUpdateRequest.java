package com.BenjaminPark.dto;

public class UserUpdateRequest {
    String name;
    char[] password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
