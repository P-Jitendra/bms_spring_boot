package com.example.bms_backend.CartControllers;

import com.example.bms_backend.CartRepository.LendBookInfo;

import java.util.List;

public class CartReturnGetMessage {
    private final String status;
    private final List<LendBookInfo> bookInfoList;
    public CartReturnGetMessage(String status, List<LendBookInfo> booksList){
        this.status = status;
        this.bookInfoList = booksList;
    }
    public String getStatus(){
        return status;
    }
    public List<LendBookInfo> getBooksList(){
        return bookInfoList;
    }
}
