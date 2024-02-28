package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.UserInfo;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UserInfoDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates a UserInfoDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the userInfo table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public UserInfoDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Retrieves the userInfo by its ID.
     *
     * @param userId The ID of the userInfo to retrieve.
     * @return The userInfo object if found, or throws exception if not found.
     */
    public UserInfo getUserInfoById(String userId) {
        UserInfo userInfo = dynamoDbMapper.load(UserInfo.class, userId);
        if (userInfo == null) {
            throw new RuntimeException("UserInfo not found for userId" + userId);
        }
        return userInfo;
    }

    /**
     * Saves userInfo to the database.
     *
     *  @param userInfo The userInfo to save.
     *  @return The userInfo object that was saved.
     */
    public UserInfo saveUserInfo(UserInfo userInfo) {
        this.dynamoDbMapper.save(userInfo);
        return userInfo;
    }

}
