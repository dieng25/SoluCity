package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllIncidentsClientRequest extends ClientRequest<Object, Suggestions> {

    public SelectAllIncidentsClientRequest(
            NetworkConfig networkConfig, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, request, info, bytes);
    }

    @Override
    public Suggestions readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Suggestions suggestions = mapper.readValue(body, Suggestions.class);
        return suggestions;
    }
}
