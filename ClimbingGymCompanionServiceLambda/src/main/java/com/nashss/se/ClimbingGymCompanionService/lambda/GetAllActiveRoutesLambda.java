package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllActiveRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllActiveRoutesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllActiveRoutesLambda
        extends LambdaActivityRunner<GetAllActiveRoutesRequest, GetAllActiveRoutesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllActiveRoutesRequest>, LambdaResponse> {


    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllActiveRoutesRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetAllActiveRoutesRequest unauthenticatedRequest = input.fromBody(GetAllActiveRoutesRequest.class);
                    return input.fromUserClaims(claims ->
                            GetAllActiveRoutesRequest.builder()
                                    .withExcludedStatus(unauthenticatedRequest.getExcludedStatus())
                                    .build());

                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllActiveRoutesActivity().handleRequest(request)
        );
    }
}
