package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.enums.ArchivedStatus;
import com.nashss.se.ClimbingGymCompanionService.exceptions.ArchivedStatusNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.exceptions.RouteNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsConstants;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;
import com.nashss.se.ClimbingGymCompanionService.utils.Base64Utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class RouteDao {
    public static final String IMAGE_BUCKET_NAME = "climbing.gym.compainion.images";
    private final DynamoDBMapper dynamoDbMapper;
    private final S3Client s3;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a ReservationDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the pets table
     * @param s3
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public RouteDao(DynamoDBMapper dynamoDbMapper, S3Client s3, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.s3 = s3;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     *
     * @param isArchived is the archived status to return
     * @return list of nonArchived routes
     */
    public List<Route> getAllActiveRoutes(String isArchived) {
        log.info("Entered RouteDao getAllActiveRoutes() ");

        if (isArchived == null || !isArchived.equals(ArchivedStatus.FALSE.name())) {
            throw new ArchivedStatusNotFoundException("Archived status: " + isArchived +
                    " does not match criteria of TRUE or FALSE");
        }

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":archivedValue", new AttributeValue().withS(isArchived));

        DynamoDBQueryExpression<Route> queryExpression = new DynamoDBQueryExpression<Route>()
                .withIndexName("RoutesByArchivedIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("isArchived = :archivedValue")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDbMapper.query(Route.class, queryExpression);
    }

    /**
     * Retrieves a Route by its IDs.
     *
     * @param routeId The ID of the route to retrieve.
     * @return The Route object if found, or throws exception if not found.
     */
    public Route getRouteById(String routeId) {
        Route route = dynamoDbMapper.load(Route.class, routeId);
        if (route == null) {
            metricsPublisher.addCount(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT, 1);
            throw new RouteNotFoundException("Route not found for routeId" + routeId);
        }
        metricsPublisher.addCount(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT, 0);
        return route;
    }

    /**
     * Saves a new route to the database.
     *
     *  @param newRoute The route to save.
     *  @return The Route object that was saved.
     */
    public Route saveRoute(Route newRoute) {
        this.dynamoDbMapper.save(newRoute);
        return newRoute;
    }

    /**
     *
     * @param filePath
     * @param key
     */
    public void saveToS3(Path filePath, String key) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("ATTEMPTING TO SAVE TO S3");
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(IMAGE_BUCKET_NAME)
                .key(key)
                .build();

        this.s3.putObject(request, filePath );
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("YAY SAVED TO S3");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     *
     * @param key
     * @return
     */
    public String getFromS3(String key)  {
        try {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("ATTEMPTING TO GET FROM S3");
            System.out.format("Downloading %s from S3 bucket %s...\n", key, IMAGE_BUCKET_NAME);
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(IMAGE_BUCKET_NAME)
                    .key(key)
                    .build();


            ResponseInputStream<GetObjectResponse> s3objectResponse = s3.getObject(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(s3objectResponse));

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("YAY I GOT IT FROM S3");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            return Base64Utils.convertBufferedReaderToBase64(reader);
        } catch (AwsServiceException | SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

}
