package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyens;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllCitoyensClientRequest extends ClientRequest<Object, Citoyens> {

    public SelectAllCitoyensClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Citoyens readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Citoyens citoyens = mapper.readValue(body, Citoyens.class);
        return citoyens;
    }
}
