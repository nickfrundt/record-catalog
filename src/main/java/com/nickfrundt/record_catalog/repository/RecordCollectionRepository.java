package com.nickfrundt.record_catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nickfrundt.record_catalog.model.RecordCollection;
import com.nickfrundt.record_catalog.model.User;

public interface RecordCollectionRepository
        extends JpaRepository<RecordCollection, Long> {

    List<RecordCollection> findByUser(User user);
}