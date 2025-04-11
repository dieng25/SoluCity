package edu.ezip.ing1.pds.services.Citoyen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.CategorieIncidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests.SelectAllCategorieIncidentsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class CategorieIncidentService {

    private final static String LoggingLabel = "FrontEnd - CategorieIncidentService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    final String selectRequestOrder = "SELECT_ALL_CategorieIncident";

    private final NetworkConfig networkConfig;

    public CategorieIncidentService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
        logger.debug("NetworkConfig: {}", networkConfig);
    }

    public CategorieIncidents selectCategorieIncidents() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllCategorieIncidentsRequest clientRequest = new SelectAllCategorieIncidentsRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (CategorieIncidents) joinedClientRequest.getResult();
        }
        else {
            logger.error("No categorie incident found");
            return null;
        }
    }
}
