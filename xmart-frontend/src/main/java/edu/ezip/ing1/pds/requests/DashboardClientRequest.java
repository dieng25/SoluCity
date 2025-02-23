package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;

import java.io.IOException;

public class DashboardClientRequest extends ClientRequest<Object, DashboardDatas> {

    public DashboardClientRequest(NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public DashboardDatas readResult(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // On désérialise maintenant
        return mapper.readValue(body, DashboardDatas.class);
    }
}