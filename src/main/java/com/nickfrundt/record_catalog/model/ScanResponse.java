package com.nickfrundt.record_catalog.model;

import java.util.List;

public class ScanResponse {
    
    private List<ScannedRecord> records;

    public ScanResponse() {
    }

    public List<ScannedRecord> getRecords() {
        return records;
    }

    public void setRecords(List<ScannedRecord> records) {
        this.records = records;
    }
}
