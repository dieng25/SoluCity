package edu.ezip.ing1.pds.services.Citoyen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.business.dto.Incidents;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertIncidentClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class IncidentService {

    private final static String LoggingLabel = "FrontEnd - IncidentService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //private final static String studentsToBeInserted = "students-to-be-inserted.yaml";



    final String insertRequestOrder = "INSERT_INCIDENT";


    private final NetworkConfig networkConfig;

    public IncidentService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertIncident(Incident incident) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        //final Students guys = ConfigLoader.loadConfig(Students.class, studentsToBeInserted);
        final Incidents tickets = new Incidents();
        tickets.getIncidents().add(incident);


        int birthdate = 0;
        for(final Incident ticket : tickets.getIncidents()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticket);
            logger.trace("Incident with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(insertRequestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertIncidentClientRequest clientRequest = new InsertIncidentClientRequest(
                    networkConfig,
                    birthdate++, request, ticket, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Incident ticket = (Incident) clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest.getThreadName(),
                    ticket.getIdTicket(), ticket.getTitre(), ticket.getDescription(), ticket.getDate_creation(), ticket.getCategorie(),
                    ticket.getStatut(), ticket.getCP_Ticket(), ticket.getPriorite(), ticket.getDate_cloture(), ticket.getTelNum(), ticket.getCP(),
                    clientRequest.getResult());
        }
    }
}
