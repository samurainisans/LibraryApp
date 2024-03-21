package com.example.libraryapp.Config;

import com.example.libraryapp.Models.Role;
import com.example.libraryapp.Repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        jdbcTemplate.execute("ALTER TABLE book MODIFY image LONGBLOB;");
    }
}
