package com.nickfrundt.record_catalog.model;

import java.util.ArrayList;

/**
 * Catalog Object class that creates a catalog to store vinyl records in
 * 
 * @author Nicholas Frundt
 */

public class Catalog {

    private ArrayList<VinylRecord> records;
    private String username;


    public Catalog(String username) {
        this.username = username;
        records = new ArrayList<VinylRecord>();
    }

    public void addRecord(VinylRecord record) {
        records.add(record);
    }

}
