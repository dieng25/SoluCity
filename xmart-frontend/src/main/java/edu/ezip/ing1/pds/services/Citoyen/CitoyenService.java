package edu.ezip.ing1.pds.services.Citoyen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Citoyens;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests.InsertCitoyenClientRequest;
import edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests.SelectAllCitoyensClientRequest;
import edu.ezip.ing1.pds.requests.InterfaceCitoyenRequests.SelectTelExistClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class CitoyenService {

    private final static String LoggingLabel = "FrontEnd - CitoyenService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);




    final String insertRequestOrder = "INSERT_CITOYEN";
    final String selectRequestOrder = "SELECT_CITOYEN";
    final String selectTelExistRequestOrder = "SELECT_TEL_EXIST";

    private final NetworkConfig networkConfig;

    public CitoyenService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertCitoyen(Citoyen citoyen) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final Citoyens guys = new Citoyens();
        guys.getCitoyens().add(citoyen);


        int birthdate = 0;
        for(final Citoyen guy : guys.getCitoyens()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(guy);
            logger.trace("Citoyen with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(insertRequestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertCitoyenClientRequest clientRequest = new InsertCitoyenClientRequest(
                    networkConfig,
                    birthdate++, request, guy, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Citoyen guy = (Citoyen) clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest.getThreadName(),
                    guy.getTelNum(), guy.getNom(), guy.getPrenom(), guy.getEmail(), guy.getEmail(),
                    clientRequest.getResult());
        }
    }

    public Citoyens selectCitoyens() throws InterruptedException, IOException {
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
        final SelectAllCitoyensClientRequest clientRequest = new SelectAllCitoyensClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (Citoyens) joinedClientRequest.getResult();
        }
        else {
            logger.error("No students found");
            return null;
        }
    }

    public boolean selectTelExist(String tel) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectTelExistRequestOrder);

        // Converti en Json tel, pour que le serveur puisse le lire
        ObjectNode requestContent = objectMapper.createObjectNode();
        requestContent.put("citoyen_telNum", tel);
        request.setRequestContent(objectMapper.writeValueAsString(requestContent));

        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        final SelectTelExistClientRequest clientRequest = new SelectTelExistClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            Boolean result = (Boolean) joinedClientRequest.getResult();
            if (result == null) {
                logger.error("Le résultat est null");
                throw new IllegalStateException("Résultat de selectTelExist ne doit pas être null.");
            }
            return result;
        }
        else {
            return false;
        }
    }


}
