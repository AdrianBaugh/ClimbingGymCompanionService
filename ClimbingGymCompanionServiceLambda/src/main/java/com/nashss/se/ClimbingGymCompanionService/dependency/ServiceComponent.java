package com.nashss.se.ClimbingGymCompanionService.dependency;

import com.nashss.se.ClimbingGymCompanionService.activity.CreateClimbActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.CreateRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetAllActiveRoutesActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.UpdateRouteStatusActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the ClimbingGymCompanionService.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {
    /**
     *
     * @return GetAllActiveRoutesActivity
     */
    GetAllActiveRoutesActivity provideGetAllActiveRoutesActivity();

    /**
     *
     * @return GetRouteActivity
     */
    GetRouteActivity provideGetRouteActivity();

    /**
     *
     * @return CreateRouteActivity
     */
    CreateRouteActivity provideCreateRouteActivity();

    /**
     *
     * @return UpdateRouteStatusActivity
     */
    UpdateRouteStatusActivity provideUpdateRouteActivity();

    /**
     *
     * @return CreateClimbActivity
     */
    CreateClimbActivity provideCreateClimbActivity();
}

