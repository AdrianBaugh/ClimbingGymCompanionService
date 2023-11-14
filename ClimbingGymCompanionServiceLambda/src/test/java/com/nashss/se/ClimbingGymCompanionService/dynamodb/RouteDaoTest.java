package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Route;
import com.nashss.se.ClimbingGymCompanionService.exceptions.ArchivedStatusNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.exceptions.RouteNotFoundException;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsConstants;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RouteDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Route> paginatedQueryList;
    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Route>> queryCaptor;

    private RouteDao routeDao;
    @BeforeEach
    public void setup() {
        initMocks(this);
        this.routeDao = new RouteDao(dynamoDBMapper, metricsPublisher);
        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }
    @Test
    public void getAllActiveRoutes_isNotArchived_returnsRoutes() {
        // GIVEN
        String isArchived = "FALSE";

        when(dynamoDBMapper.query(eq(Route.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Route> results = routeDao.getAllActiveRoutes(isArchived);

        // THEN
        assertNotNull(results);
        assertEquals(paginatedQueryList, results);
        verify(dynamoDBMapper, times(1)).query(eq(Route.class), queryCaptor.capture());
    }

    @Test
    public void getAllActiveRoutes_isArchived_throwsException() {
        // GIVEN
        String isArchived = "TRUE";

        when(dynamoDBMapper.query(eq(Route.class), any(DynamoDBQueryExpression.class))).thenReturn(null);

        // WHEN

        // THEN
        assertThrows(ArchivedStatusNotFoundException.class, () -> routeDao.getAllActiveRoutes(isArchived));
    }

    @Test
    public void getRouteById_withPartitionKey_returnsRoute() {
        // GIVEN
        String routeId = "routeId";
        when(dynamoDBMapper.load(Route.class, routeId)).thenReturn(new Route());

        // WHEN
        Route route = routeDao.getRouteById(routeId);

        // THEN
        assertNotNull(route);
        verify(dynamoDBMapper).load(Route.class, routeId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void getRouteById_RouteNotFound_throwsException() {
        // GIVEN
        String nonexistentRouteId = "NotReal";

        when(dynamoDBMapper.load(Route.class, nonexistentRouteId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(RouteNotFoundException.class, () -> routeDao.getRouteById(nonexistentRouteId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETROUTE_ROUTENOTFOUND_COUNT), anyDouble());
    }
}
