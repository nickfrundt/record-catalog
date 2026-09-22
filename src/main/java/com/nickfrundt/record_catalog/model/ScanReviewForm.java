package com.nickfrundt.record_catalog.model;

import java.util.ArrayList;
import java.util.List;

public class ScanReviewForm {

    private List<VinylRecord> records = new ArrayList<>();

    public ScanReviewForm() {
    }

    public List<VinylRecord> getRecords() {
        return records;
    }

    public void setRecords(List<VinylRecord> records) {
        this.records = records;
    }
}
