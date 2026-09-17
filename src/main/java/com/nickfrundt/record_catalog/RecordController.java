package com.nickfrundt.record_catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nickfrundt.record_catalog.model.VinylRecord;
import com.nickfrundt.record_catalog.repository.VinylRecordRepository;

@Controller
public class RecordController {

    private final VinylRecordRepository repository;

    public RecordController(VinylRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/records")
    public String showRecords(Model model) {
        model.addAttribute("records", repository.findAll());
        return "records";
    }

    @GetMapping("/records/new")
    public String showAddRecordForm(Model model) {
        model.addAttribute("vinylRecord", new VinylRecord());
        return "add-record";
    }

    @PostMapping("/records")
    public String addRecord(VinylRecord vinylRecord) {
        repository.save(vinylRecord);
        return "redirect:/records";
    }
}