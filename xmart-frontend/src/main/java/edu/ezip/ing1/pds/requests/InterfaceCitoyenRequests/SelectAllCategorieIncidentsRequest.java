package edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.CategorieIncidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllCategorieIncidentsRequest extends ClientRequest<Object, CategorieIncidents> {

    public SelectAllCategorieIncidentsRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public CategorieIncidents readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final CategorieIncidents categorieIncidents = mapper.readValue(body, CategorieIncidents.class);
        return categorieIncidents;
    }
}
