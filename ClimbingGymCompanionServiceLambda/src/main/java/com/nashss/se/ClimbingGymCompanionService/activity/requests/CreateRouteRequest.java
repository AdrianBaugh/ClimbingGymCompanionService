package com.nashss.se.ClimbingGymCompanionService.activity.requests;


public class CreateRouteRequest {

        private final String location;
        private final String color;
        private final String routeStatus;
        private final String type;
        private final String difficulty;
        private final String pictureKey;

    private CreateRouteRequest(String location, String color, String routeStatus, String type, String difficulty, String pictureKey) {
        this.location = location;
        this.color = color;
        this.routeStatus = routeStatus;
        this.type = type;
        this.difficulty = difficulty;
        this.pictureKey = pictureKey;
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

    public String getPictureKey() {
        return pictureKey;
    }

    @Override
    public String toString() {
        return "CreateRouteRequest{" +
                "location='" + location + '\'' +
                ", color='" + color + '\'' +
                ", routeStatus='" + routeStatus + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", pictureKey='" + pictureKey + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAllActiveRoutesRequest.Builder builder() {
        return new GetAllActiveRoutesRequest.Builder();
    }

    public static class Builder {
        private String location;
        private String color;
        private String routeStatus;
        private String type;
        private String difficulty;
        private String pictureKey;

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

        public Builder withPictureKey(String pictureKey) {
            this.pictureKey = pictureKey;
            return this;
        }

        public CreateRouteRequest build() {
            return new CreateRouteRequest(location, color, routeStatus, type, difficulty, pictureKey);
        }
    }
}
