package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateRouteResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateRouteLambda
        extends LambdaActivityRunner<CreateRouteRequest, CreateRouteResult>
        implements RequestHandler<LambdaRequest<CreateRouteRequest>, LambdaResponse> {
    /**
     * CreateRouteLambda handle request method.
     * @param input The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return
     */
    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateRouteRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromBody(CreateRouteRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreateRouteActivity().handleRequest(request)
        );
    }
}
