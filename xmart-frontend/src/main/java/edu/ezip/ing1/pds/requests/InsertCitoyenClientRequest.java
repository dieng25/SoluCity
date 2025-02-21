package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertCitoyenClientRequest extends ClientRequest<Citoyen, String> {

    public InsertCitoyenClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Citoyen info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> citoyenIdMap = mapper.readValue(body, Map.class);
        final String result  = citoyenIdMap.get("citoyen_telNum").toString();
        return result;
    }
}
