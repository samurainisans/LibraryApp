package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Genre;
import com.example.libraryapp.Repos.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).get();
    }

    public Genre updateGenre(Genre genre) {
        if (genre.getId() == null || !genreRepository.existsById(genre.getId())) {
            throw new IllegalArgumentException("Genre ID is required and must exist");
        }
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    public Genre findGenreById(Long genreId) {
        return genreRepository.findById(genreId).get();
    }
}