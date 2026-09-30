package com.nickfrundt.record_catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nickfrundt.record_catalog.model.RecordCollection;
import com.nickfrundt.record_catalog.model.VinylRecord;

public interface VinylRecordRepository extends JpaRepository<VinylRecord, Long> {
    List<VinylRecord> findByCollection(RecordCollection collection);
}