package com.example.bms_backend.BookService;

import com.example.bms_backend.BookControllers.BookDTO;
import com.example.bms_backend.BookRepository.BookInfo;
import com.example.bms_backend.BookRepository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Transactional
    public void addNewBooks(List<BookDTO> booksList){
        List<BookInfo> newBooksList = new ArrayList<>();
        for(BookDTO bookInfo : booksList){
            List<BookInfo> existingRecList = bookRepository.findByTitleAndAuthorAndPublications(bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublications());
            if(existingRecList.isEmpty()){
                newBooksList.add(new BookInfo(bookInfo.getBookId(), bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublications(), bookInfo.getGenre()));
            }
        }
        bookRepository.saveAll(newBooksList);
    }
    public List<BookInfo> getAllBooks(){
        return bookRepository.findAll();
    }
    @Transactional
    public List<BookInfo> getBookById(int bookId){
        return bookRepository.findById(bookId);
    }
    @Transactional
    public List<BookInfo> getBookByTitleAndAuthor(String title, String author){
        return bookRepository.findByTitleAndAuthor(title, author);
    }
    @Transactional
    public List<BookInfo> getBookByTitleAndPublication(String title, String publication){
        return bookRepository.findByTitleAndPublications(title, publication);
    }
    @Transactional
    public List<BookInfo> getBookByAuthorAndPublication(String author, String publication){
        return bookRepository.findByAuthorAndPublications(author, publication);
    }
    @Transactional
    public List<BookInfo> getBookByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }
    @Transactional
    public List<BookInfo> getBookByPublication(String publication){
        return bookRepository.findByPublications(publication);
    }
    @Transactional
    public List<BookInfo> getBookByTitle(String title){
        return bookRepository.findByTitle(title);
    }
    @Transactional
    public List<BookInfo> getTopNBooks(int n){
        return bookRepository.findTopNBooks(n);
    }
    @Transactional
    public void updateBooks(BookInfo bookInfo){
        List<BookInfo> bookInfoList = bookRepository.findById(bookInfo.getBookId());
        if(bookInfoList.size()!=1){
            throw new BookDoesNotExistException("Book ID doesn't exist!");
        }
        else{
            bookRepository.save(bookInfo);
        }
    }
    @Transactional
    public List<BookInfo> getBooksByGenre(List<String> genreList){
        List<BookInfo> finalBooksList = new ArrayList<>();
        for(String genre:genreList){
            finalBooksList.addAll(bookRepository.findByGenre(genre));
        }
        return finalBooksList;
    }
    @Transactional
    public List<BookInfo> getBookInfoFromBookIds(List<Integer> bookIdList){
        List<BookInfo> bookInfoList = new ArrayList<>();
        for(Integer bookId : bookIdList){
            bookInfoList.addAll(bookRepository.findById((int)bookId));
        }
        return bookInfoList;
    }
}

class BookDoesNotExistException extends RuntimeException{
    public BookDoesNotExistException(String message){
        super(message);
    }
}