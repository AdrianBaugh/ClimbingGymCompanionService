package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetUsersClimbHistoryRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetUsersClimbHistoryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUsersClimbHistoryLambda
        extends LambdaActivityRunner<GetUsersClimbHistoryRequest, GetUsersClimbHistoryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUsersClimbHistoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUsersClimbHistoryRequest> input, Context context) {
        log.info("Entered handleRequest from GetUsersClimbHistoryLambda");
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                    GetUsersClimbHistoryRequest.builder()
                            .withUserId(claims.get("email"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllUsersClimbsActivity().handleRequest(request)
        );
    }
}
