package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllUsersClimbsRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllUsersClimbsResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllUsersClimbsLambda
        extends LambdaActivityRunner<GetAllUsersClimbsRequest, GetAllUsersClimbsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllUsersClimbsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllUsersClimbsRequest> input, Context context) {
        log.info("Entered handleRequest from GetAllUsersClimbsLambda");
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                    GetAllUsersClimbsRequest.builder()
                            .withUserId(claims.get("email"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllUsersClimbsActivity().handleRequest(request)
        );
    }
}
