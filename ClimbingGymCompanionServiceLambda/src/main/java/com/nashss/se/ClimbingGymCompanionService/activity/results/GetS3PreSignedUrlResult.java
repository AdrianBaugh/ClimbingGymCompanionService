package com.nashss.se.ClimbingGymCompanionService.activity.results;

public class GetS3PreSignedUrlResult {

    private final String s3PreSignedUrl;

    private GetS3PreSignedUrlResult(String imageKey) {
        this.s3PreSignedUrl = imageKey;
    }

    public String getS3PreSignedUrl() {
        return s3PreSignedUrl;
    }

    @Override
    public String toString() {
        return "GetS3PreSignedUrlResult{" +
                "s3PreSignedUrl='" + s3PreSignedUrl + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String s3PreSignedUrl;

        public Builder withS3PreSignedUrl(String GetS3PreSignedUrlResult) {
            this.s3PreSignedUrl = GetS3PreSignedUrlResult;
            return this;
        }

        public GetS3PreSignedUrlResult build() {
            return new GetS3PreSignedUrlResult(s3PreSignedUrl);
        }
    }
}
