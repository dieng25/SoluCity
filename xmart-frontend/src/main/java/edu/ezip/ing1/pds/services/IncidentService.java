package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.SelectAllIncidentsClientRequest;

import java.io.IOException;
import java.util.UUID;

public class IncidentService {
    private final NetworkConfig networkConfig;

    public IncidentService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public Incidents getIncidents(String codePostal) throws InterruptedException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("SELECT_INCIDENTS");
        request.setRequestContent(codePostal);
        
        final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
        SelectAllIncidentsClientRequest clientRequest = new SelectAllIncidentsClientRequest(networkConfig, codePostal, request, requestBytes);
        clientRequest.join();

        return clientRequest.getResult();
    }
}
