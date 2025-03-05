package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class SelectIncidentClientRequest extends ClientRequest<Object, List<Incident>> {

    public SelectIncidentClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Incident info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public List<Incident> readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(body, Incident[].class)); // Convertit en liste
    }
}
