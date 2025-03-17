package edu.ezip.ing1.pds.services.Dashboard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalData;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.DashboardRequest.DashboardGlobalRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class DashboardServiceGlobal {
    private final static String LoggingLabel = "FrontEnd - DashboardServiceGlobal";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    final String dashboardRequestOrder = "GLOBAL_REQUEST";

    private final NetworkConfig networkConfig;

    public DashboardServiceGlobal(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    //Envoie une requête DashboardRequest pour récupérer les statistiques des incidents.
     
     public GlobalDatas global(DashboardFilterDTO filterDTO) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        //Object info = null;
        request.setRequestId(requestId);
        request.setRequestOrder(dashboardRequestOrder);
        //request.setRequestBody("");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        /*final DashboardGlobalRequest clientRequest = new DashboardGlobalRequest(
                networkConfig,
                birthdate++, request, info, requestBytes);
        clientRequests.push(clientRequest);*/
        final DashboardGlobalRequest clientRequest = new DashboardGlobalRequest(
                networkConfig, birthdate++, request, filterDTO, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (GlobalDatas) joinedClientRequest.getResult();
        } else {
            logger.error("Aucun résultat de dashboard n'a été obtenu");
            return null;
        }
    }
}
