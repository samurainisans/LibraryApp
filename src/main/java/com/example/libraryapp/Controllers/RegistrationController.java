package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Role;
import com.example.libraryapp.Models.User;
import com.example.libraryapp.Repos.RoleRepository;
import com.example.libraryapp.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "Такой пользователь уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(1L).get());
        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/login";
    }
}
