package com.example.bms_backend.UserControllers;

public class CheckEmailRequest {
    private String email;
    private String password;

    public CheckEmailRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}

