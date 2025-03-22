package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetAllArchivedRoutesRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetAllArchivedRoutesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllArchivedRoutesLambda
        extends LambdaActivityRunner<GetAllArchivedRoutesRequest, GetAllArchivedRoutesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllArchivedRoutesRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllArchivedRoutesRequest> input, Context context) {
        log.info("Entered handleRequest from GetAllArchivedRoutesRequest");
        return super.runActivity(
            () -> input.fromQuery(query ->
                    GetAllArchivedRoutesRequest.builder()
                            .withIsArchived(query.get("isArchived"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllArchivedRoutesActivity().handleRequest(request)
        );
    }
}
