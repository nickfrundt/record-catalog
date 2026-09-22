package com.nickfrundt.record_catalog;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nickfrundt.record_catalog.model.ScannedRecord;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecordScanController {

    private final RecordVisionService visionService;

    public RecordScanController(RecordVisionService visionService) {
        this.visionService = visionService;
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
            HttpSession session) {

        List<ScannedRecord> scanResults
                = (List<ScannedRecord>) session.getAttribute("scanResults");

        if (scanResults == null || selectedRecords == null) {
            return "redirect:/records";
        }

        for (Integer index : selectedRecords) {

            ScannedRecord scanned = scanResults.get(index);

            System.out.println(
                    scanned.getArtist() + " - " + scanned.getAlbum()
            );
        }

        return "redirect:/records";
    }
}
