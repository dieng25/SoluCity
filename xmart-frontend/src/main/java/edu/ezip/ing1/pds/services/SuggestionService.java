package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.SelectAllSuggestionsClientRequest;

import java.io.IOException;
import java.util.UUID;

public class SuggestionService {
    private final NetworkConfig networkConfig;

    public SuggestionService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public Suggestions getSuggestions(String codePostal) throws InterruptedException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("SELECT_SUGGESTIONS");
        request.setRequestContent(codePostal);
        
        final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
        SelectAllSuggestionsClientRequest clientRequest = new SelectAllSuggestionsClientRequest(networkConfig, codePostal, request, requestBytes);
        clientRequest.join();

        return clientRequest.getResult();
    }
}
