package com.example.bms_backend.BookControllers;

import com.example.bms_backend.BookRepository.BookInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewBooksRequest {
    @JsonProperty("books_data")
    private List<BookDTO> booksData;

    public List<BookDTO> getBooksData(){
        return booksData;
    }
    public void setBooksData(List<BookDTO> booksInfoList){
        this.booksData = booksInfoList;
    }
}
