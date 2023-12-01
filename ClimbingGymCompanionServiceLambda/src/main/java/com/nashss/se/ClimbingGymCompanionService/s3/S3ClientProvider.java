package com.nashss.se.ClimbingGymCompanionService.s3;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientProvider {

    public static S3Client getS3Client(Region region) {
        return S3Client.builder()
                .region(region)
                .build();
    }
}
