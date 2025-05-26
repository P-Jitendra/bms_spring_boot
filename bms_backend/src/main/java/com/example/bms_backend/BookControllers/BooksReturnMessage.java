package com.example.bms_backend.BookControllers;

public class BooksReturnMessage {
    private String status;
    private String message;
    public BooksReturnMessage(String status, String message){
        this.status = status;
        this.message = message;
    }
    public String getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
}
