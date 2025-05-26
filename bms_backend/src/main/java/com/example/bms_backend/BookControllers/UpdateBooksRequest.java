package com.example.bms_backend.BookControllers;

import com.example.bms_backend.BookRepository.BookInfo;

public class UpdateBooksRequest {
    private final BookInfo bookInfo;
    public UpdateBooksRequest(BookInfo bookInfo){
        this.bookInfo = bookInfo;
    }
    public BookInfo getBookInfo(){
        return bookInfo;
    }
}
