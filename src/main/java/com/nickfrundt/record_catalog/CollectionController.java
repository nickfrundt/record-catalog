package com.nickfrundt.record_catalog;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nickfrundt.record_catalog.model.RecordCollection;
import com.nickfrundt.record_catalog.model.User;
import com.nickfrundt.record_catalog.repository.RecordCollectionRepository;
import com.nickfrundt.record_catalog.repository.UserRepository;
import com.nickfrundt.record_catalog.repository.VinylRecordRepository;


@Controller
public class CollectionController {

    private final RecordCollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final VinylRecordRepository recordRepository;
    public CollectionController(
            RecordCollectionRepository collectionRepository,
            UserRepository userRepository,
            VinylRecordRepository recordRepository) {

        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
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

    @GetMapping("/collections/{id}")
    public String showCollection(
            @PathVariable Long id,
            Principal principal,
            Model model) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        RecordCollection collection
                = collectionRepository.findById(id).orElse(null);

        if (collection == null) {
            return "redirect:/collections";
        }

        if (!collection.getUser().getId().equals(user.getId())) {
            return "redirect:/collections";
        }

        model.addAttribute("collection", collection);

        model.addAttribute(
                "records",
                recordRepository.findByCollection(collection)
        );

        return "collection-detail";
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