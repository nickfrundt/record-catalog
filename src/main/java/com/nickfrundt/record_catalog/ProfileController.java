package com.nickfrundt.record_catalog;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nickfrundt.record_catalog.model.User;
import com.nickfrundt.record_catalog.repository.UserRepository;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String showProfile(
            Principal principal,
            Model model) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }
}