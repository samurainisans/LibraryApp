package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Author;
import com.example.libraryapp.Repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author updateAuthor(Author author) {
        if (author.getId() == null || !authorRepository.existsById(author.getId())) {
            throw new IllegalArgumentException("Author ID is required and must exist");
        }
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author findAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }
}