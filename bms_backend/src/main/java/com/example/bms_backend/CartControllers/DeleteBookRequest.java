package com.example.bms_backend.CartControllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteBookRequest {
    @JsonProperty("delete_data")
    private List<BorrowBook> deleteData;
    public DeleteBookRequest(){}
    public DeleteBookRequest(List<BorrowBook> deleteData){
        this.deleteData = deleteData;
    }
    public List<BorrowBook> getDeleteList(){
        return deleteData;
    }
}
