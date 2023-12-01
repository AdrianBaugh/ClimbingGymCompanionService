package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetS3PreSignedUrlRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetS3PreSignedUrlResult;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import javax.inject.Inject;

public class GetS3PreSignedUrlActivity {
    public static final String IMAGE_BUCKET_NAME = "climbing.gym.companion.images";
    public static final int PRESIGNED_URL_TIMEOUT_DURATION = 7;

    @Inject
    public GetS3PreSignedUrlActivity() {
    }

    /**
     *
     * @param request GetS3PreSignedUrlRequest
     * @return GetS3PreSignedUrlResult
     */
    public GetS3PreSignedUrlResult handleRequest(final GetS3PreSignedUrlRequest request) {
        String imageKey = request.getImageKey();
//        String imageKey = "TEST.jpeg";


        PresignedPutObjectRequest presignedPutObjectRequest;

        try(S3Presigner s3Presigner = S3Presigner.create()) {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(IMAGE_BUCKET_NAME)
                    .key(imageKey)
                    .contentType("image/webp")
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(PRESIGNED_URL_TIMEOUT_DURATION))
                    .putObjectRequest(objectRequest)
                    .build();

            presignedPutObjectRequest = s3Presigner.presignPutObject(presignRequest);
        }
        URL presignedUrl = presignedPutObjectRequest.url();
        String url = presignedUrl.toString();

        return GetS3PreSignedUrlResult.builder()
                .withS3PreSignedUrl(url)
                .build();
    }
}
