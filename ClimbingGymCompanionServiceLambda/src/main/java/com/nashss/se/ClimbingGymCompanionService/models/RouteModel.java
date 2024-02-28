package com.nashss.se.ClimbingGymCompanionService.models;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

public class RouteModel {
    private String routeId;
    private String routeStatus;
    private String isArchived;
    private String location;
    private String color;
    private String type;
    private String difficulty;
    private ZonedDateTime dateCreated;
    private Integer rating;
    private String imageName;
    private String imageKey;
    private Map<String, String> betaMap;

    /**
     *
     * @param routeId  route model metadaata
     * @param routeStatus route model metadaata
     * @param isArchived route model metadaata
     * @param location route model metadaata
     * @param color route model metadaata
     * @param type route model metadaata
     * @param difficulty route model metadaata
     * @param dateCreated route model metadaata
     * @param rating route model metadaata
     * @param imageName route model metadaata
     * @param imageKey route model metadaata
     * @param betaMap route model metadaata
     */
    private RouteModel(String routeId, String routeStatus, String isArchived, String location,
                      String color, String type, String difficulty, ZonedDateTime dateCreated,
                      Integer rating, String imageName, String imageKey,
                      Map<String, String> betaMap) {
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
        this.imageKey = imageKey;
        this.betaMap = betaMap;
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

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Integer getRating() {
        return rating;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageKey() {
        return imageKey;
    }

    public Map<String, String> getBetaMap() {
        return betaMap;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        RouteModel that = (RouteModel) other;
        return Objects.equals(routeId, that.routeId) && Objects.equals(routeStatus, that.routeStatus) &&
                Objects.equals(isArchived, that.isArchived) && Objects.equals(location, that.location) &&
                Objects.equals(color, that.color) && Objects.equals(type, that.type) &&
                Objects.equals(difficulty, that.difficulty) && Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(rating, that.rating) && Objects.equals(imageName, that.imageName) &&
                Objects.equals(imageKey, that.imageKey) &&
                Objects.equals(betaMap, that.betaMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, routeStatus, isArchived, location, color, type,
                difficulty, dateCreated, rating, imageName, imageKey,
                betaMap);
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
        private ZonedDateTime dateCreated;
        private Integer rating;
        private String imageName;
        private String imageKey;
        private Map<String, String> betaMap;

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

        public Builder withDateCreated(ZonedDateTime dateCreated) {
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

        public Builder withImageKey(String imageKey) {
            this.imageKey = imageKey;
            return this;
        }

        public Builder withBetaMap(Map<String, String> betaMap) {
            this.betaMap = betaMap;
            return this;
        }

        public RouteModel build() {
            return new RouteModel(routeId, routeStatus, isArchived, location, color, type,
                    difficulty, dateCreated, rating, imageName, imageKey,
                    betaMap);
        }
    }
}
