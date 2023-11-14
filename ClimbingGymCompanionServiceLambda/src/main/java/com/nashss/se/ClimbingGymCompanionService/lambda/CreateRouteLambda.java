package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateRouteRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateRouteResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateRouteLambda
        extends LambdaActivityRunner<CreateRouteRequest, CreateRouteResult>
        implements RequestHandler<LambdaRequest<CreateRouteRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateRouteRequest> input, Context context) {
        return super.runActivity(
//                () -> {
//                    CreateRouteRequest unauthenticatedRequest = input.fromBody(CreateRouteRequest.class);
//                    return input.fromUserClaims(claims ->
//                            CreateRouteRequest.builder()
//                                    .withLocation(unauthenticatedRequest.getLocation())
//                                    .withRouteStatus(unauthenticatedRequest.getRouteStatus())
//                                    .withType(unauthenticatedRequest.getType())
//                                    .withDifficulty(unauthenticatedRequest.getDifficulty())
//                                    .withColor(unauthenticatedRequest.getColor())
//                                    .withPictureKey(unauthenticatedRequest.getPictureKey())
//                                    .build());
//                },
                () -> input.fromBody(CreateRouteRequest.class),
                (request, serviceComponent) ->
                        serviceComponent.provideCreateRouteActivity().handleRequest(request)
        );
    }
}
