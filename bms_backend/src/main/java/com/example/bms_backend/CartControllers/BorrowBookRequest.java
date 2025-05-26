package com.example.bms_backend.CartControllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BorrowBookRequest {
    @JsonProperty("lend_data")
    private List<BorrowBook> lendData;
    public BorrowBookRequest(){}
    public BorrowBookRequest(List<BorrowBook> lendList){
        this.lendData = lendList;
    }
    public List<BorrowBook> getBorrowList(){
        return lendData;
    }
}
