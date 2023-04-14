package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Book;
import com.example.libraryapp.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String showBooks(Model model, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "filter", required = false) String filter) {
        List<Book> books;

        if (search != null && !search.isEmpty()) {
            books = bookService.searchBooks(search);
        } else if (filter != null && !filter.isEmpty()) {
            books = bookService.filterBooks(filter);
        } else {
            books = getAllBooks().getBody();
        }

        List<String> encodedImages = new ArrayList<>();

        for (Book book : books) {
            String encodedImage = Base64.getEncoder().encodeToString(book.getImage());
            encodedImages.add(encodedImage);
        }

        model.addAttribute("books", books);
        model.addAttribute("encodedImages", encodedImages);
        return "books";
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
