package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Publisher;
import com.example.libraryapp.Service.PublisherService;
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
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/publishers")
    public String showPublishers(Model model) {
        List<Publisher> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        return "publishers";
    }

    @GetMapping("/publishers/add")
    public String showAddPublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "addPublisher";
    }

    @PostMapping("/publishers")
    public String addPublisher(@Valid @ModelAttribute("publisher") Publisher publisher, BindingResult result) {
        if (result.hasErrors()) {
            return "addPublisher";
        }
        publisherService.createPublisher(publisher);
        return "redirect:/publishers";
    }

    @PostMapping("/publishers/delete/{id}")
    public String deletePublisher(@PathVariable("id") Long id) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }
}
