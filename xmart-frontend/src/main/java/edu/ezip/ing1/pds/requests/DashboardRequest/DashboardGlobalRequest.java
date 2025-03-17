package edu.ezip.ing1.pds.requests.DashboardRequest;

/*import com.fasterxml.jackson.databind.ObjectMapper;
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
        // Appel du constructeur de la classe parent
        super(networkConfig, myBirthDate, request, filterDTO, convertToJson(filterDTO));
    }

    private static byte[] convertToJson(DashboardFilterDTO filterDTO) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        // Encapsuler le corps de la requête sous la clé "request"
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date_debut", filterDTO.getDateDebut());
        requestBody.put("date_fin", filterDTO.getDateFin());
        requestBody.put("code_postal", filterDTO.getCodePostal());
        
        // Mise en forme pour correspondre à la structure attendue
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("request", requestBody); // La clé 'request' encapsule les données
        
        return mapper.writeValueAsBytes(requestMap); // Sérialisation en JSON
    }

    @Override
    public GlobalDatas readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        // Désérialisation du corps de la réponse
        return mapper.readValue(body, GlobalDatas.class);
    }
}*/

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("request", filterDTO); // Encapsulation sous "request"
        return mapper.writeValueAsBytes(requestBody);
    }

    @Override
    public GlobalDatas readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, GlobalDatas.class);
    }
}
