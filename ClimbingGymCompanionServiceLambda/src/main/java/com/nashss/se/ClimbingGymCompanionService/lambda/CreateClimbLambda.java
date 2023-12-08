package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.CreateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.CreateClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateClimbLambda
        extends LambdaActivityRunner<CreateClimbRequest, CreateClimbResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateClimbRequest>, LambdaResponse> {

    /**
     * CreateClimbLambda handle request method.
     * @param input The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateClimbRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateClimbRequest unauthenticatedRequest = input.fromBody(CreateClimbRequest.class);
                return input.fromUserClaims(claims ->
                        CreateClimbRequest.builder()
                                .withUserId(claims.get("email"))
                                .withUserName(claims.get("name"))
                                .withRouteId(unauthenticatedRequest.getRouteId())
                                .withType(unauthenticatedRequest.getType())
                                .withClimbStatus(unauthenticatedRequest.getClimbStatus())
                                .withThumbsUp(unauthenticatedRequest.getThumbsUp())
                                .withPublicBeta(unauthenticatedRequest.getPublicBeta())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideCreateClimbActivity().handleRequest(request)
        );
    }
}
