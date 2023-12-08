package com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos;

import com.nashss.se.ClimbingGymCompanionService.converters.ZonedDateTimeConverter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Represents a climb in the climbs table.
 */
@DynamoDBTable(tableName = "climbs")
public class Climb {
    private static final String GSI_INDEX_USER_ID = "ClimbsByUserIdIndex";
    private static final String GSI_INDEX_ROUTE_ID = "ClimbsByRouteIdIndex";
    private String climbId;
    private String userId;
    private String routeId;
    private String type;
    private String climbStatus;
    private ZonedDateTime dateTimeClimbed;
    private Boolean thumbsUp;
    private String publicBeta;
    @DynamoDBRangeKey(attributeName = "climbId")
    public String getClimbId() {
        return climbId;
    }

    public void setClimbId(String climbId) {
        this.climbId = climbId;
    }
    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_INDEX_USER_ID)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "routeId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_INDEX_ROUTE_ID)
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "climbStatus")
    public String getClimbStatus() {
        return climbStatus;
    }

    public void setClimbStatus(String climbStatus) {
        this.climbStatus = climbStatus;
    }
    @DynamoDBAttribute(attributeName = "dateTimeClimbed")
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    public ZonedDateTime getDateTimeClimbed() {
        return dateTimeClimbed;
    }

    public void setDateTimeClimbed(ZonedDateTime dateTimeClimbed) {
        this.dateTimeClimbed = dateTimeClimbed;
    }
    @DynamoDBAttribute(attributeName = "thumbsUp")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean isThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }
    @DynamoDBAttribute(attributeName = "publicBeta")
    public String getPublicBeta() {
        return publicBeta;
    }

    public void setPublicBeta(String publicBeta) {
        this.publicBeta = publicBeta;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Climb climb = (Climb) other;
        return Objects.equals(climbId, climb.climbId) &&
                Objects.equals(userId, climb.userId) &&
                Objects.equals(routeId, climb.routeId) &&
                Objects.equals(type, climb.type) &&
                Objects.equals(climbStatus, climb.climbStatus) &&
                Objects.equals(dateTimeClimbed, climb.dateTimeClimbed) &&
                Objects.equals(thumbsUp, climb.thumbsUp) &&
                Objects.equals(publicBeta, climb.publicBeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(climbId, userId, routeId, type, climbStatus, dateTimeClimbed, thumbsUp, publicBeta);
    }

    @Override
    public String toString() {
        return "Climb{" +
                "climbId='" + climbId + '\'' +
                ", userId='" + userId + '\'' +
                ", routeId='" + routeId + '\'' +
                ", type='" + type + '\'' +
                ", climbStatus='" + climbStatus + '\'' +
                ", dateTimeClimbed=" + dateTimeClimbed +
                ", thumbsUp=" + thumbsUp +
                ", publicBeta='" + publicBeta + '\'' +
                '}';
    }
}
