package com.example.bms_backend.BookControllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO {

    @JsonProperty("BookId")
    private int bookId;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Author")
    private String author;

    @JsonProperty("Publications")
    private String publications;

    @JsonProperty("Genre")
    private String genre;

    // Getters and Setters
    public int getBookId(){
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublications() {
        return publications;
    }

    public void setPublications(String publications) {
        this.publications = publications;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}