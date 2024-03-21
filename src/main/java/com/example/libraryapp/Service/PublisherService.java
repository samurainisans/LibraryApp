package com.example.libraryapp.Service;

import com.example.libraryapp.Models.Publisher;
import com.example.libraryapp.Repos.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public Publisher updatePublisher(Publisher publisher) {
        if (publisher.getId() == null || !publisherRepository.existsById(publisher.getId())) {
            throw new IllegalArgumentException("Publisher ID is required and must exist");
        }
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    public Publisher findPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId).orElse(null);
    }
}
