package com.shingetsu.datntruong.Models;

public class User {
    private String Username;
    private String Email;
    private String Phonenumber;
    private String Dateofbirth;
    private String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getDateofbirth() {
        return Dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        Dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(String username, String email, String phonenumber, String dateofbirth, String password) {
        Username = username;
        Email = email;
        Phonenumber = phonenumber;
        Dateofbirth = dateofbirth;
        Password = password;
    }
}
