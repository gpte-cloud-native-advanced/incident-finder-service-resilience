package com.redhat.emergency.response.incident.finder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MissionService {

    @Inject
    @RestClient
    MissionServiceClient client;

    @Retry(maxRetries = 2, maxDuration = 2500, abortOn = {TimeoutException.class})
    @Timeout(500)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio=0.75, delay = 5000)
    @Fallback(fallbackMethod= "fallback")
    public JsonObject missionByIncidentId(String incidentId) {
        return client.missionByIncident(incidentId);
    }

    public JsonObject fallback(String incidentId) {
        return null;
    }

}
