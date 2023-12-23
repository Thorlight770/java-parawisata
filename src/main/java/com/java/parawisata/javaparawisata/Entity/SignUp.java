package com.java.parawisata.javaparawisata.Entity;

public class SignUp {
    private String name;
    private String identityType;
    private String identityID;
    private String email;
    private String username;
    private String password;

    public SignUp() {
    }

    public SignUp(String name, String identityType, String identityID, String email, String username, String password) {
        this.name = name;
        this.identityType = identityType;
        this.identityID = identityID;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "SignUp{" +
                "name='" + name + '\'' +
                ", identityType='" + identityType + '\'' +
                ", identityID='" + identityID + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
