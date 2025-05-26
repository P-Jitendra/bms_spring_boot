package com.example.bms_backend.BookControllers;

import com.example.bms_backend.BookRepository.BookInfo;
import java.util.List;

public class BookReturnGetMessage {
    private String status;
    private List<BookInfo> data;
    public BookReturnGetMessage(String status, List<BookInfo> booksList){
        this.status = status;
        this.data = booksList;
    }
    public String getStatus(){
        return status;
    }
    public List<BookInfo> getBooksList(){
        return data;
    }
}
