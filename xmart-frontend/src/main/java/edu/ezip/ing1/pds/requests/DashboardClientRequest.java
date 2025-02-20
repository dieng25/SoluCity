package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.business.dto.DashboardData;
import java.io.IOException;

public class DashboardClientRequest extends ClientRequest<Object, DashboardData> {

    public DashboardClientRequest(NetworkConfig networkConfig, int myBirthDate, Request request, byte[] bytes)
            throws IOException {
        // Pour DashboardRequest, aucune donnée va etre envoyée (info = null)
        super(networkConfig, myBirthDate, request, null, bytes);
    }

    @Override
    public DashboardData readResult(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // On désérialise maintenant
        return mapper.readValue(body, DashboardData.class);
    }
}