package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectSuggestionByTelClientRequest extends ClientRequest<Object, List<Suggestion>> {

    public SelectSuggestionByTelClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public List<Suggestion> readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Suggestions suggestions = mapper.readValue(body, Suggestions.class);
        Set<Suggestion> suggestionSet = suggestions.getSuggestions();
        return new ArrayList<>(suggestionSet);
    }
}
