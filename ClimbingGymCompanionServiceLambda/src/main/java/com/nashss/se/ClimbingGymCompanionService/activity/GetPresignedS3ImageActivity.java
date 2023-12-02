package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetS3PreSignedUrlRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetS3PreSignedUrlResult;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;
import javax.inject.Inject;

public class GetPresignedS3ImageActivity {
    public static final String IMAGE_BUCKET_NAME = "climbing.gym.companion.images";
    public static final int PRESIGNED_URL_TIMEOUT_DURATION = 7;

    /**
     * GetPresignedS3ImageActivity constructor.
     */
    @Inject
    public GetPresignedS3ImageActivity() {
    }

    /**
     *
     * @param request GetS3PreSignedUrlRequest
     * @return GetS3PreSignedUrlResult
     */
    public GetS3PreSignedUrlResult handleRequest(final GetS3PreSignedUrlRequest request) {
        String imageKey = request.getImageKey();


        PresignedGetObjectRequest getObjectRequest;

        try (S3Presigner s3Presigner = S3Presigner.create()) {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(IMAGE_BUCKET_NAME)
                    .key(imageKey)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(PRESIGNED_URL_TIMEOUT_DURATION))
                    .getObjectRequest(objectRequest)
                    .build();

            getObjectRequest = s3Presigner.presignGetObject(presignRequest);
        }
        URL presignedUrl = getObjectRequest.url();
        String url = presignedUrl.toString();

        return GetS3PreSignedUrlResult.builder()
                .withS3PreSignedUrl(url)
                .build();
    }
}
