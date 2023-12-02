package com.nashss.se.ClimbingGymCompanionService.dependency;

import com.nashss.se.ClimbingGymCompanionService.activity.CreateClimbActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.CreateRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.DeleteClimbActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetAllActiveRoutesActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetClimbActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetPresignedS3ImageActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetRouteActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetS3PreSignedUrlActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.GetUsersClimbHistoryActivity;
import com.nashss.se.ClimbingGymCompanionService.activity.UpdateClimbActivity;
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
     * @return GetUsersClimbHistoryActivity
     */
    GetUsersClimbHistoryActivity provideGetAllUsersClimbsActivity();

    /**
     *
     * @return GetClimbActivity
     */
    GetClimbActivity provideGetClimbActivity();

    /**
     *
     * @return CreateRouteActivity
     */
    CreateRouteActivity provideCreateRouteActivity();

    /**
     *
     * @return CreateClimbActivity
     */
    CreateClimbActivity provideCreateClimbActivity();

    /**
     *
     * @return UpdateRouteStatusActivity
     */
    UpdateRouteStatusActivity provideUpdateRouteActivity();

    /**
     *
     * @return UpdateClimbActivity
     */
    UpdateClimbActivity provideUpdateClimbActivity();

    /**
     *
     * @return DeleteClimbActivity
     */
    DeleteClimbActivity provideDeleteClimbActivity();

    /**
     *
     * @return GetS3PreSignedUrlActivity
     */
    GetS3PreSignedUrlActivity provideGetS3PreSignedUrlActivity();

    /**
     *
     * @return GetPresignedS3ImageActivity
     */
    GetPresignedS3ImageActivity provideGetPresignedS3ImageActivity();
}

