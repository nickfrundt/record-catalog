package com.nickfrundt.record_catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nickfrundt.record_catalog.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
