package com.example.bms_backend.CartControllers;

import com.example.bms_backend.BookRepository.BookInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
public class AddToCartRequest {
    @JsonProperty("add_to_cart_data")
    private List<BorrowBook> addToCartData;
    public AddToCartRequest(){}
    public AddToCartRequest(List<BorrowBook> cartData){
        this.addToCartData = cartData;
    }
    public List<BorrowBook> getCartList(){
        return addToCartData;
    }
}
