package edu.ezip.ing1.pds.services.Citoyen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.*;

public class SuggestionService {

    private final static String LoggingLabel = "FrontEnd - SuggestionService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);


    final String insertRequestOrder = "INSERT_SUGGESTION";
    final String selectRequestOrder = "SELECT_SUGGESTION";
    final String selectTicketSuggestionsByTel = "SELECT_SUGGESTION_BY_TEL";

    private final NetworkConfig networkConfig;

    public SuggestionService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertSuggestion(Suggestion suggestion) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final Suggestions tickets = new Suggestions();
        tickets.getSuggestions().add(suggestion);


        int birthdate = 0;
        for(final Suggestion ticket : tickets.getSuggestions()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticket);
            logger.trace("Suggestion with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(insertRequestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertSuggestionClientRequest clientRequest = new InsertSuggestionClientRequest(
                    networkConfig,
                    birthdate++, request, ticket, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Suggestion ticket = (Suggestion) clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest.getThreadName(),
                    ticket.getIdTicket(), ticket.getTitre(), ticket.getDescription(), ticket.getDate_creation(), ticket.getCategorie(),
                    ticket.getStatut(), ticket.getCP_Ticket(), ticket.getDate_cloture(), ticket.getCommentaire(), ticket.getTelNum(), ticket.getCP(),
                    clientRequest.getResult());
        }
    }

    public Suggestions selectSuggestions() throws Exception {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllSuggestionsClientRequest clientRequest = new SelectAllSuggestionsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (Suggestions) joinedClientRequest.getResult();
        } else {
            logger.error("Pas de de suggestion trouvé");
            return null;
        }
    }

    public List<Suggestion> selectSuggestionsByTel(String telNum) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectTicketSuggestionsByTel);

        //Converti en Json tel, pour que le serveur puisse le lire
        ObjectNode requestContent = objectMapper.createObjectNode();
        requestContent.put("citoyen_telNum", telNum);
        request.setRequestContent(objectMapper.writeValueAsString(requestContent));

        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        final SelectSuggestionByTelClientRequest clientRequest = new SelectSuggestionByTelClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());

            return (List<Suggestion>) joinedClientRequest.getResult();
        } else {
            logger.error("Aucune réponse reçue du serveur.");
            return new ArrayList<>();
        }
    }
}
