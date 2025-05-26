package com.example.bms_backend.UserControllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class UserDTO {
    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("ContactNo")
    private long contactNo;
    @JsonProperty("Category")
    private String category;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public long getContactNo(){
        return contactNo;
    }
    public void setContactNo(long contactNo){
        this.contactNo = contactNo;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
}
