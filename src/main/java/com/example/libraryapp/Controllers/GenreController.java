package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Genre;
import com.example.libraryapp.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public String showGenres(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genres/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "addGenre";
    }

    @PostMapping("/genres")
    public String addGenre(@ModelAttribute Genre genre) {
        genreService.createGenre(genre);
        return "redirect:/genres";
    }

    @PostMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}
