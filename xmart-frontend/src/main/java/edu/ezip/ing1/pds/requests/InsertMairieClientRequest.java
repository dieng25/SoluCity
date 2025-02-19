package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertMairieClientRequest extends ClientRequest<Mairie, String> {

    public InsertMairieClientRequest(
            NetworkConfig networkConfig, Request request, Mairie info, byte[] bytes)
            throws IOException {
        super(networkConfig, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> mairieIdMap = mapper.readValue(body, Map.class);
        final String result  = mairieIdMap.get("incident_id").toString();
        return result;
    }
}
