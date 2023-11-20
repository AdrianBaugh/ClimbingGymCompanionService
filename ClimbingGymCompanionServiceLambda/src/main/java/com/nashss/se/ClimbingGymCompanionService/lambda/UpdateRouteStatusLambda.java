package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateRouteStatusRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateRouteStatusResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateRouteStatusLambda
        extends LambdaActivityRunner<UpdateRouteStatusRequest, UpdateRouteStatusResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateRouteStatusRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateRouteStatusRequest> input, Context context) {
        log.info("Entered HandleRequest from update route Lambda");
        return super.runActivity(
            () -> {
                UpdateRouteStatusRequest unAuthenticatedRequest = input.fromBody(UpdateRouteStatusRequest.class);
                return input.fromPath(path ->
                        UpdateRouteStatusRequest.builder()
                                .withRouteId(path.get("routeId"))
                                .withRouteStatus(unAuthenticatedRequest.getRouteStatus())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateRouteActivity().handleRequest(request)
        );
    }
}


