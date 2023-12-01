package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateRouteRequest.Builder.class)
public class CreateRouteRequest {
    private final String location;
    private final String color;
    private final String routeStatus;
    private final String type;
    private final String difficulty;
    private final String routeImageBase64;
    private final String imageName;
    private final String imageType;

    public CreateRouteRequest(String location, String color, String routeStatus, String type, String difficulty,
                              String routeImageBase64, String imageName, String imageType) {
        this.location = location;
        this.color = color;
        this.routeStatus = routeStatus;
        this.type = type;
        this.difficulty = difficulty;
        this.routeImageBase64 = routeImageBase64;
        this.imageName = imageName;
        this.imageType = imageType;
    }

    public String getLocation() {
        return location;
    }

    public String getColor() {
        return color;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getRouteImageBase64() {
        return routeImageBase64;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageType() {
        return imageType;
    }

    @Override
    public String toString() {
        return "CreateRouteRequest{" +
                "location='" + location + '\'' +
                ", color='" + color + '\'' +
                ", routeStatus='" + routeStatus + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", routeImageBase64='" + routeImageBase64 + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String location;
        private String color;
        private String routeStatus;
        private String type;
        private String difficulty;
        private String routeImageBase64;
        private String imageName;
        private String imageType;

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Builder withRouteStatus(String routeStatus) {
            this.routeStatus = routeStatus;
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

        public Builder withRouteImageBase64(String routeImageBase64) {
            this.routeImageBase64 = routeImageBase64;
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

        public CreateRouteRequest build() {
            return new CreateRouteRequest(location, color, routeStatus, type, difficulty, routeImageBase64, imageName, imageType);
        }
    }
}
