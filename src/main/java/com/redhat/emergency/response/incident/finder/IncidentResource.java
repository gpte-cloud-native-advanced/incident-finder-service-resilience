package com.redhat.emergency.response.incident.finder;

import java.math.BigDecimal;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class IncidentResource {

    private static final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    @Inject
    IncidentService incidentService;

    @Inject
    MissionService missionService;

    @Inject
    ShelterService shelterService;

    @GET
    @Path("/incidents")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getIncidents(@QueryParam("name") String name) {

        return Uni.createFrom().item(() -> doGetIncidents(name)).map(array -> Response.ok(array.encode()).build());
    }

    private JsonArray doGetIncidents(String name) {
        JsonArray incidents = incidentService.incidentsByName(name);
        for (Object o : incidents) {
            JsonObject incident = (JsonObject) o;
            JsonObject mission = missionService.missionByIncidentId(incident.getString("id"));
            if (mission != null) {
                incident.put("destinationLat", mission.getDouble("destinationLat"));
                incident.put("destinationLon", mission.getDouble("destinationLong"));
                incident.put("destinationName", shelterService.shelter(BigDecimal.valueOf(mission.getDouble("destinationLat")),
                        BigDecimal.valueOf(mission.getDouble("destinationLong"))));
                JsonArray responderLocationHistory = mission.getJsonArray("responderLocationHistory");
                if (responderLocationHistory != null && !responderLocationHistory.isEmpty()) {
                    JsonObject responderLocation = responderLocationHistory.getJsonObject(responderLocationHistory.size() - 1);
                    incident.put("currentPositionLat", responderLocation.getDouble("lat"));
                    incident.put("currentPositionLon", responderLocation.getDouble("lon"));
                } else {
                    incident.put("currentPositionLat", new BigDecimal(incident.getString("lat")).doubleValue());
                    incident.put("currentPositionLon", new BigDecimal(incident.getString("lon")).doubleValue());
                }
            }
        }
        return incidents;
    }
}
