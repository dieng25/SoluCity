package edu.ezip.ing1.pds.requests.DashboardRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardGlobalRequest extends ClientRequest<DashboardFilterDTO, GlobalDatas> {

    public DashboardGlobalRequest(NetworkConfig networkConfig, int myBirthDate, Request request, DashboardFilterDTO filterDTO, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, filterDTO, convertToJson(filterDTO));
    }

   private static byte[] convertToJson(DashboardFilterDTO filterDTO) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    
    ObjectNode rootNode = mapper.createObjectNode();
    rootNode.set("request", mapper.valueToTree(filterDTO));
    
    return mapper.writeValueAsBytes(rootNode);
}

    

    @Override
    public GlobalDatas readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, GlobalDatas.class);
    }
}
