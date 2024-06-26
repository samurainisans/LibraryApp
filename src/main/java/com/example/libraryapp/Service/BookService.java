package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Book;
import com.example.libraryapp.Repos.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> filterBooks(String filter) {
        List<Book> books;
        switch (filter) {
            case "alphabetical":
                books = bookRepository.findAllByOrderByNameAsc();
                break;
            case "publishYear":
                books = bookRepository.findAllByOrderByPublishYearDesc();
                break;
            case "author":
                books = bookRepository.findAllByOrderByAuthorFioAsc();
                break;
            case "genre":
                books = bookRepository.findAllByOrderByGenreNameAsc();
                break;
            default:
                books = getAllBooks();
        }
        return books;
    }

    public List<Book> searchBooks(String search) {
        List<Book> booksByName = bookRepository.findByNameContainingIgnoreCase(search);
        List<Book> booksByAuthor = bookRepository.findByAuthorFioContainingIgnoreCase(search);
        List<Book> result = new ArrayList<>();
        result.addAll(booksByName);

        for (Book book : booksByAuthor) {
            if (!booksByName.contains(book)) {
                result.add(book);
            }
        }
        return result;
    }

    @Transactional
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
    }
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}