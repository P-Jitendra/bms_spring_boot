package com.example.bms_backend.UserControllers;

public class CheckUserIdRequest {
    private String userId;
    private String password;
    public CheckUserIdRequest(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }
    public String getPassword(){
        return password;
    }
}
