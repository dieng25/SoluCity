package edu.ezip.ing1.pds.services.Dashboard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatSuggestionDatas;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.DashboardRequest.StatSuggestionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class StatSuggestionService {
    private final static String LoggingLabel = "FrontEnd - StatSuggestionService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private final String dashboardRequestOrder = "STAT_SUGGESTION_REQUEST";
    private final NetworkConfig networkConfig;

    public StatSuggestionService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    // Envoie une requête STAT_SUGGESTION_REQUEST pour récupérer les statistiques des suggestions
    public StatSuggestionDatas suggestion(DashboardFilterDTO filterDTO) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(dashboardRequestOrder);

        java.util.Date utilDateDebut = filterDTO.getDateDebut();
        java.util.Date utilDateFin = filterDTO.getDateFin();

        Date sqlDateDebut = new Date(utilDateDebut.getTime());
        Date sqlDateFin = new Date(utilDateFin.getTime());

        request.setDateDebut(sqlDateDebut);
        request.setDateFin(sqlDateFin);
        request.setCodePostal(filterDTO.getCodePostal());

        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
            System.out.println("JSON envoyé au backend : " + new String(requestBytes));

        final StatSuggestionRequest clientRequest = new StatSuggestionRequest(
                networkConfig, birthdate++, request, filterDTO, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (StatSuggestionDatas) joinedClientRequest.getResult();
        } else {
            logger.error("Aucun résultat de dashboard n'a été obtenu");
            return null;
        }
    }
}
