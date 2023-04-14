package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Book;
import com.example.libraryapp.Repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    // Implement CRUD methods using BookRepository

    @Autowired
    private BookRepository bookRepository;

    // Create
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Read
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
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

    // Update
    public Book updateBook(Book book) {
        if (book.getId() == null || !bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book ID is required and must exist");
        }
        return bookRepository.save(book);
    }

    //    public List<Book> searchBooks(String search) {
//        return bookRepository.findByNameContainingIgnoreCase(search);
//    }
    // Delete
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}