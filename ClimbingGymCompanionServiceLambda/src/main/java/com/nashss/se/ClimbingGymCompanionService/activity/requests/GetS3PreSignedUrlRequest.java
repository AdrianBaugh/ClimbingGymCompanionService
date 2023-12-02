package com.nashss.se.ClimbingGymCompanionService.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetS3PreSignedUrlRequest.Builder.class)
public class GetS3PreSignedUrlRequest {
    private final String imageKey;

    private GetS3PreSignedUrlRequest(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageKey() {
        return imageKey;
    }

    @Override
    public String toString() {
        return "GetS3PreSignedUrlRequest{" +
                "imageKey='" + imageKey + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String imageKey;

        public Builder withImageKey(String imageKey) {
            this.imageKey = imageKey;
            return this;
        }

        public GetS3PreSignedUrlRequest build() {
            return new GetS3PreSignedUrlRequest(imageKey);
        }
    }
}
