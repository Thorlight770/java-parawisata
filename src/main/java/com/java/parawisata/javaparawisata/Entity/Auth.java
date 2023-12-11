package com.java.parawisata.javaparawisata.Entity;

import com.java.parawisata.javaparawisata.Entity.Constraint.Role;

public class Auth {
    private String username;
    private String password;
    private String role;
    private String userID;

    public Auth() {
    }

    public Auth(String username, String password, String role, String userID) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
