package com.nashss.se.ClimbingGymCompanionService.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RouteModel {
    private String routeId;
    private String routeStatus;
    private String isArchived;
    private String location;
    private String color;
    private String type;
    private String difficulty;
    private LocalDate dateCreated;
    private Integer rating;
    private String imageName;
    private String imageType;
    private String routeImageBase64;
    private List<String> notesList;

    /**
     *
     * @param routeId
     * @param routeStatus
     * @param isArchived
     * @param location
     * @param color
     * @param type
     * @param difficulty
     * @param dateCreated
     * @param rating
     * @param imageName
     * @param imageType
     * @param routeImageBase64
     * @param notesList
     */
    public RouteModel(String routeId, String routeStatus, String isArchived, String location,
                      String color, String type, String difficulty, LocalDate dateCreated, Integer rating, String imageName, String imageType, String routeImageBase64, List<String> notesList) {
        this.routeId = routeId;
        this.routeStatus = routeStatus;
        this.isArchived = isArchived;
        this.location = location;
        this.color = color;
        this.type = type;
        this.difficulty = difficulty;
        this.dateCreated = dateCreated;
        this.rating = rating;
        this.imageName = imageName;
        this.imageType = imageType;
        this.routeImageBase64 = routeImageBase64;
        this.notesList = notesList;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public String getIsArchived() {
        return isArchived;
    }

    public String getLocation() {
        return location;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Integer getRating() {
        return rating;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public String getRouteImageBase64() {
        return routeImageBase64;
    }

    public List<String> getNotesList() {
        return notesList;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        RouteModel that = (RouteModel) other;
        return Objects.equals(routeId, that.routeId) && Objects.equals(routeStatus, that.routeStatus) && Objects.equals(isArchived, that.isArchived) && Objects.equals(location, that.location) && Objects.equals(color, that.color) && Objects.equals(type, that.type) && Objects.equals(difficulty, that.difficulty) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(rating, that.rating) && Objects.equals(imageName, that.imageName) && Objects.equals(imageType, that.imageType) && Objects.equals(routeImageBase64, that.routeImageBase64) && Objects.equals(notesList, that.notesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, routeStatus, isArchived, location, color, type, difficulty, dateCreated, rating, imageName, imageType, routeImageBase64, notesList);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String routeId;
        private String routeStatus;
        private String isArchived;
        private String location;
        private String color;
        private String type;
        private String difficulty;
        private LocalDate dateCreated;
        private Integer rating;
        private String imageName;
        private String imageType;
        private String routeImageBase64;
        private List<String> notesList;

        public Builder withRouteId(String routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder withRouteStatus(String routeStatus) {
            this.routeStatus = routeStatus;
            return this;
        }

        public Builder withIsArchived(String isArchived) {
            this.isArchived = isArchived;
            return this;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder withDateCreated(LocalDate dateCreated) {
            this.dateCreated = dateCreated ;
            return this;
        }

        public Builder withRating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public Builder withImageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public Builder withImageType(String imageType) {
            this.imageType = imageType;
            return this;
        }

        public Builder withRouteImageBase64(String routeImageBase64) {
            this.routeImageBase64 = routeImageBase64;
            return this;
        }

        public Builder withNotesList(List<String> notesLit) {
            this.notesList = notesLit;
            return this;
        }

        public RouteModel build() {
            return new RouteModel(routeId, routeStatus, isArchived, location, color, type,
                    difficulty, dateCreated, rating, imageName, imageType, routeImageBase64, notesList);
        }
    }
}
