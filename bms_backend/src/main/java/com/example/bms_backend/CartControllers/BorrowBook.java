package com.example.bms_backend.CartControllers;

public class BorrowBook {
    private int bookId;
    private String userId;
    public BorrowBook(int bookId, String userId){
        this.bookId = bookId;
        this.userId = userId;
    }
    public int getBookId(){
        return bookId;
    }
    public String getUserId(){
        return userId;
    }
}
