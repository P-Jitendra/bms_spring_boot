package com.example.bms_backend.BookControllers;

import com.example.bms_backend.BookRepository.BookInfo;
import com.example.bms_backend.BookService.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @PostMapping("/new-books-data")
    public ResponseEntity<BooksReturnMessage> addNewBooks(@RequestBody @Valid NewBooksRequest bookInfoData){
        try{
            List<BookDTO> booksList = bookInfoData.getBooksData();
            bookService.addNewBooks(booksList);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BooksReturnMessage("success", "New Books are added successfully!"));
        }
        catch (RuntimeException exception){
            return ResponseEntity.badRequest().body(new BooksReturnMessage("failure", exception.getMessage()));
        }
    }
    @GetMapping("/all-booksinfo")
    public ResponseEntity<BookReturnGetMessage> getAllBooks(){
        try{
            List<BookInfo> allBooksList = bookService.getAllBooks();
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", allBooksList));
        }
        catch(RuntimeException exception){
            return ResponseEntity.internalServerError().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-book-id/")
    public ResponseEntity<BookReturnGetMessage> getBookById(@PathVariable int bookId){
        try{
            List<BookInfo> bookInfoList = bookService.getBookById(bookId);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", bookInfoList));
        }
        catch(RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-title-author")
    public ResponseEntity<BookReturnGetMessage> getBookByTitleAndAuthor(@RequestParam String title, @RequestParam String author){
        try{
            List<BookInfo> bookInfoList = bookService.getBookByTitleAndAuthor(title, author);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", bookInfoList));
        }
        catch(RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-title-publication")
    public ResponseEntity<BookReturnGetMessage> getBookByTitleAndPublication(@RequestParam String title, @RequestParam String publication){
        try{
            List<BookInfo> bookInfoList = bookService.getBookByTitleAndPublication(title, publication);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", bookInfoList));
        }
        catch (RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-author-publication")
    public ResponseEntity<BookReturnGetMessage> getBookByAuthorAndPublication(@RequestParam String author, String publication){
        try{
            List<BookInfo> booksList = bookService.getBookByAuthorAndPublication(author, publication);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch(RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-author")
    public ResponseEntity<BookReturnGetMessage> getBookByAuthor(@RequestParam String author){
        try{
            List<BookInfo> booksList = bookService.getBookByAuthor(author);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch(RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-publication")
    public ResponseEntity<BookReturnGetMessage> getBookByPublication(@RequestParam String publication){
        try{
            List<BookInfo> booksList = bookService.getBookByPublication(publication);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch (RuntimeException exception){
             return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/from-title")
    public ResponseEntity<BookReturnGetMessage> getBookByTitle(@RequestParam String title){
        try{
            List<BookInfo> booksList = bookService.getBookByTitle(title);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch (RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching title: " + title);
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @GetMapping("/top-n-books/{n}")
    public ResponseEntity<BookReturnGetMessage> getTopNBooks(@PathVariable int n){
        try{
            List<BookInfo> booksList = bookService.getTopNBooks(n);
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch(RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching top " + n + " books");
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @PutMapping("/update-book-data")
    public ResponseEntity<BooksReturnMessage> updateBooks(@RequestBody @Valid UpdateBooksRequest newBookData){
        try{
            bookService.updateBooks(newBookData.getBookInfo());
            return ResponseEntity.accepted().body(new BooksReturnMessage("success", "Updated the given data successfully."));
        }
        catch(RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BooksReturnMessage("failure", exception.getMessage()));
        }
    }
    @PostMapping("/from-genre")
    public ResponseEntity<BookReturnGetMessage> getBooksByGenre(@RequestBody @Valid BooksGenreRequest genreRequestData){
        try{
            System.out.println("Received genreList: " + genreRequestData.getGenreList());
            List<BookInfo> booksList = bookService.getBooksByGenre(genreRequestData.getGenreList());
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch(RuntimeException exception){
            System.out.println("Encountered an exception: " + exception.getMessage() + " while fetching books related to genre: " + genreRequestData.getGenreList());
            return ResponseEntity.internalServerError().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
    @PostMapping("/get-book-info")
    public ResponseEntity<BookReturnGetMessage> getBookInfoFromBookIds(@RequestBody @Valid BookIdRequest bookIdData){
        try{
            List<BookInfo> booksList = bookService.getBookInfoFromBookIds(bookIdData.getBookIdList());
            return ResponseEntity.ok().body(new BookReturnGetMessage("success", booksList));
        }
        catch (RuntimeException exception){
            return ResponseEntity.badRequest().body(new BookReturnGetMessage("failure", new ArrayList<>()));
        }
    }
}
