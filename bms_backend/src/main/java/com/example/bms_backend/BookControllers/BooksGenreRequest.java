package com.example.bms_backend.BookControllers;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BooksGenreRequest {
    @JsonProperty("genre_list")
    private List<String> genre;
    public BooksGenreRequest(List<String> genreList){
        this.genre = genreList;
    }
    public List<String> getGenreList(){
        return genre;
    }
}
