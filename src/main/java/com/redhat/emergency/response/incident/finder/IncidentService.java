package com.redhat.emergency.response.incident.finder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.json.JsonArray;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class IncidentService {

    private static Logger log = LoggerFactory.getLogger(IncidentService.class);

    @Inject
    @RestClient
    IncidentServiceClient client;

    public JsonArray incidentsByName(String name) {
        if (!name.startsWith("%")) {
            name = "%" + name;
        }
        if (!name.endsWith("%")) {
            name = name + "%";
        }
        return client.incidentsByName(name);
    }
}