package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Genre;
import com.example.libraryapp.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
