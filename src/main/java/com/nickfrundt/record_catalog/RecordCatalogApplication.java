package com.nickfrundt.record_catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nickfrundt.record_catalog.model.VinylRecord;
import com.nickfrundt.record_catalog.repository.VinylRecordRepository;

@SpringBootApplication
public class RecordCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordCatalogApplication.class, args);
    }

    @Bean
    CommandLineRunner run(VinylRecordRepository repository) {
        return args -> {

            VinylRecord record = new VinylRecord(
                "Rumours",
                "Fleetwood Mac",
                2400,
                25.99,
                1977,
                "Very Good",
                true
            );

            repository.save(record);

            System.out.println(repository.findAll());
        };
    }
}