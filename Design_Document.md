# ClimbingGymCompanionService Design Document

## 1. Problem statement

As a user at my local climbing gym, I want to log my climbs to monitor progress on different routes. I want to write optional notes about my climbs called beta. I want to view my climbing history and status achieved for those routes. Routes will be viewable by all users. Changes or updates, such as difficulty or route color, will be crowd sourced.

## 2. Top Questions to Resolve in Review
1. Best way to add pictures of routes?
2. How to handle bouldering routes which vary in number? maybe not in scope
## 3. Use Cases

1. As a user I want log a new climb, give it a status, rating, and optional notes
2. As a user I want to retrieve my climb history
3. As a user I want to update my climb's status, rating, or notes
4. As a user I want to update a route's details such as difficulty and/or color
5. As a user I want to see available routes and filter by difficulty or type
6. As a User I want to view a single route and it's details (location, difficulty, color)

## 4. Project Scope

### 4.1. In Scope

- Creating, retrieving, updating, and deleting a users climbs
- Updating the routes difficulty and color
- retrieve the gyms current routes

### 4.2. Out of Scope

## 5. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including creating, retrieving, and updating a climb, as well as updating and retrieving the current routes in the gym.

We will use API Gateway and Lambda to create ==XXX== endpoints (`GetClimb`, `CreateClimb`, `UpdateClimb`, `DeleteClimb`, ` DeleteRoute`,  `UpdateRoute`, and `GetRoute`) that will handle the creation, update, and retrieval of climbs and routes to satisfy our requirements.

We will store climb and route data in different DynamoDB tables. 

==ClimbingGymCompanionService== will also provide a web interface for users to manage their climbs. A main page providing a list view of all the available routes where they can click on a route for details or link off to pages to log/update metadata for a new climb or update a route.

## 6. API

### 6.1. Public Models

```
// Route Model
String routeId;
String location;
Enum type;
String difficulty;
String color;
Integer thumbsUpPercent;
```

```
//Climb Model
String climbId;
Enum Status;
LocalDateandTime dateTime;
Boolean rating; (thumbsUp = true)
String notes;
```

### 6.2. Endpoints

#### 6.2.1 *Get single Route Endpoint*
- Accepts `GET` requests to `/routes/:routeId`
- Accepts a route ID and returns a corresponding routeModel
	- If the given route ID is not found throw a `RouteNotFoundException`
#### 6.2.2 *Get All Routes Endpoint*
- Accepts `GET` requests to `/routes/:`
- Returns all routes as a list of routeModels
#### 6.2.3. Create Climb Endpoint*
- Accepts a `POST` request to `/climbs/:climbId`
- Takes the user ID from cognito
- Accepts data to create a new climb with a provided user ID,  status, optional rating, optional notes and returns a corresponding climbModel with a climb ID assigned by the service.
#### 6.2.4. *Get single Climb by user Endpoint*
- Accepts a `GET` request to `/climbs/:climbId`
- Takes the user ID from cognito
- Accepts a user ID and climbID and returns a corresponding climbModel
	- If the given climb ID is not found throw a `ClimbNotFoundException`
#### 6.2.5. _Get All Climbs by user Endpoint_
- Accepts a `GET` request to `/climbs/:`
- Takes the user ID from cognito
-  Returns all the user's climbs as a list of climbModels
#### 6.2.6. _Delete Climb Endpoint_
- Accepts a `DELETE` request to `/climbs/:climbId`
- Takes the user ID from cognito
- Accepts a user ID and climbID and returns a corresponding deleted climbModel
	- If the given climb ID is not found throw a `ClimbNotFoundException`
#### 6.2.7. _Update Climb Endpoint_
- Accepts a `PUT` request to `/climbs/:climbId`
- Takes the user ID from cognito
- Accepts data to update a climb including status, rating, and notes.  Returns the corresponding updated climbModel
	- If the given climb ID is not found throw a `ClimbNotFoundException`
	- throws `UnauthorizedUserException` if attempted to be updated by an unauthorized user.
#### 6.2.8. _Update Route Endpoint_
- Accepts a `PUT` request to `/routes/:routeID`
- Accepts data to update a route including route difficulty and route color. Returns the corresponding updated routeModel
	- If the given route ID is not found throw a `RouteNotFoundException`

#### 6.2.9. _Delete Route Endpoint_
- Accepts a `DELETE` request to `/routes/:routeId`
- Accepts route ID and returns a corresponding deleted climbModel
	- If the given route ID is not found throw a `RouteNotFoundException`

## 7. Tables

### 7.1 `routes`

```
routeId // Partition key, string
location // String
type // String
difficulty // String
color // String
thumbsUpPercent // Interger (number type)
```
- Key Structure: `location:: + color`
	- top rope/lead location = number
	- bouldering location = wall location (slab, island, cave)
### 7.2 `climbs`

```
climbId // Partition key, String
routeId // String
status // String
dateTime // String (converted dateTime)
rating // BOOL (thumbsUp = true)
notes // String
```
- Key Structure: `RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);`

### 7.3 `RoutesByLocationIndex` GSI table

### 7.4 `RoutesByDifficultyIndex` GSI table


# 8. Page storyboard
