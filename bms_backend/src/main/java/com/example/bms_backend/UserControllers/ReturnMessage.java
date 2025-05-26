package com.example.bms_backend.UserControllers;

public class ReturnMessage {
    private String status;
    private String message;
    public ReturnMessage(String newStatus, String newMessage){
        this.status = newStatus;
        this.message = newMessage;
    }
    public String getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
    public void setStatus(String newStatus){
        this.status = newStatus;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
