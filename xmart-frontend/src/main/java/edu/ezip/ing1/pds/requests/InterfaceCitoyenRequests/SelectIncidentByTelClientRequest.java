package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectIncidentByTelClientRequest extends ClientRequest<Object, List<Incident>> {

    public SelectIncidentByTelClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public List<Incident> readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Incidents incidents = mapper.readValue(body, Incidents.class);
        Set<Incident> incidentSet = incidents.getIncidents();
        return new ArrayList<>(incidentSet);
    }
}