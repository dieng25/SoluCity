package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertSuggestionClientRequest extends ClientRequest<Suggestion, String> {

    public InsertSuggestionClientRequest(
            NetworkConfig networkConfig, Request request, Suggestion info, byte[] bytes)
            throws IOException {
        super(networkConfig, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> suggestionIdMap = mapper.readValue(body, Map.class);
        final String result  = suggestionIdMap.get("suggestion_id").toString();
        return result;
    }
}
