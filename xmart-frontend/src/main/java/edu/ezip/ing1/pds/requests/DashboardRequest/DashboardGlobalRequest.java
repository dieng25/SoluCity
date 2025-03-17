package edu.ezip.ing1.pds.requests.DashboardRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalData;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;

public class DashboardGlobalRequest extends ClientRequest<DashboardFilterDTO, GlobalDatas> {

    public DashboardGlobalRequest(NetworkConfig networkConfig, int myBirthDate, Request request, DashboardFilterDTO filterDTO, byte[] bytes)
            throws IOException {
        //super(networkConfig, myBirthDate, request, info, bytes);
       // super(networkConfig, myBirthDate, request, filterDTO, new ObjectMapper().writeValueAsBytes(filterDTO));
       super(networkConfig, myBirthDate, request, filterDTO, convertToJson(filterDTO));

    }
    private static byte[] convertToJson(DashboardFilterDTO filterDTO) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("request", filterDTO);
        return mapper.writeValueAsBytes(requestBody);
    }

    @Override
    public GlobalDatas readResult(String body) throws IOException {
         final ObjectMapper mapper = new ObjectMapper();
        // On désérialise maintenant
        return mapper.readValue(body, GlobalDatas.class);
    }
}
