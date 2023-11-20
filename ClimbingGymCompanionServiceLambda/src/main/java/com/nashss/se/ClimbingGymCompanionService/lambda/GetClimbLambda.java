package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetClimbLambda
        extends LambdaActivityRunner<GetClimbRequest, GetClimbResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetClimbRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetClimbRequest> input, Context context) {
        return super.runActivity(
            () -> {
                GetClimbRequest unauthenticatedRequest = input.fromPath(path ->
                        GetClimbRequest.builder()
                                .withClimbId(path.get("climbId"))
                                .build());
                return input.fromUserClaims(claims ->
                        GetClimbRequest.builder()
                                .withClimbId(unauthenticatedRequest.getClimbId())
                                .withUserId(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetClimbActivity().handleRequest(request)
        );
    }
}
