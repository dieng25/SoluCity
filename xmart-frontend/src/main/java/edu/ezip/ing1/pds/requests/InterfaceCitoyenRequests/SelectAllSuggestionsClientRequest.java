package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllSuggestionsClientRequest extends ClientRequest<Object, Suggestions> {

    public SelectAllSuggestionsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws Exception {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Suggestions readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Suggestions suggestions = mapper.readValue(body, Suggestions.class);
        return suggestions;
    }


}
