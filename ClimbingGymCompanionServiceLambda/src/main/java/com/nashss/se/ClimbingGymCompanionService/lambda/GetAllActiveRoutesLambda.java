package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllActiveRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllActiveRoutesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllActiveRoutesLambda
        extends LambdaActivityRunner<GetAllActiveRoutesRequest, GetAllActiveRoutesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllActiveRoutesRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllActiveRoutesRequest> input, Context context) {
        log.info("Entered handleRequest from GetAllActiveRoutesLambda");
        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetAllActiveRoutesRequest.builder()
                                .withIsArchived(query.get("isArchived"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllActiveRoutesActivity().handleRequest(request)
        );
    }
}
