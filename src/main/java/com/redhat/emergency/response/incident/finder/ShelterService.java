package com.redhat.emergency.response.incident.finder;

import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ShelterService {

    @Inject
    @RestClient
    ShelterServiceClient client;

    public JsonArray shelters() {
        return client.getShelters();
    }

    public String shelter(BigDecimal lat, BigDecimal lon) {
        return shelters().stream().map(o -> (JsonObject)o)
                .filter(shelter -> BigDecimal.valueOf(shelter.getDouble("lat")).equals(lat) && BigDecimal.valueOf(shelter.getDouble("lon")).equals(lon))
                .map(shelter -> shelter.getString("name")).findFirst().orElse("");
    }

}
