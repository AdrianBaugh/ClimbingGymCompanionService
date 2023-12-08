package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.UpdateClimbRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.UpdateClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateClimbLambda
        extends LambdaActivityRunner<UpdateClimbRequest, UpdateClimbResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateClimbRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateClimbRequest> input, Context context) {
        log.info("Entered HandleRequest from update climb Lambda");
        return super.runActivity(
            () -> {
                UpdateClimbRequest unAuthenticatedRequest = input.fromBody(UpdateClimbRequest.class);
                UpdateClimbRequest authenticatedRequest = input.fromUserClaims(claims -> UpdateClimbRequest.builder()
                        .withUserId(claims.get("email"))
                        .withUserName(claims.get("name"))
                        .build());
                return input.fromPath(path ->
                UpdateClimbRequest.builder()
                        .withClimbId(path.get("climbId"))
                        .withUserId(authenticatedRequest.getUserId())
                        .withUserName(authenticatedRequest.getUserName())
                        .withType(unAuthenticatedRequest.getType())
                        .withClimbStatus(unAuthenticatedRequest.getClimbStatus())
                        .withThumbsUp(unAuthenticatedRequest.getThumbsUp())
                        .withPublicBeta(unAuthenticatedRequest.getPublicBeta())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideUpdateClimbActivity().handleRequest(request)
        );
    }
}
