package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectConnexionClientRequest extends ClientRequest<Object, Citoyen> {

    public SelectConnexionClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Citoyen readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, Citoyen.class);
    }
}