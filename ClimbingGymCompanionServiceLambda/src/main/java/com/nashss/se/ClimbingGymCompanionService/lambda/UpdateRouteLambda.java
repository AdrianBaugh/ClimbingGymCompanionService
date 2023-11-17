package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateRouteResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateRouteLambda
        extends LambdaActivityRunner<UpdateRouteRequest, UpdateRouteResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateRouteRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateRouteRequest> input, Context context) {
        log.info("Entered HandleRequest from update route Lambda");
        return super.runActivity(
            () -> {
                UpdateRouteRequest unAuthenticatedRequest = input.fromBody(UpdateRouteRequest.class);
                return input.fromPath(path ->
                        UpdateRouteRequest.builder()
                                .withRouteId(path.get("routeId"))
                                .withRouteStatus(unAuthenticatedRequest.getRouteStatus())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateRouteActivity().handleRequest(request)
        );
    }
}


