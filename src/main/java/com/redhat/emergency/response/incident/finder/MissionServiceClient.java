package com.redhat.emergency.response.incident.finder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/missions")
@RegisterRestClient(configKey="missions")
public interface MissionServiceClient {

    @GET
    @Path("/incident/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject missionByIncident(@PathParam("id") String incidentId);
}
