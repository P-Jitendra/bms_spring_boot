package com.example.bms_backend.BookRepository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "books_info")
public class BookInfo {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "BookId is required!")
    @Column(name = "book_id")
    private int bookId;
    @NotNull(message = "Title is required!")
    private String title;
    @NotNull(message = "Author is required!")
    private String author;
    @NotNull(message = "Publications is required!")
    private String publications;
    @NotNull(message = "Genre is required!")
    private String genre;

    public BookInfo(){}
    public BookInfo(int bookId, String title, String author, String publications, String genre){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publications = publications;
        this.genre = genre;
    }

    public int getBookId(){
        return bookId;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublications(){
        return publications;
    }
    public String getGenre(){
        return genre;
    }
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public void setAuthor(String newAuthor){
        this.author = newAuthor;
    }
    public void setPublications(String newPublications){
        this.publications = newPublications;
    }
    public void setGenre(String newGenre){
        this.genre = newGenre;
    }
}
