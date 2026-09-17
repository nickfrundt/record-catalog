package com.nickfrundt.record_catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VinylRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private int runtime;
    private double priceWhenPurchased;
    private int releaseYear;
    private String condition;
    private boolean owned;

    public VinylRecord() {
    }

    public VinylRecord(String title, String artist, int runtime,
                       double priceWhenPurchased, int releaseYear,
                       String condition, boolean owned) {
        this.title = title;
        this.artist = artist;
        this.runtime = runtime;
        this.priceWhenPurchased = priceWhenPurchased;
        this.releaseYear = releaseYear;
        this.condition = condition;
        this.owned = owned;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getPriceWhenPurchased() {
        return priceWhenPurchased;
    }

    public void setPriceWhenPurchased(double priceWhenPurchased) {
        this.priceWhenPurchased = priceWhenPurchased;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    @Override
    public String toString() {
        return "VinylRecord{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseYear=" + releaseYear +
             '}';
    }
}