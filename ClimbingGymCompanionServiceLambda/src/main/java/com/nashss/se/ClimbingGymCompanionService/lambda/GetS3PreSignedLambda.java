package com.nashss.se.ClimbingGymCompanionService.lambda;

import com.nashss.se.ClimbingGymCompanionService.activity.requests.GetS3PreSignedUrlRequest;
import com.nashss.se.ClimbingGymCompanionService.activity.results.GetS3PreSignedUrlResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetS3PreSignedLambda
        extends LambdaActivityRunner<GetS3PreSignedUrlRequest, GetS3PreSignedUrlResult>
        implements RequestHandler<LambdaRequest<GetS3PreSignedUrlRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetS3PreSignedUrlRequest> input, Context context) {
        log.info("handleRequest from GetS3PreSignedLambda");
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetS3PreSignedUrlRequest.builder()
                            .withImageKey(path.get("imageKey"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetS3PreSignedUrlActivity().handleRequest(request)
        );
    }
}
