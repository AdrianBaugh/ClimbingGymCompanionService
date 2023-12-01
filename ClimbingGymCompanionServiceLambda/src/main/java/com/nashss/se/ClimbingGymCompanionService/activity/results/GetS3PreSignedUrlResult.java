package com.nashss.se.ClimbingGymCompanionService.activity.results;

public class GetS3PreSignedUrlResult {

    private final String S3PreSignedUrl;

    private GetS3PreSignedUrlResult(String imageKey) {
        this.S3PreSignedUrl = imageKey;
    }

    public String getS3PreSignedUrl() {
        return S3PreSignedUrl;
    }

    @Override
    public String toString() {
        return "GetS3PreSignedUrlResult{" +
                "S3PreSignedUrl='" + S3PreSignedUrl + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String S3PreSignedUrl;

        public Builder withS3PreSignedUrl(String GetS3PreSignedUrlResult) {
            this.S3PreSignedUrl = GetS3PreSignedUrlResult;
            return this;
        }

        public GetS3PreSignedUrlResult build() {
            return new GetS3PreSignedUrlResult(S3PreSignedUrl);
        }
    }
}
