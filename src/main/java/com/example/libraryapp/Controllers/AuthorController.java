package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Author;
import com.example.libraryapp.Service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String showAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @PostMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

    @GetMapping("/authors/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/authors")
    public String addAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "addAuthor";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }
}
