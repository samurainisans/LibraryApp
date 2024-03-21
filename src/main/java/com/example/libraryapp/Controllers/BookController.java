package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Author;
import com.example.libraryapp.Models.Book;
import com.example.libraryapp.Models.Genre;
import com.example.libraryapp.Models.Publisher;
import com.example.libraryapp.Repos.AuthorRepository;
import com.example.libraryapp.Repos.BookRepository;
import com.example.libraryapp.Repos.GenreRepository;
import com.example.libraryapp.Repos.PublisherRepository;
import com.example.libraryapp.Service.AuthorService;
import com.example.libraryapp.Service.BookService;
import com.example.libraryapp.Service.GenreService;
import com.example.libraryapp.Service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/books/add")
    public String addBook(@RequestParam(name = "title") String title, @RequestParam(name = "authorId") Long authorId, @RequestParam(name = "genreId") Long genreId, @RequestParam(name = "publisherId") Long publisherId, @RequestParam(name = "year") Integer year, @RequestParam(name = "isbn") String isbn, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "pageCount") Integer pageCount, @RequestParam(name = "image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        try {
            Book book = new Book();
            book.setName(title);
            Author author = authorService.findAuthorById(authorId);
            book.setAuthor(author);
            Genre genre = genreService.findGenreById(genreId);
            book.setGenre(genre);
            Publisher publisher = publisherService.findPublisherById(publisherId);
            book.setPublisher(publisher);
            book.setPublishYear(year);
            book.setIsbn(isbn);
            book.setDescr(description);
            book.setPageCount(pageCount);

            // Обработка файла изображения
            if (!imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                book.setImage(imageBytes);
            } else {
                throw new Exception("Image file must not be empty.");
            }

            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("message", "Book added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/books";
    }
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Publisher> publishers = publisherRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("publishers", publishers);
        return "addBook";
    }

    @GetMapping("/books/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showEditBookForm(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Author> authors = authorService.getAllAuthors();
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        model.addAttribute("genres", genres);
        return "editBook";
    }

    @PostMapping("/books/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateBook(@PathVariable("id") long id, @Valid @ModelAttribute("book") Book book, BindingResult bindingResult,  @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "editBook";
        }

        // Обработка файла изображения
        if (!imageFile.isEmpty()) {
            try {
                byte[] imageBytes = imageFile.getBytes();
                book.setImage(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        book.setId(id);
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @PostMapping("books/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

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