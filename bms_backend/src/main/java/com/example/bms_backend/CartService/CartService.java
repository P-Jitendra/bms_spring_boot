package com.example.bms_backend.CartService;

import com.example.bms_backend.CartControllers.BorrowBook;
import com.example.bms_backend.CartRepository.CartRepository;
import com.example.bms_backend.CartRepository.LendBookInfo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }
    @Transactional
    public void addNewBooks(List<BorrowBook> booksList){
        for(BorrowBook borrowBook : booksList) {
            List<LendBookInfo> cartBooksList = cartRepository.findByUserIdAndBookIdAndStatus(borrowBook.getUserId(), borrowBook.getBookId(), "pending");
            if (!cartBooksList.isEmpty()) {
                throw new BookAlreadyExistsInCartException("Add to Cart data is already existing for same input!");
            }
            cartRepository.save(new LendBookInfo(borrowBook.getBookId(), borrowBook.getUserId(), "pending", new Date(), null));
        }
    }
    @Transactional
    public void updateBorrowedBooks(List<BorrowBook> booksList){
        for(BorrowBook borrowBook : booksList){
            List<LendBookInfo> cartBooksList = cartRepository.findByUserIdAndBookIdAndStatusAndBorrowedTimestamp(borrowBook.getUserId(), borrowBook.getBookId(), "borrowed", null);
            if(!cartBooksList.isEmpty()){
                throw new BookAlreadyExistsInCartException("Borrow Data is already existing for same input!");
            }
            cartRepository.save(new LendBookInfo(borrowBook.getBookId(), borrowBook.getUserId(), "borrowed", new Date(), null));
            List<LendBookInfo> deleteBooksList = cartRepository.findByBookIdAndStatus(borrowBook.getBookId(), "pending");
            if(!deleteBooksList.isEmpty()) {
                cartRepository.delete(deleteBooksList.get(0));
            }
        }
    }
    @Transactional
    public void deleteCartBooks(List<BorrowBook> booksList){
        List<LendBookInfo> finalDeleteList = new ArrayList<>();
        for(BorrowBook borrowBook : booksList){
            List<LendBookInfo> deleteList = cartRepository.findByUserIdAndBookIdAndStatus(borrowBook.getUserId(), borrowBook.getBookId(), "pending");
            finalDeleteList.addAll(deleteList);
        }
        cartRepository.deleteAllInBatch(finalDeleteList);
    }
    @Transactional
    public void updateReturnedBooks(List<BorrowBook> booksList){
        for(BorrowBook borrowBook : booksList){
            List<LendBookInfo> res = cartRepository.findByUserIdAndBookIdAndStatus(borrowBook.getUserId(), borrowBook.getBookId(), "borrowed");
            if(res.isEmpty()){
                throw new RecordDoesNotExistException("Borrow data not found given book_id and user_id!");
            }
            LendBookInfo bookInfo = res.get(0);
            bookInfo.setStatus("returned");
            bookInfo.setReturnedTimestamp(new Date());
            cartRepository.save(bookInfo);
        }
    }
    @Transactional
    public List<LendBookInfo> getCartBooks(String userId){
        List<LendBookInfo> booksList = cartRepository.findByUserIdAndStatus(userId, "pending");
        return booksList.stream().filter(ele -> ele.getBookId()>0).toList();
    }
    @Transactional
    public List<LendBookInfo> getCurrBorrowedBooks(String userId){
        List<LendBookInfo> booksList = cartRepository.findByUserIdAndStatus(userId, "borrowed");
        return booksList.stream().filter(ele -> ele.getBookId()>0).toList();
    }
    @Transactional
    public List<LendBookInfo> getPastBorrowedBooks(String userId){
        List<LendBookInfo> booksList = cartRepository.findByUserIdAndStatus(userId, "returned");
        return booksList.stream().filter(ele -> ele.getBookId()>0).toList();
    }
    @Transactional
    public void deletePastBorrowedBooks(List<BorrowBook> booksList) {
        List<LendBookInfo> finalDeleteList = new ArrayList<>();
        for (BorrowBook borrowBook : booksList) {
            System.out.println("Printing borrowBookUserId: " + borrowBook.getUserId() + " bookId: " + borrowBook.getBookId());
            List<LendBookInfo> deleteList = cartRepository.findByUserIdAndBookIdAndStatus(borrowBook.getUserId(), borrowBook.getBookId(), "returned");
            finalDeleteList.addAll(deleteList);
        }
        cartRepository.deleteAllInBatch(finalDeleteList);
    }
    @Transactional
    public List<LendBookInfo> getCurrBorrowedUsers(int bookId){
        return cartRepository.findByBookIdAndStatus(bookId, "borrowed");
    }
    @Transactional
    public List<LendBookInfo> getPastBorrowedUsers(int bookId){
        return cartRepository.findByBookIdAndStatus(bookId, "returned");
    }
}
class BookAlreadyExistsInCartException extends RuntimeException{
    public BookAlreadyExistsInCartException(String message){
        super(message);
    }
}
class RecordDoesNotExistException extends RuntimeException{
    public RecordDoesNotExistException(String message){
        super(message);
    }
}