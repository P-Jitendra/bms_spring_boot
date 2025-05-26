package com.example.bms_backend.CartControllers;

import com.example.bms_backend.CartRepository.LendBookInfo;
import com.example.bms_backend.CartService.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService){
        this.cartService = cartService;
    }
    @PostMapping("/add-to-cart-books")
    public ResponseEntity<CartReturnMessage> addBooksToCart(@RequestBody @Valid AddToCartRequest cartData){
        try{
            cartService.addNewBooks(cartData.getCartList());
            return ResponseEntity.status(HttpStatus.CREATED).body(new CartReturnMessage("success", "Add To Cart Request Created successfully"));
        }
        catch (RuntimeException exception){
            if(exception.getMessage().equals("Add to Cart data is already existing for same input!")){
                return ResponseEntity.ok().body(new CartReturnMessage("success", exception.getMessage()));
            }
            return ResponseEntity.badRequest().body(new CartReturnMessage("failure", exception.getMessage()));
        }
    }
    @PutMapping("/borrow-books")
    public ResponseEntity<CartReturnMessage> updateBorrowedBooks(@RequestBody @Valid BorrowBookRequest borrowData){
        try{
            cartService.updateBorrowedBooks(borrowData.getBorrowList());
            return ResponseEntity.status(HttpStatus.CREATED).body(new CartReturnMessage("success", "Lend Request Created successfully!"));
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while borrowing books");
            return ResponseEntity.badRequest().body(new CartReturnMessage("failure", exception.getMessage()));
        }
    }
    @DeleteMapping("/delete-add-to-cart-books")
    public ResponseEntity<Void> deleteCartBooks(@RequestBody @Valid DeleteBookRequest deleteData){
        try{
            cartService.deleteCartBooks(deleteData.getDeleteList());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception : " + exception.getMessage() + " while deleting the data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/return-books")
    public ResponseEntity<CartReturnMessage> updateReturnedBooks(@RequestBody @Valid ReturnBookRequest returnData) {
        try {
            cartService.updateReturnedBooks(returnData.getReturnData());
            return ResponseEntity.accepted().body(new CartReturnMessage("success", "Returned book status updated successfully!"));
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnMessage("failure", exception.getMessage()));
        }
    }
    @GetMapping("/add-to-cart-books")
    public ResponseEntity<CartReturnGetMessage> getCartBooks(@RequestParam String userId){
        try{
            List<LendBookInfo> cartBookIdList = cartService.getCartBooks(userId);
            return ResponseEntity.ok().body(new CartReturnGetMessage("success", cartBookIdList));
        }
        catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/curr-borrowed-books")
    public ResponseEntity<CartReturnGetMessage> getCurrBorrowedBooks(@RequestParam String userId){
        try{
            List<LendBookInfo> booksList = cartService.getCurrBorrowedBooks(userId);
            return ResponseEntity.ok().body(new CartReturnGetMessage("success", booksList));
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching curr borrowed books");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/past-borrowed-books")
    public ResponseEntity<CartReturnGetMessage> getPastBorrowedBooks(@RequestParam String userId){
        try{
            List<LendBookInfo> booksList = cartService.getPastBorrowedBooks(userId);
            return ResponseEntity.ok().body(new CartReturnGetMessage("success", booksList));
        }
        catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @DeleteMapping("/delete-past-borrowed-books")
    public ResponseEntity<Void> deletePastBorrowedBooks(@RequestBody @Valid DeleteBookRequest deleteBooksData){
        try{
            cartService.deletePastBorrowedBooks(deleteBooksData.getDeleteList());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while deleting past borrowed books!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/curr-borrowed-users")
    public ResponseEntity<CartReturnGetMessage> getCurrBorrowedUsers(@RequestParam int bookId){
        try{
            List<LendBookInfo> usersList = cartService.getCurrBorrowedUsers(bookId);
            return ResponseEntity.ok().body(new CartReturnGetMessage("success", usersList));
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching curr borrowed users!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/past-borrowed-users")
    public ResponseEntity<CartReturnGetMessage> getPastBorrowedUsers(@RequestParam int bookId){
        try{
            List<LendBookInfo> usersList = cartService.getPastBorrowedUsers(bookId);
            return ResponseEntity.ok().body(new CartReturnGetMessage("success", usersList));
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching past borrowed users");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartReturnGetMessage("failure", new ArrayList<>()));
        }
    }
}
