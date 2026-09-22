package com.nickfrundt.record_catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.nickfrundt.record_catalog.model.ScannedRecord;

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
            Model model) {

        try {

            List<ScannedRecord> records =
                    visionService.identifyRecords(image);

            model.addAttribute("records", records);

        } catch (Exception e) {

            model.addAttribute(
                    "result",
                    "Something went wrong: " + e.getMessage()
            );
        }

        return "scan-results";
    }
}