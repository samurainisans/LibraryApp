package com.example.libraryapp.Controllers;

import com.example.libraryapp.Models.Role;
import com.example.libraryapp.Models.User;
import com.example.libraryapp.Repos.RoleRepository;
import com.example.libraryapp.Repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    public void registrationPageTest() throws Exception {
        mockMvc.perform(get("/registration")).andExpect(status().isOk()).andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void addUserTest() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_password");

        mockMvc.perform(post("/registration").param("username", "testuser").param("password", "testpassword")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));

        User expectedUser = new User();
        expectedUser.setActive(true);
        expectedUser.setUsername("testuser");
        expectedUser.setPassword("encoded_password");
        verify(userRepository, times(1)).save(expectedUser);
    }
}
