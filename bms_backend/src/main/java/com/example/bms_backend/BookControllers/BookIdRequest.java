package com.example.bms_backend.BookControllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookIdRequest {
    @JsonProperty("book_id_list")
    private final List<Integer> bookIdList;
    public BookIdRequest(List<Integer> bookIdList){
        this.bookIdList = bookIdList;
    }
    public List<Integer> getBookIdList(){
        return bookIdList;
    }
}
