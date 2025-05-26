package com.example.bms_backend.CartRepository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "lend_books_info")
@IdClass(LendInfo.class)
public class LendBookInfo {
    @Id
    private int bookId;
    @Id
    private String userId;
    @NotNull
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedTimestamp;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedTimestamp;
    public LendBookInfo(){
        this.bookId = 0;
        this.userId = null;
    }
    public LendBookInfo(int bookId, String userId, String status, Date borrowedTimestamp, Date returnedTimestamp){
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
        this.borrowedTimestamp = borrowedTimestamp;
        this.returnedTimestamp = returnedTimestamp;
    }
    public int getBookId(){
        return bookId;
    }
    public String getUserId(){
        return userId;
    }
    public String getStatus(){
        return status;
    }
    public Date getBorrowedTimestamp(){
        return borrowedTimestamp;
    }
    public Date getReturnedTimestamp(){
        return returnedTimestamp;
    }
    public void setReturnedTimestamp(Date newDate){
        this.returnedTimestamp = newDate;
    }
    public void setStatus(String newStatus){
        this.status = newStatus;
    }
}
class LendInfo implements Serializable{
    private int bookId;
    private String userId;
    public LendInfo(){}
    public LendInfo(int bookId, String userId){
        this.userId = userId;
        this.bookId = bookId;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        LendInfo lendId = (LendInfo)o;
        return Objects.equals(bookId, lendId.bookId) && Objects.equals(userId, lendId.userId);
    }
    @Override
    public int hashCode(){
        return Objects.hash(bookId, userId);
    }
}
