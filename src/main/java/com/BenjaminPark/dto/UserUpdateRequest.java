package com.BenjaminPark.dto;

public class UserUpdateRequest {
    private final String name;
    private final char[] password;

    public UserUpdateRequest(String name, char[] password) {
        this.name = name;
        this.password = password != null ? password.clone() : null;
    }

    public String getName() {
        return name;
    }

    public char[] getPassword() {
        return password != null ? password.clone() : null;
    }
}

