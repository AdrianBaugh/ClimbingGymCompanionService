package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUserInfoRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUserInfoResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetUserInfoLambda
        extends LambdaActivityRunner<GetUserInfoRequest, GetUserInfoResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserInfoRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserInfoRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                    GetUserInfoRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserInfoActivity().handleRequest(request)
        );
    }
}
