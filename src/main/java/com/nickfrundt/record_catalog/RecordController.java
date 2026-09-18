package com.nickfrundt.record_catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/records/{id}/edit")
    public String showEditRecordForm(@PathVariable Long id, Model model) {
        VinylRecord vinylRecord = repository.findById(id).orElse(null);
        if (vinylRecord == null) {
            return "redirect:/records";
        }
        model.addAttribute("vinylRecord", vinylRecord);
        return "edit-record";
    }

    @PostMapping("/records")
    public String addRecord(VinylRecord vinylRecord) {
        repository.save(vinylRecord);
        return "redirect:/records";
    }

    @PostMapping("/records/{id}")
    public String updateRecord(@PathVariable Long id, VinylRecord updatedRecord) {
        VinylRecord existingRecord = repository.findById(id).orElse(null);
        if (existingRecord != null) {
            existingRecord.setTitle(updatedRecord.getTitle());
            existingRecord.setArtist(updatedRecord.getArtist());
            existingRecord.setReleaseYear(updatedRecord.getReleaseYear());
            existingRecord.setRuntime(updatedRecord.getRuntime());
            existingRecord.setPriceWhenPurchased(updatedRecord.getPriceWhenPurchased());
            existingRecord.setGenre(updatedRecord.getGenre());
            existingRecord.setOwned(updatedRecord.isOwned());
            repository.save(existingRecord);
        }
        return "redirect:/records";
    }

    @PostMapping("/records/{id}/delete")
    public String deleteRecord(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/records";
    }

    @GetMapping("/ui-playground")
    public String showUiPlayground() {
        return "ui-playground";
    }

}