package com.nickfrundt.record_catalog;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nickfrundt.record_catalog.model.RecordCollection;
import com.nickfrundt.record_catalog.model.User;
import com.nickfrundt.record_catalog.repository.RecordCollectionRepository;
import com.nickfrundt.record_catalog.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecordCollectionRepository recordCollectionRepository;
    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RecordCollectionRepository recordCollectionRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.recordCollectionRepository = recordCollectionRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            User user,
            Model model) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute(
                    "error",
                    "That username is already taken."
            );

            return "register";
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute(
                    "error",
                    "That email is already registered."
            );

            return "register";
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);

        RecordCollection defaultCollection = new RecordCollection();

        defaultCollection.setName("My Collection");
        defaultCollection.setDescription("My main record collection.");
        defaultCollection.setUser(user);

        recordCollectionRepository.save(defaultCollection);

        return "redirect:/login?registered";
    }
}