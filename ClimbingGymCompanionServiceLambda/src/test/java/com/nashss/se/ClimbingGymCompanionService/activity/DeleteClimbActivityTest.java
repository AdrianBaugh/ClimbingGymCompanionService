package com.nashss.se.ClimbingGymCompanionService.activity;

import com.nashss.se.ClimbingGymCompanionService.dynamodb.ClimbDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteClimbActivityTest {
    @InjectMocks
    private DeleteClimbActivity activity;
    @Mock
    private ClimbDao climbDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest() {
    }
}