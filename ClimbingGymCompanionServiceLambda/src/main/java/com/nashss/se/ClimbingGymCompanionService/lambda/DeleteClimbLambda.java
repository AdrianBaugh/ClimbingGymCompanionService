package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.DeleteClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.DeleteClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteClimbLambda
        extends LambdaActivityRunner<DeleteClimbRequest, DeleteClimbResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteClimbRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteClimbRequest> input, Context context) {
        log.info("handleRequest from Cancel Reservation LAMBDA");
        return super.runActivity(
            () -> {
                DeleteClimbRequest unauthenticatedRequest = input.fromPath(path ->
                        DeleteClimbRequest.builder()
                                .withClimbId(path.get("climbId"))
                                .build());

                return input.fromUserClaims(claims ->
                        DeleteClimbRequest.builder()
                                .withClimbId(unauthenticatedRequest.getClimbId())
                                .withUserId(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteClimbActivity().handleRequest(request)
        );
    }
}
