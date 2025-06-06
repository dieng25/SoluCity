package edu.ezip.ing1.pds.services.Mairie;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.MairieRequests.AuthenticateFonctionnaireClientRequest;
import edu.ezip.ing1.pds.requests.MairieRequests.RegisterFonctionnaireClientRequest;
import edu.ezip.ing1.pds.business.dto.Fonctionnaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class FonctionnaireService {

    private static final Logger logger = LoggerFactory.getLogger(FonctionnaireService.class);
    private final NetworkConfig networkConfig;

    public FonctionnaireService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public boolean registerFonctionnaire(Fonctionnaire fonctionnaire) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("REGISTER");
    
        try {
            String fonctionnaireJson = objectMapper.writeValueAsString(fonctionnaire);
            request.setRequestContent(fonctionnaireJson); 
    
            final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
    
            RegisterFonctionnaireClientRequest clientRequest = new RegisterFonctionnaireClientRequest(
                    networkConfig,
                    0,
                    request,
                    fonctionnaire,
                    requestBytes
            );
    
          
            clientRequest.join();
    
            return (boolean) clientRequest.getResult();
        } catch (InterruptedException e) {
            logger.error("Thread interrompu lors de l'inscription.", e);
            return false;
        } catch (IOException e) {
            logger.error("Erreur de sérialisation lors de la requête d'inscription.", e);
            return false;
        }
    }
    

    // Authentification d'un fonctionnaire
    public boolean authenticateFonctionnaire(Fonctionnaire fonctionnaire) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("AUTHENTICATE");
        

        try {
            // Sérialiser la requête en JSON
            final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
            String fonctionnaireJson = objectMapper.writeValueAsString(fonctionnaire);
            request.setRequestContent(fonctionnaireJson);
            AuthenticateFonctionnaireClientRequest clientRequest = new AuthenticateFonctionnaireClientRequest(
                    networkConfig,
                    0,
                    request,
                    fonctionnaire,
                    requestBytes
            );

            // Attendre la réponse de la requête
            clientRequest.join();
            return (boolean) clientRequest.getResult();
        } catch (InterruptedException e) {
            // Capturer l'exception d'interruption
            logger.error("Thread interomppu lors de l'inscription.", e);
            return false;

        } catch (IOException e) {
            // Capturer les erreurs d'entrée/sortie (erreurs de sérialisation)
            logger.error("Error de serialisation lors de la requete pour l'authentication.", e);
            return false;
        }
    }
}
