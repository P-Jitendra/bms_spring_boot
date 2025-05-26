package com.example.bms_backend.BookRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookInfo, Integer> {
    List<BookInfo> findByTitleAndAuthorAndPublications(String Title, String Author, String Publications);
    List<BookInfo> findById(int bookId);
    List<BookInfo> findByTitleAndAuthor(String title, String author);
    List<BookInfo> findByTitleAndPublications(String title, String publication);
    List<BookInfo> findByAuthorAndPublications(String author, String publication);
    List<BookInfo> findByAuthor(String author);
    List<BookInfo> findByPublications(String publication);
    List<BookInfo> findByTitle(String title);
    @Query(value = "Select * from books_info Limit :limit", nativeQuery = true)
    List<BookInfo> findTopNBooks(@Param("limit") int limit);
    //OR
    //default List<BookInfo> findTopNBooks(int limit){
    //     return findAll(PageRequest.of(0, limit)).getContent();
    //}
    List<BookInfo> findByGenre(String genre);
}
