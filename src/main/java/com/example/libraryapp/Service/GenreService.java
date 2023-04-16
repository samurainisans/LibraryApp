package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Genre;
import com.example.libraryapp.Models.Publisher;
import com.example.libraryapp.Repos.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    // Implement CRUD methods using BookRepository

    @Autowired
    private GenreRepository genreRepository;

    // Create
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // Read
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).get();
    }

    // Update
    public Genre updateGenre(Genre genre) {
        if (genre.getId() == null || !genreRepository.existsById(genre.getId())) {
            throw new IllegalArgumentException("Genre ID is required and must exist");
        }
        return genreRepository.save(genre);
    }

    // Delete
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }


    public Genre findGenreById(Long genreId) {
        return genreRepository.findById(genreId).get();
    }
}