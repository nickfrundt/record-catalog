package com.nickfrundt.record_catalog;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nickfrundt.record_catalog.model.RecordCollection;
import com.nickfrundt.record_catalog.model.User;
import com.nickfrundt.record_catalog.repository.RecordCollectionRepository;
import com.nickfrundt.record_catalog.repository.UserRepository;

@Controller
public class CollectionController {

    private final RecordCollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public CollectionController(
            RecordCollectionRepository collectionRepository,
            UserRepository userRepository) {

        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/collections")
    public String showCollections(
            Principal principal,
            Model model) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        model.addAttribute(
                "collections",
                collectionRepository.findByUser(user)
        );

        model.addAttribute(
                "newCollection",
                new RecordCollection()
        );

        return "collections";
    }

    @PostMapping("/collections")
    public String createCollection(
            RecordCollection newCollection,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        newCollection.setUser(user);

        collectionRepository.save(newCollection);

        return "redirect:/collections";
    }
}