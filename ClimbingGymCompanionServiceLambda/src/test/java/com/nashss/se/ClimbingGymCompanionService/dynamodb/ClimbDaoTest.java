package com.nashss.se.ClimbingGymCompanionService.dynamodb;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.pojos.Climb;
import com.nashss.se.ClimbingGymCompanionService.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClimbDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedQueryList<Climb> paginatedQueryList;
    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Climb>> queryCaptor;
    private ClimbDao climbDao;
    @BeforeEach
    public void setup() {
        openMocks(this);
        this.climbDao = new ClimbDao(dynamoDBMapper, metricsPublisher);
        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    public void getAllUsersClimbs_activeUser_returnsClimbs() {
        // GIVEN
        String userID = "userId";

        when(dynamoDBMapper.query(eq(Climb.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Climb> results = climbDao.getAllUsersClimbs(userID);

        // THEN
        assertNotNull(results);
        assertEquals(paginatedQueryList, results);
        verify(dynamoDBMapper, times(1)).query(eq(Climb.class), queryCaptor.capture());
    }

    @Test
    public void getClimbById_withPartitionKey_returnsClimb() {
        // GIVEN
        String climbId = "climbId";
        String userID = "userId";

        when(dynamoDBMapper.load(Climb.class, userID, climbId)).thenReturn(new Climb());

        // WHEN
        Climb climb = climbDao.getClimbById(climbId, userID);

        // THEN
        assertNotNull(climb);
        verify(dynamoDBMapper).load(Climb.class, userID, climbId);
    }

    @Test
    public void saveClimb_callsMapperWithClimb() {
        // GIVEN
        Climb climb = new Climb();

        // WHEN
        Climb result = climbDao.saveClimb(climb);

        // THEN
        verify(dynamoDBMapper).save(climb);
        assertEquals(climb, result);
    }
}
