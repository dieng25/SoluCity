package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.DashboardClientRequest;
//import edu.ezip.ing1.pds.requests.SelectAllStudentsClientRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class DashboardServiceClient {
    private final static String LoggingLabel = "FrontEnd - DashboardServiceClient";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    // Ordre de requête pour DashboardRequest
    final String dashboardRequestOrder = "DASHBOARD_REQUEST";

    private final NetworkConfig networkConfig;

    public DashboardServiceClient(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    //Envoie une requête DashboardRequest pour récupérer les statistiques des incidents.
     
    public DashboardDatas dashboard() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        Object info = null;
        request.setRequestId(requestId);
        request.setRequestOrder(dashboardRequestOrder);
        request.setRequestContent("");

        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        final DashboardClientRequest clientRequest = new DashboardClientRequest(
                networkConfig,
                birthdate++, request, info, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (DashboardDatas) joinedClientRequest.getResult();
        } else {
            logger.error("Aucun résultat de dashboard n'a été obtenu");
            return null;
        }
    }
}