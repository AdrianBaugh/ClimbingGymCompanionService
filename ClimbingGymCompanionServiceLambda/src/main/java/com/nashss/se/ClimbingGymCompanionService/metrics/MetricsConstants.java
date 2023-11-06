package com.nashss.se.ClimbingGymCompanionService.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETCLIMB_PLAYLISTNOTFOUND_COUNT =
            "GetClimb.ClimbNotFoundException.Count";
    public static final String GETROUTE_ROUTENOTFOUND_COUNT =
            "GetRoute.RouteNotFoundException.Count";
    public static final String UPDATECLIMB_INVALIDATTRIBUTEVALUE_COUNT =
        "UpdateClimb.InvalidAttributeValueException.Count";
    public static final String UPDATEROUTE_INVALIDATTRIBUTECHANGE_COUNT =
        "UpdateRoute.InvalidAttributeChangeException.Count";
    public static final String CREATECLIMB_INVALIDATTRIBUTEVALUE_COUNT =
            "CreateClimb.InvalidAttributeValueException.Count";
    public static final String CREATEROUTE_INVALIDATTRIBUTECHANGE_COUNT =
            "CreateRoute.InvalidAttributeChangeException.Count";
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "ClimbingGymCompanionService";
    public static final String NAMESPACE_NAME = "Capstone/ClimbingGymCompanionService";
}
