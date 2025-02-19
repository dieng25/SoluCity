package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertIncidentClientRequest extends ClientRequest<Incident, String> {

    public InsertIncidentClientRequest(
            NetworkConfig networkConfig, Request request, Student info, byte[] bytes)
            throws IOException {
        super(networkConfig, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> incidentIdMap = mapper.readValue(body, Map.class);
        final String result  = incidentIdMap.get("incident_id").toString();
        return result;
    }
}
