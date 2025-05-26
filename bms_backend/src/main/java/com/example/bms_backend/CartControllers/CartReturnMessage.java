package com.example.bms_backend.CartControllers;

public class CartReturnMessage {
    private final String status;
    private String message;
    public CartReturnMessage(String status, String message){
        this.status = status;
        this.message = message;
    }
    public String getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String newMessage){
        message = newMessage;
    }
}
