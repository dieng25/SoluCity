package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllIncidentsClientRequest extends ClientRequest<Object, Incidents> {

    public SelectAllIncidentsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
    throws Exception {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Incidents readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Incidents incidents = mapper.readValue(body, Incidents.class);
        return incidents;
    }
}
