package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertSuggestionClientRequest extends ClientRequest<Suggestion, String> {

    public InsertSuggestionClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Suggestion info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> ticketIdMap = mapper.readValue(body, Map.class);
        final String result = ticketIdMap.get("suggestion_IdTicket").toString();
        return result;
    }
}
