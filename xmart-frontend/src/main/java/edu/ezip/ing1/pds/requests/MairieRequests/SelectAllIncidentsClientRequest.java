package edu.ezip.ing1.pds.requests.MairieRequests;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;
import edu.ezip.ing1.pds.business.dto.Incidents;

public class SelectAllIncidentsClientRequest extends ClientRequest<Object, Incidents> {

    public SelectAllIncidentsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Incident info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Incidents readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Incidents incidents = mapper.readValue(body, Incidents.class);
        return incidents;
    }
}
