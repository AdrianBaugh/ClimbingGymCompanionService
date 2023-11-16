package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetRouteResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetRouteLambda
        extends LambdaActivityRunner<GetRouteRequest, GetRouteResult>
        implements RequestHandler<LambdaRequest<GetRouteRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetRouteRequest> input, Context context) {
        log.info("handleRequest from Get route LAMBDA");
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetRouteRequest.builder()
                            .withRouteId(path.get("routeId"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetRouteActivity().handleRequest(request)
        );
    }
}
