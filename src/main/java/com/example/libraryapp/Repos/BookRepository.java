package com.example.libraryapp.Repos;

import com.example.libraryapp.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByNameContainingIgnoreCase(String search);

    List<Book> findByAuthorFioContainingIgnoreCase(String search);

    List<Book> findAllByOrderByNameAsc();

    List<Book> findAllByOrderByPublishYearDesc();

    List<Book> findAllByOrderByAuthorFioAsc();

    List<Book> findAllByOrderByGenreNameAsc();

}