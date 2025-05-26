package com.example.bms_backend.CartControllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
public class ReturnBookRequest {
    @JsonProperty("return_data")
    private final List<BorrowBook> returnData;
    public ReturnBookRequest(List<BorrowBook> returnData){
        this.returnData = returnData;
    }
    public List<BorrowBook> getReturnData(){
        return returnData;
    }
}
