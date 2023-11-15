package com.nashss.se.ClimbingGymCompanionService.dependency;

import com.nashss.se.ClimbingGymCompanionService.activity.CreateRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetAllActiveRoutesActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.UpdateRouteActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component for providing dependency injection in the ClimbingGymCompanionService.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {
    /*

     */
    GetAllActiveRoutesActivity provideGetAllActiveRoutesActivity();

    /*

     */
    GetRouteActivity provideGetRouteActivity();

    /*

     */
    CreateRouteActivity provideCreateRouteActivity();

    /*

     */
    UpdateRouteActivity provideUpdateRouteActivity();
}
