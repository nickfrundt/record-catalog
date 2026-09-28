package com.nickfrundt.record_catalog.model;

public class RecordMetadata {

    private Integer releaseYear;
    private int matchScore;
    private boolean verified;
    private String musicBrainzId;

    public RecordMetadata() {
    }

    public RecordMetadata(Integer releaseYear, int matchScore, boolean verified) {
        this(releaseYear, matchScore, verified, null);
    }

    public RecordMetadata(Integer releaseYear, int matchScore, boolean verified,
                          String musicBrainzId) {
        this.releaseYear = releaseYear;
        this.matchScore = matchScore;
        this.verified = verified;
        this.musicBrainzId = musicBrainzId;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getMusicBrainzId() {
        return musicBrainzId;
    }

    public void setMusicBrainzId(String musicBrainzId) {
        this.musicBrainzId = musicBrainzId;
    }
}
