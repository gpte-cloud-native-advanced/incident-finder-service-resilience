package com.redhat.emergency.response.incident.finder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MissionService {

    @Inject
    @RestClient
    MissionServiceClient client;

    public JsonObject missionByIncidentId(String incidentId) {
        return client.missionByIncident(incidentId);
    }

}
