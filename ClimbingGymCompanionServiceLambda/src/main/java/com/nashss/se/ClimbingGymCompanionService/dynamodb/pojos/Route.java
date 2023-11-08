package com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos;

import com.nashss.se.ClimbingGymCompanionService.converters.LocalDateConverter;

import java.time.LocalDate;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "routes")
public class Route {
    private String routeId;
    private String routeStatus;
    private Boolean isArchived;
    private String location;
    private String color;
    private String type;
    private String difficulty;
    private LocalDate dateCreated;
    private Integer rating;
    private String pictureKey;
    private static final String GSI_INDEX_NAME = "RoutesByStatusIndex";

    @DynamoDBHashKey(attributeName = "routeId")
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @DynamoDBAttribute(attributeName = "routeStatus")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_INDEX_NAME)
    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }
    @DynamoDBAttribute(attributeName = "isArchived")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "difficulty")
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @DynamoDBAttribute(attributeName = "dateCreated")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute(attributeName = "pictureKey")
    public String getPictureKey() {
        return pictureKey;
    }

    public void setPictureKey(String pictureKey) {
        this.pictureKey = pictureKey;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Route route = (Route) other;
        return Objects.equals(routeId, route.routeId) && Objects.equals(routeStatus, route.routeStatus) &&
                Objects.equals(isArchived, route.isArchived) && Objects.equals(location, route.location) &&
                Objects.equals(color, route.color) && Objects.equals(type, route.type) &&
                Objects.equals(difficulty, route.difficulty) && Objects.equals(dateCreated, route.dateCreated) &&
                Objects.equals(rating, route.rating) && Objects.equals(pictureKey, route.pictureKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, routeStatus, isArchived, location,
                color, type, difficulty, dateCreated, rating, pictureKey);
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId='" + routeId + '\'' +
                ", routeStatus='" + routeStatus + '\'' +
                ", isArchived=" + isArchived +
                ", location='" + location + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", dateCreated=" + dateCreated +
                ", rating=" + rating +
                ", pictureKey='" + pictureKey + '\'' +
                '}';
    }
}
