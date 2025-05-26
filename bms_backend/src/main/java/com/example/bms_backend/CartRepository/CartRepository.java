package com.example.bms_backend.CartRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CartRepository extends JpaRepository<LendBookInfo, LendInfo> {
    List<LendBookInfo> findByUserIdAndBookIdAndStatus(String userId, int bookId, String status);
    List<LendBookInfo> findByUserIdAndBookIdAndStatusAndBorrowedTimestamp(String userId, int bookId, String status, Date borrowTimestamp);
    List<LendBookInfo> findByBookIdAndStatus(int bookId, String status);
    List<LendBookInfo> findByUserIdAndStatus(String userId, String status);
}
