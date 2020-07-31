package com.redhat.emergency.response.incident.finder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.core.json.JsonArray;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/incidents")
@RegisterRestClient(configKey="incidents")
public interface IncidentServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byname/{name}")
    JsonArray incidentsByName(@PathParam("name") String name);

}
