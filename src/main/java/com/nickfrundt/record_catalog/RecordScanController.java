package com.nickfrundt.record_catalog;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nickfrundt.record_catalog.model.ScanReviewForm;
import com.nickfrundt.record_catalog.model.ScannedRecord;
import com.nickfrundt.record_catalog.model.VinylRecord;
import com.nickfrundt.record_catalog.repository.VinylRecordRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecordScanController {

    private final RecordVisionService visionService;
    private final VinylRecordRepository repository;
    private final MusicBrainzService musicBrainzService;

    public RecordScanController(RecordVisionService visionService, VinylRecordRepository repository, MusicBrainzService musicBrainzService) {
        this.visionService = visionService;
        this.repository = repository;
        this.musicBrainzService = musicBrainzService;
    }

    @GetMapping("/scan")
    public String showScanPage() {
        return "scan-records";
    }

    @PostMapping("/scan")
    public String scanRecords(
            @RequestParam("image") MultipartFile image,
            Model model,
            HttpSession session) {

        try {

            List<ScannedRecord> records
                    = visionService.identifyRecords(image);

            model.addAttribute("records", records);

            session.setAttribute("scanResults", records);

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    "Something went wrong: " + e.getMessage()
            );
        }

        return "scan-results";
    }

    @PostMapping("/scan/confirm")
    public String confirmScan(
            @RequestParam(value = "selectedRecords", required = false) List<Integer> selectedRecords,
            HttpSession session,
            Model model) {

        List<ScannedRecord> scanResults
                = (List<ScannedRecord>) session.getAttribute("scanResults");

        if (scanResults == null || selectedRecords == null) {
            return "redirect:/records";
        }

        ScanReviewForm reviewForm = new ScanReviewForm();

        for (Integer index : selectedRecords) {

            ScannedRecord scanned = scanResults.get(index);

            VinylRecord record = new VinylRecord();

            record.setArtist(scanned.getArtist());
            record.setTitle(scanned.getAlbum());
            record.setOwned(true);

            reviewForm.getRecords().add(record);

            Map<?, ?> result =
                musicBrainzService.searchAlbum(
                    scanned.getArtist(),
                    scanned.getAlbum()
                );

            System.out.println(result);
        }

        model.addAttribute("reviewForm", reviewForm);

        return "scan-review";
    }

    @PostMapping("/scan/save")
    public String saveReviewedRecords(
            ScanReviewForm reviewForm,
            HttpSession session) {
        for (VinylRecord record : reviewForm.getRecords()) {
            repository.save(record);
        }
        session.removeAttribute("scanResults");
        return "redirect:/records"; 
    }
}
