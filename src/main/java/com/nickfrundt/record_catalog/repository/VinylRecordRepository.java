package com.nickfrundt.record_catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nickfrundt.record_catalog.model.VinylRecord;

public interface VinylRecordRepository extends JpaRepository<VinylRecord, Long> {

}