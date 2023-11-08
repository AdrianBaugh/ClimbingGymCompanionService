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
        //from path and query
        log.info("Entered handleRequest from GetAllActiveRoutesLambda");

        //delete after its working
        System.out.println("************Entered LAMBDA getAllActiveRoutes()************** ");

        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetAllActiveRoutesRequest.builder()
                                .withExcludedStatus(query.get("routeStatus"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllActiveRoutesActivity().handleRequest(request)
        );
    }
}
