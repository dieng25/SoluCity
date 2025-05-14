package edu.ezip.ing1.pds.services.Mairie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Suggestions;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.MairieRequests.SelectAllSuggestionsClientRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class SuggestionService {

    private static final String LOGGING_LABEL = "FrontEnd - SuggestionService";
    private static final Logger logger = LoggerFactory.getLogger(LOGGING_LABEL);

    private static final String SELECT_REQUEST_ORDER = "SELECT_ALL_SUGGESTIONS";

    private final NetworkConfig networkConfig;
    public SuggestionService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public Suggestions selectSuggestions() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();

        // Créer la requête pour récupérer les incidents
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(SELECT_REQUEST_ORDER);

        // Sérialiser la requête en JSON
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        // Log de la requête
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        // Créer la requête client pour sélectionner tous les sugg
        try {
            final SelectAllSuggestionsClientRequest clientRequest = new SelectAllSuggestionsClientRequest(
                networkConfig,
                birthdate,
                request,
                null,
                requestBytes
            );
            clientRequests.push(clientRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

       

        // Traiter la requête si la file n'est pas vide
        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();  // Attendre que la requête soit terminée
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());

            // Retourner le résultat de la requête client
            return (Suggestions) joinedClientRequest.getResult();
        } else {
            logger.error("Aucune Suggestion trouvée");
            return null;  // Retourner null si aucun résultat n'a été trouvé
        }
    }

}
